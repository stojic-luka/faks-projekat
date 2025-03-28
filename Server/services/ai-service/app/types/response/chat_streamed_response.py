from pydantic import BaseModel

from app.enum import ResponseTypes, MessageRoles  # type: ignore


class ChatStreamedTextResponse(BaseModel):
    request_id: str
    type: ResponseTypes = ResponseTypes.STREAMED_TEXT
    sequence: int
    last_chunk: bool
    content: str


class ChatStreamedImageContent(BaseModel):
    data: str
    format: str
    width: str
    height: str
    description: str


class ChatStreamedImageResponse(BaseModel):
    request_id: str
    type: ResponseTypes = ResponseTypes.STREAMED_IMAGE
    sequence: int
    last_chunk: bool
    content: ChatStreamedImageContent


class ChatStreamedMetadataContent(BaseModel):
    model: str
    role: MessageRoles
    timestamp: int


class ChatStreamedMetadataResponse(BaseModel):
    request_id: str
    type: ResponseTypes = ResponseTypes.STREAMED_METADATA
    sequence: int
    last_chunk: bool
    content: ChatStreamedMetadataContent
