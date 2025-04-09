import pika  # type: ignore
import logging
from app.config.rabbitmq import (  # type: ignore
    get_connection,
    RESPONSE_ROUTING_KEY,
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

    def __reconnect(self) -> None:
        try:
            if self.__connection and not self.__connection.is_closed:
                self.__connection.close()
        except Exception as e:
            logging.error("Error during connection close: %s", e)
        # Reinitialize connection and channel
        self.__connection = get_connection()
        self.__channel = self.__connection.channel()
        self.__channel.queue_declare(queue=RESPONSE_QUEUE, durable=True)
        logging.info("Reconnected to RabbitMQ.")

    def publish(self, message: str) -> None:
        try:
            self.__channel.basic_publish(
                exchange=EXCHANGE_NAME,
                routing_key=RESPONSE_ROUTING_KEY,
                body=message,
                properties=pika.BasicProperties(delivery_mode=2),
            )
            print("published message: ", message)
        except pika.exceptions.AMQPError as error:
            logging.error("Publish error detected (%s), attempting reconnect.", error)
            self.__reconnect()
            self.__channel.basic_publish(
                exchange=EXCHANGE_NAME,
                routing_key=RESPONSE_ROUTING_KEY,
                body=message,
                properties=pika.BasicProperties(delivery_mode=2),
            )
            print("published message after reconnect: ", message)

    def close(self) -> None:
        self.__connection.close()
