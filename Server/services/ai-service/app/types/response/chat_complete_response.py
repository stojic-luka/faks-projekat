from pydantic import BaseModel

from typing import List

from app.enum import ResponseTypes, MessageRoles  # type: ignore


class ChatResponseMessageImage(BaseModel):
    data: str
    format: str
    width: str
    height: str
    description: str


class ChatResponseMessage(BaseModel):
    content: str
    images: List[ChatResponseMessageImage] = []


class ChatResponse(BaseModel):
    request_id: str
    type: ResponseTypes
    role: MessageRoles
    model: str
    timestamp: int
    message: ChatResponseMessage
