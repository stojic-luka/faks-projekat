from enum import Enum  # type: ignore


class MessageRoles(str, Enum):
    ASSISTANT = "ASSISTANT"
    USER = "USER"
