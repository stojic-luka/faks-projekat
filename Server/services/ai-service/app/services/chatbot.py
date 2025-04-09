import os
from datetime import datetime, timezone

from typing import Any, Coroutine, List, Union
from openai import AsyncStream
from openai.types.chat import (
    ChatCompletion,
    ChatCompletionChunk,
    ChatCompletionUserMessageParam,
    ChatCompletionSystemMessageParam,
    ChatCompletionContentPartParam,
)

from app.services import client  # type: ignore
from app.types.response.chat_complete_response import (  # type: ignore
    ChatResponse,
    ChatResponseMessage,
    ChatResponseMessageImage,
)
from app.types.response.chat_streamed_response import (  # type: ignore
    ChatStreamedMetadataResponse,
    ChatStreamedMetadataContent,
)
from app.enum import MessageRoles, ResponseTypes  # type: ignore


def get_streamed_chatbot_response(
    request_id: str, user_id: str, model: str, prompt: str, image_b64: str | None = None
) -> tuple[
    ChatStreamedMetadataResponse,
    Coroutine[Any, Any, AsyncStream[ChatCompletionChunk]],
]:
    user_content: List[ChatCompletionContentPartParam] = [
        {"type": "text", "text": prompt}
    ]

    if image_b64 is not None:
        user_content.append(
            {
                "type": "image_url",
                "image_url": {"url": f"data:image/jpeg;base64,{image_b64}"},
            }
        )

    user_message: Union[ChatCompletionUserMessageParam] = {
        "role": "user",
        "content": user_content,
    }

    system_message: Union[ChatCompletionSystemMessageParam] = {
        "role": "system",
        # "content": "You are a helpful assistant that only discusses recipes. Avoid other topics.",
        "content": "You are a helpful assistant.",
    }

    return (
        ChatStreamedMetadataResponse(
            request_id=request_id,
            type=ResponseTypes.STREAMED_METADATA,
            sequence=0,
            last_chunk=False,
            content=ChatStreamedMetadataContent(
                user_id=user_id,
                model=model,
                role=MessageRoles.ASSISTANT,
                timestamp=0,
            ),
        ),
        client.chat.completions.create(
            model=model,
            messages=[system_message, user_message],
            stream=True,
        ),
    )


async def get_chatbot_response(
    request_id: str, user_id: str, model: str, prompt: str, image_b64: str | None = None
) -> ChatResponse:
    user_content: List[ChatCompletionContentPartParam] = [
        {"type": "text", "text": prompt}
    ]

    if image_b64 is not None:
        user_content.append(
            {
                "type": "image_url",
                "image_url": {"url": f"data:image/jpeg;base64,{image_b64}"},
            }
        )

    user_message: Union[ChatCompletionUserMessageParam] = {
        "role": "user",
        "content": user_content,
    }

    system_message: Union[ChatCompletionSystemMessageParam] = {
        "role": "system",
        # "content": "You are a helpful assistant that only discusses recipes. Avoid other topics.",
        "content": "You are a helpful assistant",
    }

    response: ChatCompletion = await client.chat.completions.create(
        model=model,
        messages=[system_message, user_message],
    )

    return ChatResponse(
        request_id=request_id,
        user_id=user_id,
        type=ResponseTypes.COMPLETE,
        role=MessageRoles.ASSISTANT,
        model=model,
        timestamp=str(
            int(
                round(
                    datetime.fromtimestamp(response.created, timezone.utc).timestamp()
                )
            )
        ),
        content=ChatResponseMessage(
            text=str(response.choices[0].message.content),
            images=[],
        ),
    )
