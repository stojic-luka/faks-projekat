import { memo } from "react";
import { useAuth } from "../../contexts/useAuth";
import { formatBotResponse } from "../../util/formatBotResponse";
import { ChatMessage, RoleTypes } from "../../types/chatTypes";

interface MessageContentProps {
  content: JSX.Element[];
}

const BotMessageBubble = ({ content }: MessageContentProps) => {
  return (
    <div className="flex w-full justify-start">
      <div className="flex flex-col max-w-full">
        <div className="ml-3 mb-2">
          <p className="text-xs text-gray-400">MeLLama</p>
        </div>
        <div className="leading-relaxed pr-4 pb-3 rounded-2xl text-gray-800 bot-reply">{content}</div>
      </div>
    </div>
  );
};

const UserMessageBubble = ({ content }: MessageContentProps) => {
  const { user } = useAuth();
  const username = user?.username || "You";

  return (
    <div className="flex w-full justify-end">
      <div className="flex flex-col max-w-full mr-2">
        <div className="ml-auto mr-3 mb-2">
          <p className="text-xs text-gray-400">{username}</p>
        </div>
        <div className="leading-relaxed px-4 py-3 rounded-2xl shadow-md bg-green-500 text-white whitespace-pre-wrap">{content}</div>
      </div>
    </div>
  );
};

const MessageBubble = memo(({ message }: { message: ChatMessage }) => {
  const formattedMessage = formatBotResponse(message.content);
  return (
    <div className="flex w-full whitespace-pre-wrap">
      {message.role === RoleTypes.ASSISTANT ? <BotMessageBubble content={formattedMessage} /> : <UserMessageBubble content={formattedMessage} />}
    </div>
  );
});

export default MessageBubble;
