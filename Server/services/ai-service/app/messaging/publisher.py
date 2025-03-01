import pika  # type: ignore
import logging
from app.config.rabbitmq import (
    get_connection,
    ROUTING_KEY,
    RESPONSE_QUEUE,
    EXCHANGE_NAME,
)


class RabbitMQPublisher:
    def __init__(self) -> None:
        try:
            self.__connection: pika.BlockingConnection = get_connection()
            self.__channel: pika.BlockingChannel = self.__connection.channel()
            self.__channel.queue_declare(queue=RESPONSE_QUEUE, durable=True)
        except pika.exceptions.AMQPConnectionError as e:
            logging.error("Failed to connect to RabbitMQ: %s", e)
            raise

    def publish(self, message: str) -> None:
        self.__channel.basic_publish(
            exchange=EXCHANGE_NAME,
            routing_key=ROUTING_KEY,
            body=message,
            properties=pika.BasicProperties(
                delivery_mode=2,  # make message persistent
            ),
        )
        print("Published response:", message)

    def close(self) -> None:
        self.__connection.close()
