from pydantic import BaseModel
from typing import Generic, TypeVar
from app.enum import ResponseTypes, MessageRoles  # type: ignore

T = TypeVar("T")


class ChatStreamedResponse(BaseModel, Generic[T]):
    request_id: str
    sequence: int
    last_chunk: bool
    content: T


#


class ChatStreamedTextResponse(ChatStreamedResponse[str]):
    type: ResponseTypes = ResponseTypes.STREAMED_TEXT


#


class ChatStreamedImageContent(BaseModel):
    data: str
    format: str
    width: str
    height: str
    description: str


class ChatStreamedImageResponse(ChatStreamedResponse[ChatStreamedImageContent]):
    type: ResponseTypes = ResponseTypes.STREAMED_IMAGE


#


class ChatStreamedMetadataContent(BaseModel):
    user_id: str
    model: str
    role: MessageRoles
    timestamp: int


class ChatStreamedMetadataResponse(ChatStreamedResponse[ChatStreamedMetadataContent]):
    type: ResponseTypes = ResponseTypes.STREAMED_METADATA
