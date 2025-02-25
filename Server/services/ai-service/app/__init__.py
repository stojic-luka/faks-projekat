from fastapi import FastAPI
from app.routes import register_routes
from app.config import configure_cors


def create_app() -> FastAPI:
    app = FastAPI(title="Chatbot API")
    configure_cors(app)
    register_routes(app)
    return app
