from pydantic import BaseModel

from typing import Optional, List

from app.enum import ResponseTypes, MessageRoles


class ChatResponseMessage(BaseModel):
    role: str
    content: str
    images: Optional[List[str]] = []
    tool_calls: Optional[List[str]] = []


class ChatResponse(BaseModel):
    type: ResponseTypes
    role: MessageRoles
    model: str
    timestamp: str
    message: ChatResponseMessage


class ChatStreamedResponse(BaseModel):
    type: ResponseTypes
    role: MessageRoles
    model: str
    timestamp: str
    images: Optional[List[str]] = []
    tool_calls: Optional[List[str]] = []
