import os, pika  # type: ignore
from dotenv import load_dotenv
import logging

load_dotenv()

RABBITMQ_HOST = os.getenv("RABBITMQ_HOST")
RABBITMQ_PORT = int(str(os.getenv("RABBITMQ_PORT")))
RABBITMQ_USER = os.getenv("RABBITMQ_USER")
RABBITMQ_PASS = os.getenv("RABBITMQ_PASS")
EXCHANGE_NAME = os.getenv("EXCHANGE_NAME")
ROUTING_KEY = os.getenv("ROUTING_KEY")
RESPONSE_QUEUE = os.getenv("RESPONSE_QUEUE")
REQUEST_QUEUE = os.getenv("REQUEST_QUEUE")


def get_connection():
    credentials = pika.PlainCredentials(RABBITMQ_USER, RABBITMQ_PASS)
    parameters = pika.ConnectionParameters(
        host=RABBITMQ_HOST,
        port=RABBITMQ_PORT,
        credentials=credentials,
    )
    try:
        connection = pika.BlockingConnection(parameters)
        logging.info("Successfully connected to RabbitMQ")
        return connection
    except pika.exceptions.AMQPConnectionError as e:
        logging.error("Failed to connect to RabbitMQ: %s", e)
        raise


__all__ = [
    "RABBITMQ_HOST",
    "RABBITMQ_PORT",
    "RABBITMQ_USER",
    "RABBITMQ_PASS",
    "EXCHANGE_NAME",
    "ROUTING_KEY",
    "RESPONSE_QUEUE",
    "REQUEST_QUEUE",
    "get_connection",
]
