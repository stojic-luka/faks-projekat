import { motion } from "framer-motion";
import { Chat } from "../components/home";
import { ChatSettingsProvider } from "../contexts/useChatSettings";

const Home = () => {
  return (
    <>
      <div className="flex justify-center h-full w-full dark:bg-[#212121]">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ duration: 0.5 }}
          className="w-full max-w-[768px] h-full flex flex-col px-4 pb-2"
        >
          <ChatSettingsProvider>
            <Chat />
          </ChatSettingsProvider>
        </motion.div>
      </div>
    </>
  );
};

export default Home;
