import { useContext } from "react";
import { ChatSettingsContext } from "./chatSettingsContext";

export const useChatSettings = () => useContext(ChatSettingsContext);
