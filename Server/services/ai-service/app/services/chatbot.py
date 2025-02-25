import json
from datetime import datetime, timezone

from app.services import client  # type: ignore
from app.utils.responses_creator import (
    create_chat_response,
    create_chat_response_message,
    create_chat_streamed_response,
)

from typing import AsyncIterable, List, Union
from openai import AsyncStream
from openai.types.chat import (
    ChatCompletion,
    ChatCompletionChunk,
    ChatCompletionUserMessageParam,
    ChatCompletionSystemMessageParam,
    ChatCompletionContentPartParam,
)


async def get_streamed_chatbot_response(
    message: str, image_b64: str | None = None
) -> AsyncIterable[str]:
    user_content: List[ChatCompletionContentPartParam] = [
        {"type": "text", "text": message}
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
        "content": "You are a helpful assistant that only discusses recipes. Avoid other topics.",
    }

    stream: AsyncStream[ChatCompletionChunk] = await client.chat.completions.create(
        model="llama3.2",
        messages=[system_message, user_message],
        stream=True,
    )

    yield json.dumps(
        create_chat_streamed_response(
            model="llama3.2",
            role="assistant",
            timestamp=str(int(round(datetime.now(timezone.utc).timestamp()))),
            images=[],
            tool_calls=[],
        )
    )

    initial_chunk = await anext(stream, None)
    if initial_chunk is not None:
        yield str(initial_chunk.choices[0].delta.content)

    async for chunk in stream:
        if chunk is not None:
            yield str(chunk.choices[0].delta.content)


async def get_chatbot_response(message: str, image_b64: str | None = None) -> object:
    user_content: List[ChatCompletionContentPartParam] = [
        {"type": "text", "text": message}
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
        "content": "You are a helpful assistant that only discusses recipes. Avoid other topics.",
    }

    response: ChatCompletion = await client.chat.completions.create(
        model="llama3.2",
        messages=[system_message, user_message],
    )

    choice = response.choices[0]
    message_data = choice.message

    return create_chat_response(
        model=response.model,
        timestamp=str(
            int(
                round(
                    datetime.fromtimestamp(response.created, timezone.utc).timestamp()
                )
            )
        ),
        message=create_chat_response_message(
            role=message_data.role,
            content=str(message_data.content),
            images=[],
            tool_calls=[
                str(tool_call) for tool_call in (message_data.tool_calls or [])
            ],
        ),
    )
