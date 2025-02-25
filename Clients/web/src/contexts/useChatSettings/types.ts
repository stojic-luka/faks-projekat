export interface ChatSettingsContextType {
  streamResponse: boolean;
  setStreamResponse: (value: boolean) => void;
  clearChatFunction: () => void;
  registerClearChatFunction: (value: () => void) => void;
}
