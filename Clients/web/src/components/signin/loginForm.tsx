import { useCallback, useState } from "react";

import EmailSvg from "../../assets/svg/email.svg?react";
import PasswordSvg from "../../assets/svg/password.svg?react";
import OpenEyeSvg from "../../assets/svg/open_eye.svg?react";
import ClosedEyeSvg from "../../assets/svg/closed_eye.svg?react";
import { useAuth } from "../../contexts/useAuth";
import { useOAuthParams } from "../../hooks/auth/useOAuthParams";
import { AuthData } from "../../types/authTypes";

const LoginForm = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const oauth = useOAuthParams();
  const {
    login: { mutate: loginMutate, isPending: isLoginPending, isError: isLoginError, error: loginError },
  } = useAuth();

  const handleChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevForm) => ({ ...prevForm, [name]: value }));
  }, []);

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();

      const loginData: AuthData = {
        credentials: { username: formData.email, password: formData.password },
      };
      if (oauth && oauth.client_id && oauth.redirect_uri && oauth.code_challenge && oauth.code_challenge_method) {
        loginData.params = {
          client_id: oauth.client_id,
          redirect_uri: oauth.redirect_uri,
          code_challenge: oauth.code_challenge,
          code_challenge_method: oauth.code_challenge_method,
        };
        if (oauth.scope) loginData.params.scope = oauth.scope;
        if (oauth.state) loginData.params.state = oauth.state;
      }
      loginMutate(loginData);
    },
    [formData, loginMutate, oauth]
  );

  return (
    <div className="flex w-full h-full">
      <form onSubmit={handleSubmit} className="flex flex-col items-center w-full gap-5 mx-16 my-auto">
        <div className="flex flex-col gap-5 w-full px-6">
          <div className="flex items-center bg-[#282828] rounded-md p-2 border-gray">
            <span className="mr-2">
              <EmailSvg className="m-auto icon-fill-light" />
            </span>
            <input
              type="text"
              name="email"
              placeholder="Email or username"
              className="bg-transparent outline-none border-0 w-full text-white"
              onChange={handleChange}
            />
          </div>
          <div className="flex flex-col">
            <div className="flex items-center bg-[#282828] rounded-md p-2">
              <span className="mr-2">
                <PasswordSvg className="m-auto icon-stroke-light [&>circle]:icon-fill-light" />
              </span>
              <input
                type={showPassword ? "text" : "password"}
                name="password"
                placeholder="Password"
                className="bg-transparent outline-none border-0 w-full text-white"
                onChange={handleChange}
              />
              <button type="button" onClick={() => setShowPassword(!showPassword)} className="text-gray-400">
                <div className="my-auto">
                  {showPassword ? <OpenEyeSvg className="icon-fill-light" /> : <ClosedEyeSvg className="icon-fill-light" />}
                </div>
              </button>
            </div>
            <a href="#" className="text-sm mt-2 text-gray-400 underline">
              Forgot your password?
            </a>
          </div>
        </div>
        {isLoginError && loginError && <span className="text-red-600">{`${loginError.error.code}: ${loginError.error.message}`}</span>}
        <button type="submit" className="bg-blue-600 py-2 px-5 rounded-md font-bold text-white" disabled={isLoginPending}>
          Login
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
