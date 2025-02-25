import { useChatSettings } from "../../contexts/useChatSettings";
import { Switch } from "../utils";

import StreamDataSvg from "../../assets/svg/stream_data.svg?react";
import AddImageSvg from "../../assets/svg/add_image.svg?react";
import EraserSvg from "../../assets/svg/eraser.svg?react";

const SettingsMenu = () => {
  const { streamResponse, setStreamResponse, clearChatFunction } = useChatSettings();

  return (
    <div className="w-56 rounded-md shadow-lg border dark:border-neutral-600 bg-[#f4f4f4] dark:bg-[#2f2f2f] focus:outline-none" role="menu">
      <div className="flex flex-col py-1">
        <button
          className="flex w-auto mx-1 px-2 py-1.5 rounded-[3px] text-sm text-neutral-700 dark:text-neutral-100 hover:bg-neutral-100 dark:hover:bg-neutral-700 transition-colors duration-150"
          role="menuitem"
        >
          <StreamDataSvg className="fill-white mr-2" />
          <span className="my-auto mr-auto">Stream response</span>
          <div className="flex my-auto">
            <Switch value={streamResponse} onChange={() => setStreamResponse(!streamResponse)} />
          </div>
        </button>
        <button
          className="flex w-auto mx-1 px-2 py-1.5 rounded-[3px] text-sm text-neutral-700 dark:text-neutral-100 hover:bg-neutral-100 dark:hover:bg-neutral-700 transition-colors duration-150"
          role="menuitem"
        >
          <AddImageSvg className="stroke-white mr-2" />
          <span className="my-auto">Upload image</span>
        </button>
        <button
          className="flex w-auto mx-1 px-2 py-1.5 rounded-[3px] text-sm text-neutral-700 dark:text-neutral-100 hover:bg-neutral-100 dark:hover:bg-neutral-700 transition-colors duration-150"
          role="menuitem"
          onClick={clearChatFunction}
        >
          <EraserSvg className="mr-2" />
          <span className="my-auto">Clear chat</span>
        </button>
      </div>
    </div>
  );
};

export default SettingsMenu;
