import { ReactNode, useMemo, useState } from "react";
import { ChatSettingsContext } from "./chatSettingsContext";
import { ChatSettingsContextType } from "./types";

interface Props {
  children: ReactNode;
}
export const ChatSettingsProvider = ({ children }: Props) => {
  const [streamResponse, setStreamResponse] = useState(false);
  const [clearChatFunction, setClearChatFunction] = useState<() => void>(() => {});

  const contextValue: ChatSettingsContextType = useMemo(
    () => ({
      streamResponse,
      setStreamResponse,
      clearChatFunction,
      registerClearChatFunction: setClearChatFunction,
    }),
    [clearChatFunction, streamResponse]
  );

  return <ChatSettingsContext.Provider value={contextValue}>{children}</ChatSettingsContext.Provider>;
};
