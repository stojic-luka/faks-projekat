import { ResponseTypes } from "./chatEnums";
import { ChatImage, ChatMetadata, MessageContent } from "./chatModels";

interface AiChatResponse {
  id: string;
  type: ResponseTypes;
}

export interface AiChatCompleteResponse extends AiChatResponse, ChatMetadata {
  content: MessageContent;
}

export interface AiChatStreamedResponse<T> extends AiChatResponse {
  sequence: number;
  lastChunk: boolean;
  content: T;
}

export type AiChatStreamedImageResponse = AiChatStreamedResponse<ChatImage>;
export type AiChatStreamedMetadataResponse = AiChatStreamedResponse<ChatMetadata>;
export type AiChatStreamedTextResponse = AiChatStreamedResponse<string>;
