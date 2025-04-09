export enum ResponseTypes {
  USER = "USER",
  COMPLETE = "COMPLETE",
  STREAMED_METADATA = "STREAMED_METADATA",
  STREAMED_TEXT = "STREAMED_TEXT",
  STREAMED_IMAGE = "STREAMED_IMAGE",
}

export enum RoleTypes {
  USER = "USER",
  ASSISTANT = "ASSISTANT",
}

export enum MessageStatus {
  SENT = "SENT",
  DONE = "DONE",
  STREAMING = "STREAMING",
  ERROR = "ERROR",
}
