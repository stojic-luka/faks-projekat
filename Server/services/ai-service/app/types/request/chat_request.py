from pydantic import BaseModel

from typing import Optional


class ChatRequest(BaseModel):
    model: str
    prompt: str
    request_id: str
    streamed: Optional[bool] = False
    imageb64: Optional[str] = None
