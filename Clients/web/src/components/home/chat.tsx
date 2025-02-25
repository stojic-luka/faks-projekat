import { useCallback, useEffect, useRef, useState } from "react";
import Message from "./message";
import ChatTextBox from "./chatTextBox";
import { useChat } from "../../hooks/chat";
import LoadingMessageBubble from "./loadingMessage";
import { useChatSettings } from "../../contexts/useChatSettings";
import { ChatMessage, ChatResponse, MessageDeliveryStatus, RoleTypes, StreamedChatResponse } from "../../types/chatTypes";
import { ErrorMessage } from "../shared";

const Chat = () => {
  const { streamResponse, registerClearChatFunction } = useChatSettings();
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [isResponding, setIsResponding] = useState(false);
  const messagesContainerRef = useRef<HTMLDivElement>(null);

  const { mutateAsync: sendMessage, isPending: isPendingChat, isError: isErrorChat, error: chatError, abort: chatAbort } = useChat(streamResponse);

  useEffect(() => {
    if (messagesContainerRef.current) {
      messagesContainerRef.current.scrollTop = messagesContainerRef.current.scrollHeight;
    }
  }, [messages]);

  useEffect(() => {
    setIsResponding(isPendingChat);
  }, [isPendingChat]);

  useEffect(() => {
    registerClearChatFunction(() => () => {
      setMessages([]);
    });
  }, [registerClearChatFunction]);

  const handleSendMessage = useCallback(
    async (message: string) => {
      const userMessageObject: ChatMessage = {
        id: "",
        role: RoleTypes.USER,
        content: message,
        images: [],
        tool_calls: [],
        timestamp: Date.now(),
        status: MessageDeliveryStatus.PENDING,
      };
      setMessages((prevMessages) => [...prevMessages, userMessageObject]); // pass userMessageObject by reference
      setIsResponding(true);
      const response = await sendMessage({ message });
      userMessageObject.status = MessageDeliveryStatus.DELIVERED;

      if (streamResponse) {
        const stream = response as AsyncGenerator<string, never, void>;
        let botMessageId: string | null = null;

        for await (const chunk of stream) {
          if (botMessageId === null) {
            setIsResponding(false);
            const initialChunk = JSON.parse(chunk as string) as StreamedChatResponse;
            botMessageId = initialChunk.id;

            const botMessageObject: ChatMessage = {
              id: botMessageId,
              role: RoleTypes.ASSISTANT,
              content: "",
              images: initialChunk.images,
              tool_calls: initialChunk.tool_calls,
              timestamp: initialChunk.timestamp,
              status: MessageDeliveryStatus.PENDING,
            };

            setMessages((prevMessages) => [...prevMessages, botMessageObject]);
            continue;
          }

          setMessages((prevMessages) =>
            prevMessages.map((message) => (message.id === botMessageId ? { ...message, content: message.content + (chunk as string) } : message))
          );
        }

        if (botMessageId) {
          setMessages((prevMessages) =>
            prevMessages.map((message) => (message.id === botMessageId ? { ...message, status: MessageDeliveryStatus.DELIVERED } : message))
          );
        }

        botMessageId = null;
      } else {
        // const responseMessage = response asur ChatResponse;
        const responseMessage = response as unknown as ChatResponse;
        setMessages((prevMessages) => [
          ...prevMessages,
          // {
          //   id: responseMessage.data.id,
          //   role: responseMessage.data.message.role,
          //   content: "",
          //   images: responseMessage.data.message.images,
          //   tool_calls: responseMessage.data.message.tool_calls,
          //   timestamp: responseMessage.data.timestamp,
          //   status: MessageDeliveryStatus.DELIVERED,
          // } as ChatMessage,
          {
            id: responseMessage.id,
            role: responseMessage.message.role,
            content: responseMessage.message.content,
            images: responseMessage.message.images,
            tool_calls: responseMessage.message.tool_calls,
            timestamp: responseMessage.timestamp,
            status: MessageDeliveryStatus.DELIVERED,
          } as ChatMessage,
        ]);
      }
    },
    [sendMessage, streamResponse]
  );

  return (
    <div className="flex justify-center h-full w-full dark:bg-[#212121] py-2">
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
          {messages.map((msg, index) => (
            <Message key={index} message={msg} />
          ))}
          {isResponding && <LoadingMessageBubble />}
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
