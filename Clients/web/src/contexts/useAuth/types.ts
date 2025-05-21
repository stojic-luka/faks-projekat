import { UseMutationResult } from "@tanstack/react-query";
import { AuthData, AuthResponse, RefreshResponse } from "../../types/authTypes";
import { User } from "../../types/userTypes";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";

export interface AuthContextType {
  user: User | null;
  setUser: (user: User | null) => void;
  token: string;
  login: UseMutationResult<ApiResponseData<AuthResponse>, ApiResponseError, AuthData, unknown>;
  signup: UseMutationResult<ApiResponseData<AuthResponse>, ApiResponseError, AuthData, unknown>;
  logout: () => void;
  refresh: UseMutationResult<ApiResponseData<RefreshResponse>, ApiResponseError, void, unknown>;
  isAuthenticated: boolean;
  isAdmin: boolean;
}
