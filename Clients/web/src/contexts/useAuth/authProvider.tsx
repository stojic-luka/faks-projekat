import { ReactNode, useCallback, useEffect, useState } from "react";
import { AuthContext } from "./authContext";
import { User, UserRoles } from "../../types/userTypes";
import { AuthContextType } from "./types";
import { useAuthHelpers } from "./useAuthHelpers";
import { useFetchUser } from "../../hooks/user";
import { useQueryClient } from "@tanstack/react-query";

interface Props {
  children: ReactNode;
}
export const AuthProvider = ({ children }: Props) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string>("");
  const queryClient = useQueryClient();

  const { data: userData, isError: isErrorUser, refetch: refetchUser } = useFetchUser();

  console.log(user);

  const logout = useCallback(() => {
    localStorage.removeItem("token");
    setUser(null);
    setToken("");
    queryClient.invalidateQueries({ queryKey: ["fetch-user"] });
  }, [queryClient]);

  useEffect(() => {
    const savedToken = localStorage.getItem("token");
    if (savedToken) {
      setToken(savedToken);
      refetchUser();
    }
  }, [refetchUser]);

  useEffect(() => {
    if (userData) {
      setUser(userData);
    } else if (isErrorUser) {
      logout();
    }
  }, [userData, isErrorUser, logout]);

  const { login, signup, refresh } = useAuthHelpers(setUser, setToken, logout);

  const contextValue: AuthContextType = {
    user,
    setUser,
    token,
    login,
    signup,
    refresh,
    logout,
    isAuthenticated: !!user,
    isAdmin: user ? user.roles.includes(UserRoles.ADMIN) : false,
  };

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};
