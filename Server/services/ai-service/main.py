import sys, logging, signal
from app.messaging.consumer import RabbitMQConsumer


def main() -> None:
    logging.basicConfig(level=logging.INFO)
    consumer = RabbitMQConsumer()

    def signal_handler(sig, frame):
        consumer.close()
        sys.exit(0)

    signal.signal(signal.SIGINT, signal_handler)
    signal.signal(signal.SIGTERM, signal_handler)

    consumer.start_consuming()


if __name__ == "__main__":
    main()
