enum ResponseTypes {
  COMPLETE = "complete",
  STREAMED = "streamed",
}

export enum RoleTypes {
  USER = "user",
  ASSISTANT = "assistant",
}

export enum MessageDeliveryStatus {
  PENDING = "pending",
  DELIVERED = "delivered",
  FAILED = "failed",
}

type WithId = { id: string };
type WithType = { type: ResponseTypes };
type WithRole = { role: RoleTypes };
type WithModel = { model: string };
type WithContent = { content: string };
type WithTimestamp = { timestamp: number };
type WithImagesAndToolCalls = { images: string[]; tool_calls: string[] };

export type StreamedChatResponse = WithId & WithType & WithRole & WithModel & WithTimestamp & WithImagesAndToolCalls;
// export type StreamedChatResponseChunk = WithType & WithContent & WithDone & WithCreateAt;

export type ChatResponseMessage = WithRole & WithContent & WithImagesAndToolCalls;
export type ChatResponse = WithId & WithType & WithModel & WithTimestamp & { message: ChatResponseMessage };

export type ChatMessage = WithId & WithRole & WithContent & WithImagesAndToolCalls & WithTimestamp & { status: MessageDeliveryStatus };

export interface UserChatRequestBody {
  message: string;
}
