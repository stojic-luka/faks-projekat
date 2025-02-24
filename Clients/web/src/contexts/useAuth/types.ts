import { AuthCredentials } from "../../types/authTypes";
import { User } from "../../types/userTypes";

export interface AuthContextType {
  user: User | null;
  setUser: (user: User | null) => void;
  token: string;
  setToken: (token: string) => void;
  login: (credentials: AuthCredentials) => void;
  signup: (credentials: AuthCredentials) => void;
  logout: () => void;
  refresh: () => void;
  isAuthenticated: boolean;
}
