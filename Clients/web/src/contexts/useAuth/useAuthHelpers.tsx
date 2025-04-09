import { useLogin, useRefresh, useSignup } from "../../hooks/auth";
import { User } from "../../types/userTypes";

export const useAuthHelpers = (setUser: (user: User | null) => void, setToken: (token: string) => void, logout: () => void) => {
  return {
    login: useLogin(
      (data) => {
        setUser(data.data.user);
        setToken(data.data.token);
        localStorage.setItem("token", data.data.token);
      },
      () => {}
    ),
    signup: useSignup(
      (data) => {
        setUser(data.data.user);
        setToken(data.data.token);
        localStorage.setItem("token", data.data.token);
      },
      () => {}
    ),
    refresh: useRefresh(
      (data) => setToken(data.data.token),
      () => logout()
    ),
  };
};
