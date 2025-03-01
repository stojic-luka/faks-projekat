from enum import Enum


class MessageRoles(str, Enum):
    ASSISTANT = "assistant"
    USER = "user"
