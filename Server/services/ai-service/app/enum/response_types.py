from enum import Enum  # type: ignore


class ResponseTypes(str, Enum):
    COMPLETE = "COMPLETE"
    STREAMED_METADATA = "STREAMED_METADATA"
    STREAMED_TEXT = "STREAMED_TEXT"
    STREAMED_IMAGE = "STREAMED_IMAGE"
