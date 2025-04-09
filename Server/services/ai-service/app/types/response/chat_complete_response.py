from pydantic import BaseModel

from typing import List

from app.enum import ResponseTypes, MessageRoles  # type: ignore


class ChatResponseMessageImage(BaseModel):
    data: str
    format: str
    width: int
    height: int
    description: str


class ChatResponseMessage(BaseModel):
    text: str
    images: List[ChatResponseMessageImage] = []


class ChatResponse(BaseModel):
    request_id: str
    user_id: str
    type: ResponseTypes
    role: MessageRoles
    model: str
    timestamp: int
    content: ChatResponseMessage
