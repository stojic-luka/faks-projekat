import asyncio, logging
import pika  # type: ignore
from datetime import datetime, timezone
from typing import Any, Coroutine
from openai import AsyncStream
from app.config.rabbitmq import (  # type: ignore
    get_connection,
    EXCHANGE_NAME,
    REQUEST_QUEUE,
    REQUEST_ROUTING_KEY,
)
from app.messaging.publisher import RabbitMQPublisher  # type: ignore

from app.utils.aenumerate import aenumerate  # type: ignore
from app.services.chatbot import get_chatbot_response, get_streamed_chatbot_response  # type: ignore

from app.types.response.chat_streamed_response import (  # type: ignore
    ChatStreamedMetadataResponse,
    ChatStreamedTextResponse,
)
from app.types.request.chat_request import ChatRequest  # type: ignore

from openai.types.chat import ChatCompletionChunk


class RabbitMQConsumer:
    def __init__(self) -> None:
        try:
            self.__connection: pika.BlockingConnection = get_connection()
            self.__channel: pika.BlockingChannel = self.__connection.channel()
            self.__channel.exchange_declare(
                exchange=EXCHANGE_NAME, exchange_type="topic", durable=True
            )
            self.__channel.queue_declare(queue=REQUEST_QUEUE, durable=True)
            self.__channel.queue_bind(
                queue=REQUEST_QUEUE,
                exchange=EXCHANGE_NAME,
                routing_key=REQUEST_ROUTING_KEY,
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
        metadata: ChatStreamedMetadataResponse,
        stream: Coroutine[Any, Any, AsyncStream[ChatCompletionChunk]],
    ) -> None:
        first_chunk_processed = False

        async for idx, chunk in aenumerate(await stream, start=1):
            if chunk is not None and chunk.choices[0].delta.content is not None:
                if not first_chunk_processed:
                    first_chunk_processed = True
                    metadata.content.timestamp = chunk.created
                    self.__publisher.publish(metadata.model_dump_json())

                self.__publisher.publish(
                    ChatStreamedTextResponse(
                        request_id=metadata.request_id,
                        sequence=idx,
                        last_chunk=bool(chunk.choices[0].finish_reason),
                        content=chunk.choices[0].delta.content,
                    ).model_dump_json()
                )

    def __on_message_callback(self, ch, method, properties, body) -> None:
        try:
            print(f"\n{body}\n")
            message: ChatRequest = ChatRequest.model_validate_json(body.decode("utf-8"))

            # if message.imageb64 and not is_valid_base64(message.imageb64):
            #     raise HTTPException(status_code=400, detail="Image base64 is invalid")

            if message.streamed:
                asyncio.run(
                    self.__process_streamed_response(
                        *get_streamed_chatbot_response(
                            request_id=message.request_id,
                            model=message.model,
                            prompt=message.prompt,
                            image_b64=message.imageb64,
                        ),
                    )
                )

            else:
                REQUEST_content = asyncio.run(
                    get_chatbot_response(
                        request_id=message.request_id,
                        model=message.model,
                        prompt=message.prompt,
                        image_b64=message.imageb64,
                    )
                )
                self.__publisher.publish(REQUEST_content.model_dump_json())

            ch.basic_ack(delivery_tag=method.delivery_tag)
        except Exception as e:
            logging.error("Error processing message: %s", e.with_traceback(None))
            ch.basic_nack(delivery_tag=method.delivery_tag, requeue=False)

    def close(self) -> None:
        logging.info("Shutting down consumer...")
        self.__publisher.close()
        if self.__channel.is_open:
            self.__channel.stop_consuming()
        if self.__connection.is_open:
            self.__connection.close()
