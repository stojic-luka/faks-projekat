from pydantic import BaseModel

from typing import Optional


class ChatRequest(BaseModel):
    message: str
    streamed: Optional[bool] = False
    imageb64: Optional[str] = None
