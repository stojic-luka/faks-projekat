import { useLogin, useRefresh, useSignup } from "../../hooks/auth";
import { User } from "../../types/userTypes";

export const useAuthHelpers = (setUser: (user: User | null) => void, setToken: (token: string) => void, logout: () => void) => {
  const { mutate: login } = useLogin(
    (data) => {
      setUser(data.data.user);
      setToken(data.data.token);
    },
    () => {}
  );

  const { mutate: signup } = useSignup(
    (data) => {
      setUser(data.data.user);
      setToken(data.data.token);
    },
    () => {}
  );

  const { mutate: refresh } = useRefresh(
    (data) => setToken(data.data.token),
    () => logout()
  );

  return { login, signup, refresh };
};
