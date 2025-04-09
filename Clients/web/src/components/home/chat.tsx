import { useCallback, useEffect, useRef, useState } from "react";
import Message from "./message";
import ChatTextBox from "./chatTextBox";
import { useChat, useChatIndexedDB } from "../../hooks/chat";
import LoadingMessageBubble from "./loadingMessage";
import { useChatSettings } from "../../contexts/useChatSettings";
import { ErrorMessage } from "../shared";
import { ApiResponseData } from "../../types/responseTypes";
import { ChatMessage } from "../../types/chatTypes/chatModels";
import { MessageStatus, ResponseTypes, RoleTypes } from "../../types/chatTypes/chatEnums";
import {
  AiChatCompleteResponse,
  AiChatStreamedImageResponse,
  AiChatStreamedMetadataResponse,
  AiChatStreamedTextResponse,
} from "../../types/chatTypes/chatResponseTypes";
import { useAuth } from "../../contexts/useAuth";

const Chat = () => {
  const { streamResponse, registerClearChatFunction, selectedModel } = useChatSettings();
  const { addMessage, getAllMessages, updateMessage, deleteAllMessages } = useChatIndexedDB();
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const { user } = useAuth();
  const messagesContainerRef = useRef<HTMLDivElement>(null);

  const { mutateAsync: sendMessage, isPending: isPendingChat, isError: isErrorChat, error: chatError, abort: chatAbort } = useChat(streamResponse);

  useEffect(() => {
    (async () => {
      try {
        const storedMessages = await getAllMessages(user!.id);
        if (storedMessages?.length) {
          setMessages(storedMessages);
        }
      } catch (error) {
        console.error("Error fetching messages:", error);
      }
    })();
  }, [getAllMessages, user]);

  useEffect(() => {
    messagesContainerRef.current?.scrollTo({
      top: messagesContainerRef.current.scrollHeight,
      behavior: "smooth",
    });
  }, [messages]);

  useEffect(() => {
    registerClearChatFunction(() => () => {
      setMessages([]);
      deleteAllMessages(user!.id);
    });
  }, [deleteAllMessages, registerClearChatFunction, user]);

  const updateMessageState = useCallback(
    (id: string, updater: (msg: ChatMessage) => ChatMessage) => {
      setMessages((prev) => {
        const index = prev.findIndex((msg) => msg.id === id);
        if (index === -1) return prev;
        const updatedMsg = updater(prev[index]);
        const newMessages = [...prev];
        newMessages[index] = updatedMsg;
        updateMessage(updatedMsg);
        return newMessages;
      });
    },
    [updateMessage]
  );

  const processStreamChunk = useCallback(
    (
      parsed: AiChatStreamedMetadataResponse | AiChatStreamedImageResponse | AiChatStreamedTextResponse,
      botMessageRef: { current: ChatMessage | null }
    ) => {
      if (!botMessageRef.current) {
        const metadata = parsed as AiChatStreamedMetadataResponse;
        botMessageRef.current = {
          id: metadata.id,
          user_id: metadata.content.user_id,
          role: metadata.content.role,
          type: metadata.type,
          content: { text: "", images: [] },
          model: metadata.content.model,
          status: MessageStatus.STREAMING,
          timestamp: metadata.content.timestamp,
        };
        setMessages((prev) => [...prev, botMessageRef.current!]);
        addMessage(botMessageRef.current!);
        return;
      }

      const dataChunk = parsed as AiChatStreamedImageResponse | AiChatStreamedTextResponse;
      updateMessageState(botMessageRef.current.id, (prevMsg) => {
        if (dataChunk.type === ResponseTypes.STREAMED_TEXT) {
          return {
            ...prevMsg,
            content: {
              ...prevMsg.content,
              text: prevMsg.content.text + (dataChunk as AiChatStreamedTextResponse).content,
            },
          };
        } else if (dataChunk.type === ResponseTypes.STREAMED_IMAGE) {
          return {
            ...prevMsg,
            content: {
              ...prevMsg.content,
              images: [...prevMsg.content.images, (dataChunk as AiChatStreamedImageResponse).content],
            },
          };
        }
        return prevMsg;
      });
    },
    [addMessage, updateMessageState]
  );

  const handleSendMessage = useCallback(
    async (message: string) => {
      const isStreamed = streamResponse;
      const userMessage: ChatMessage = {
        id: `temp-${Date.now()}`,
        user_id: user!.id,
        role: RoleTypes.USER,
        type: ResponseTypes.USER,
        content: { text: message, images: [] },
        model: selectedModel,
        status: MessageStatus.SENT,
        timestamp: Math.floor(Date.now() / 1000) - 1,
      };
      setMessages((prev) => [...prev, userMessage]);
      addMessage(userMessage);

      const botMessageRef: { current: ChatMessage | null } = { current: null };
      try {
        const response = await sendMessage({ message, image: "" });
        if (isStreamed) {
          const stream = response as AsyncGenerator<string, never, void>;
          let buffer = "";

          for await (const chunk of stream) {
            buffer += chunk;
            const parts = buffer.split(/(?<=})(?={)/);
            for (let i = 0; i < parts.length - 1; i++) {
              const parsed = JSON.parse(parts[i]);
              processStreamChunk(parsed, botMessageRef);
            }
            buffer = parts[parts.length - 1];
          }
          if (buffer.trim()) {
            const parsed = JSON.parse(buffer);
            processStreamChunk(parsed, botMessageRef);
          }
        } else {
          const completeResponse = (response as ApiResponseData<AiChatCompleteResponse>).data;
          const completedMessage: ChatMessage = {
            ...completeResponse,
            status: MessageStatus.DONE,
          };
          setMessages((prev) => [...prev, completedMessage]);
          addMessage(completedMessage);
        }
      } catch (err) {
        console.error("Send message error:", err);
        if (botMessageRef.current)
          updateMessageState(botMessageRef.current.id, (prev) => ({
            ...prev,
            status: MessageStatus.ERROR,
          }));
        else
          updateMessageState(userMessage.id, (prev) => ({
            ...prev,
            status: MessageStatus.ERROR,
          }));
      }
    },
    [addMessage, processStreamChunk, selectedModel, sendMessage, streamResponse, updateMessageState, user]
  );

  return (
    <div className="flex justify-center h-full w-full dark:bg-[#212121] pb-2">
      <div className="w-full max-w-[768px] h-full flex flex-col px-4">
        {messages.length === 0 && (
          <div className="flex flex-col mt-auto mb-12 gap-2">
            <h1 className="text-center text-4xl font-bold text-white">Welcome to MeLLama</h1>
            <h2 className="text-center text-1xl text-white">Your very own cooking AI assistant</h2>
          </div>
        )}
        <div
          ref={messagesContainerRef}
          className={`flex flex-col gap-6 mb-4 max-h-full overflow-y-auto scrollbar-redesign dark:scrollbar-redesign-dark scroll-smooth max-w-full ${
            messages.length !== 0 ? "mt-auto" : ""
          }`}
        >
          {messages.map((msg) => (
            <Message key={msg.id} message={msg} />
          ))}
          {isPendingChat && <LoadingMessageBubble />}
          {isErrorChat && <ErrorMessage message={chatError?.error} />}
        </div>
        <div className="mb-auto">
          <ChatTextBox sendMessage={handleSendMessage} disabled={isPendingChat} onAbort={chatAbort} />
        </div>
      </div>
    </div>
  );
};

export default Chat;
