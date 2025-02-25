const LoadingMessageBubble = () => {
  return (
    <div className="flex w-full whitespace-pre-wrap">
      <div className="flex w-full justify-start">
        <div className="flex flex-col max-w-full">
          <div className="ml-3 mb-2">
            <p className="text-xs text-gray-400">MeLLama</p>
          </div>
          <div className="leading-relaxed pr-4 pb-3 rounded-2xl text-gray-800 bot-reply">
            <div className="flex space-x-2 justify-center items-center dark:invert mt-3">
              <div className="h-1.5 w-1.5 bg-black rounded-full animate-bigger-bounce [animation-delay:-0.3s]"></div>
              <div className="h-1.5 w-1.5 bg-black rounded-full animate-bigger-bounce [animation-delay:-0.15s]"></div>
              <div className="h-1.5 w-1.5 bg-black rounded-full animate-bigger-bounce"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoadingMessageBubble;
