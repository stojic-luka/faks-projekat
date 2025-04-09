import { ReactNode, useEffect, useState } from "react";
import { ChatSettingsContext } from "./chatSettingsContext";
import { ChatSettingsContextType } from "./types";
import { useAvailableModels } from "../../hooks/chat/";

interface Props {
  children: ReactNode;
}
export const ChatSettingsProvider = ({ children }: Props) => {
  const [selectedModel, setSelectedModel] = useState<string>("");
  const [streamResponse, setStreamResponse] = useState(true);
  const [clearChatFunction, setClearChatFunction] = useState<() => void>(() => {});
  const { data: availableModels } = useAvailableModels();

  const handleChangeModel = (model: string) => {
    if (availableModels?.includes(model)) {
      localStorage.setItem("preferred_model", model);
      setSelectedModel(model);
    }
  };

  useEffect(() => {
    if (!availableModels?.length) return;
    const preferredModel = localStorage.getItem("preferred_model");
    setSelectedModel(preferredModel && availableModels.includes(preferredModel) ? preferredModel : availableModels[0]);
  }, [availableModels]);

  const contextValue: ChatSettingsContextType = {
    availableModels,
    handleChangeModel,
    selectedModel,
    streamResponse,
    setStreamResponse,
    clearChatFunction,
    registerClearChatFunction: setClearChatFunction,
  };

  return <ChatSettingsContext.Provider value={contextValue}>{children}</ChatSettingsContext.Provider>;
};
