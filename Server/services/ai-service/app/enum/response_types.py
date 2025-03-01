from enum import Enum


class ResponseTypes(str, Enum):
    COMPLETE = "complete"
    STREAMED = "streamed"
