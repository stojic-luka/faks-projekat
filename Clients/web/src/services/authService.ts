import authenticatedAxios from "../api/authenticatedAxios";
import { AuthCredentials, AuthResponse, RefreshResponse } from "../types/authTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const loginUser = async (credentials: AuthCredentials) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/login", credentials, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const signupUser = async (credentials: AuthCredentials) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/signup", credentials, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const refreshToken = async () => {
  const response = await authenticatedAxios.get<ApiResponse<RefreshResponse>>("/api/refresh", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<RefreshResponse>;
};
