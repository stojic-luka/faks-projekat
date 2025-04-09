import { MessageStatus } from "./chatEnums";

export interface MessageContent {
  text: string;
  images: ChatImage[];
}

export interface ChatImage {
  data: string;
  format: string;
  width: number;
  height: number;
  description: string;
}

export interface ChatMetadata {
  role: string;
  model: string;
  timestamp: number;
  user_id: string;
}

export type ChatMessage = ChatMetadata & {
  id: string;
  type: string;
  status: MessageStatus;
  content: MessageContent;
};
