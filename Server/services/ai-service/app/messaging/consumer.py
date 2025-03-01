import asyncio, logging
import pika  # type: ignore
from typing import Any, Coroutine
from openai import AsyncStream
from app.config.rabbitmq import (
    get_connection,
    EXCHANGE_NAME,
    REQUEST_QUEUE,
    ROUTING_KEY,
)
from app.messaging.publisher import RabbitMQPublisher

from app.utils.is_valid_base64 import is_valid_base64
from app.services.chatbot import get_chatbot_response, get_streamed_chatbot_response

from app.types.chat_response import ChatStreamedResponse
from app.types.chat_request import ChatRequest

from openai.types.chat import ChatCompletionChunk


class RabbitMQConsumer:
    def __init__(self) -> None:
        try:
            self.__connection: pika.BlockingConnection = get_connection()
            self.__channel: pika.BlockingChannel = self.__connection.channel()
            self.__channel.exchange_declare(
                exchange=EXCHANGE_NAME, exchange_type="direct", durable=True
            )
            self.__channel.queue_declare(queue=REQUEST_QUEUE, durable=True)
            self.__channel.queue_bind(
                queue=REQUEST_QUEUE, exchange=EXCHANGE_NAME, routing_key=ROUTING_KEY
            )
            self.__publisher = RabbitMQPublisher()
        except pika.exceptions.AMQPConnectionError as e:
            logging.error("Failed to connect to RabbitMQ: %s", e)
            raise

    def start_consuming(self) -> None:
        self.__channel.basic_qos(prefetch_count=1)
        self.__channel.basic_consume(
            queue=REQUEST_QUEUE, on_message_callback=self.__on_message_callback
        )
        logging.info("Started consumer. Waiting for messages...")

        try:
            self.__channel.start_consuming()
        except KeyboardInterrupt:
            self.__channel.stop_consuming()
            self.__connection.close()

    async def __process_streamed_response(
        self,
        metadata: ChatStreamedResponse,
        stream: Coroutine[Any, Any, AsyncStream[ChatCompletionChunk]],
    ) -> None:
        self.__publisher.publish(metadata.model_dump_json())
        async for chunk in await stream:
            if chunk is not None and chunk.choices[0].delta.content is not None:
                self.__publisher.publish(chunk.choices[0].delta.content)

    def __on_message_callback(self, ch, method, properties, body) -> None:
        try:
            message: ChatRequest = ChatRequest.model_validate_json(body)

            # if message.imageb64 and not is_valid_base64(message.imageb64):
            #     raise HTTPException(status_code=400, detail="Image base64 is invalid")

            if message.streamed:
                asyncio.run(
                    self.__process_streamed_response(
                        *get_streamed_chatbot_response(
                            model=message.model,
                            message=message.message,
                            image_b64=message.imageb64,
                        )
                    )
                )

            else:
                response_content = asyncio.run(
                    get_chatbot_response(
                        model=message.model,
                        message=message.message,
                        image_b64=message.imageb64,
                    )
                )
                self.__publisher.publish(response_content.model_dump_json())

            ch.basic_ack(delivery_tag=method.delivery_tag)
        except Exception as e:
            logging.error("Error processing message: %s", e)
            ch.basic_nack(delivery_tag=method.delivery_tag, requeue=False)

    def close(self) -> None:
        logging.info("Shutting down consumer...")
        self.__publisher.close()
        if self.__channel.is_open:
            self.__channel.stop_consuming()
        if self.__connection.is_open:
            self.__connection.close()
