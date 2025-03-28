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
type WithResponseTypes = { type: ResponseTypes };
type WithRole = { role: RoleTypes };
type WithModel = { model: string };
type WithContent = { content: string };
type WithTimestamp = { timestamp: number };
type WithImages = { images: string[] };

export type StreamedChatResponse = WithId & WithResponseTypes & WithRole & WithModel & WithTimestamp & WithImages;
// export type StreamedChatResponseChunk = WithType & WithContent & WithDone & WithCreateAt;

export type ChatResponseMessage = WithRole & WithContent & WithImages;
export type ChatResponse = WithId & WithResponseTypes & WithModel & WithTimestamp & { message: ChatResponseMessage };

export type ChatMessage = WithId & WithRole & WithContent & WithImages & WithTimestamp & { status: MessageDeliveryStatus };

export interface UserChatRequestBody {
  message: string;
}
