import { Chat } from "../components/home";
import { ChatSettingsProvider } from "../contexts/useChatSettings";

const Home = () => {
  return (
    <>
      <ChatSettingsProvider>
        <Chat />
      </ChatSettingsProvider>
    </>
  );
};

export default Home;
