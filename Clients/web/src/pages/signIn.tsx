import { useState } from "react";
import { LoginForm, Overlay, SignupForm } from "../components/signin";

const SignIn = () => {
  const [isRightPanelActive, setRightPanelActive] = useState(false);
  const togglePanel = () => setRightPanelActive(!isRightPanelActive);

  return (
    <div className="h-full w-full bg-[#151515]">
      <div className="max-w-[64rem] h-full mx-auto flex flex-col items-center justify-center">
        <div className="relative bg-[#191919] rounded-xl shadow-lg max-w-5xl overflow-hidden w-full min-h-[30rem]">
          <div
            className={`absolute top-0 left-0 w-1/2 h-full ${
              isRightPanelActive ? "translate-x-0 opacity-100 z-[1]" : "translate-x-full opacity-0 z-0"
            } [transition:opacity_600ms_cubic-bezier(1,0,0,1),_transform_550ms_ease-in-out]`}
          >
            <SignupForm />
          </div>
          <div
            className={`absolute top-0 left-0 w-1/2 h-full ${
              !isRightPanelActive ? "translate-x-full opacity-100 z-[1]" : "translate-x-0 opacity-0 z-0"
            } [transition:opacity_600ms_cubic-bezier(1,0,0,1),_transform_550ms_ease-in-out]`}
          >
            <LoginForm />
          </div>
          <Overlay togglePanel={togglePanel} isActive={isRightPanelActive} />
        </div>
      </div>
    </div>
  );
};

export default SignIn;
