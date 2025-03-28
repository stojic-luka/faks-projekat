import os, pika  # type: ignore
from dotenv import load_dotenv
import logging

load_dotenv()

RABBITMQ_HOST = os.getenv("RABBITMQ_HOST")
RABBITMQ_PORT = int(str(os.getenv("RABBITMQ_PORT")))
RABBITMQ_USER = os.getenv("RABBITMQ_USER")
RABBITMQ_PASS = os.getenv("RABBITMQ_PASS")
EXCHANGE_NAME = os.getenv("EXCHANGE_NAME")
REQUEST_QUEUE = os.getenv("REQUEST_QUEUE")
REQUEST_ROUTING_KEY = os.getenv("REQUEST_ROUTING_KEY")
RESPONSE_QUEUE = os.getenv("RESPONSE_QUEUE")
RESPONSE_ROUTING_KEY = os.getenv("RESPONSE_ROUTING_KEY")


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
    "REQUEST_QUEUE",
    "RESPONSE_QUEUE",
    "get_connection",
]
