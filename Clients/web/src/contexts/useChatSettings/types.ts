export interface ChatSettingsContextType {
  availableModels: string[] | undefined;
  handleChangeModel: (model: string) => void;
  selectedModel: string;
  streamResponse: boolean;
  setStreamResponse: (value: boolean) => void;
  clearChatFunction: () => void;
  registerClearChatFunction: (value: () => void) => void;
}
