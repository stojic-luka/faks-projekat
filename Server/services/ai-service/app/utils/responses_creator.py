from random import randint
from typing import Optional, List, Dict
from app.enum.response_types import ResponseTypes


def create_chat_response_message(
    role: str,
    content: str,
    images: Optional[List[str]] = None,
    tool_calls: Optional[List[str]] = None,
) -> Dict[str, Optional[List[str]] | str]:
    return {
        "role": role,
        "content": content,
        "images": images if images is not None else [],
        "tool_calls": tool_calls if tool_calls is not None else [],
    }


def create_chat_response(
    model: str,
    timestamp: str,
    message: Dict[str, Optional[List[str]] | str],
) -> Dict[str, str | bool | Dict[str, Optional[List[str]] | str]]:
    return {
        "id": str(randint(0, 100)),
        "type": ResponseTypes.COMPLETE,
        "model": model,
        "timestamp": timestamp,
        "message": message,
    }


def create_chat_streamed_response(
    model: str,
    role: str,
    timestamp: str,
    images: Optional[List[str]] = None,
    tool_calls: Optional[List[str]] = None,
) -> Dict[str, str | List[str]]:
    return {
        "id": str(randint(0, 100)),
        "type": ResponseTypes.STREAMED,
        "role": role,
        "model": model,
        "timestamp": timestamp,
        "images": images if images is not None else [],
        "tool_calls": tool_calls if tool_calls is not None else [],
    }
