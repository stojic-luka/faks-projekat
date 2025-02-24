import { createContext } from "react";
import { ChatSettingsContextType } from "./types";

export const ChatSettingsContext = createContext<ChatSettingsContextType>(null as unknown as ChatSettingsContextType);
