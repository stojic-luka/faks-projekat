from fastapi import FastAPI
from app.routes import chat_router


def register_routes(app: FastAPI) -> None:
    app.include_router(chat_router.router, prefix="/chat", tags=["Chat"])
