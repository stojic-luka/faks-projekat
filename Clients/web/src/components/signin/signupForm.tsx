import { useCallback, useState } from "react";

import EmailSvg from "../../assets/svg/email.svg?react";
import UsernameSvg from "../../assets/svg/username.svg?react";
import PasswordSvg from "../../assets/svg/password.svg?react";
import OpenEyeSvg from "../../assets/svg/open_eye.svg?react";
import ClosedEyeSvg from "../../assets/svg/closed_eye.svg?react";
import { useAuth } from "../../contexts/useAuth";

const SignupForm = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    email: "",
    username: "",
    password: "",
  });

  const {
    signup: { mutate: signupMutate, isPending: isSignupPending, isError: isSignupError, error: signupError },
  } = useAuth();

  const handleChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevForm) => ({ ...prevForm, [name]: value }));
  }, []);

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      signupMutate({ username: formData.email, password: formData.password });
    },
    [formData, signupMutate]
  );

  return (
    <div className="flex w-full h-full">
      <form onSubmit={handleSubmit} className="flex flex-col items-center w-full gap-5 mx-16 my-auto">
        <div className="flex flex-col gap-5 w-full px-6">
          <div className="flex items-center bg-[#282828] rounded-md p-2">
            <span className="mr-2">
              <EmailSvg className="m-auto icon-fill-light" />
            </span>
            <input
              type="email"
              name="email"
              placeholder="Email"
              className="bg-transparent outline-none border-0 w-full text-white autofill:border-cyan-400"
              onChange={handleChange}
            />
          </div>
          <div className="flex items-center bg-[#282828] rounded-md p-2">
            <span className="mr-2">
              <UsernameSvg className="m-auto icon-stroke-light" />
            </span>
            <input
              type="text"
              name="username"
              placeholder="Username"
              className="bg-transparent outline-none border-0 w-full text-white autofill:bg-transparent"
              onChange={handleChange}
            />
          </div>
          <div className="flex items-center bg-[#282828] rounded-md p-2">
            <span className="mr-2">
              <PasswordSvg className="m-auto icon-stroke-light [&>circle]:icon-fill-light" />
            </span>
            <input
              type={showPassword ? "text" : "password"}
              name="password"
              placeholder="Password"
              className="bg-transparent outline-none border-0 w-full text-white autofill:bg-transparent"
              onChange={handleChange}
            />
            <button type="button" onClick={() => setShowPassword(!showPassword)} className="text-gray-400">
              <div className="my-auto">
                {showPassword ? <OpenEyeSvg className="icon-fill-light" /> : <ClosedEyeSvg className="icon-fill-light" />}
              </div>
            </button>
          </div>
        </div>
        {isSignupError && <span className="text-red-600">{`${signupError.error.code}: ${signupError.error.message}`}</span>}
        <button type="submit" className="bg-blue-600 py-2 px-5 rounded-md font-bold text-white mt-2" disabled={isSignupPending}>
          Sign Up
        </button>
      </form>
    </div>
  );
};

export default SignupForm;
