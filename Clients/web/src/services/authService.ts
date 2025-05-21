import authenticatedAxios from "../api/authenticatedAxios";
import { AuthData, AuthResponse, RefreshResponse } from "../types/authTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const loginUser = async (authData: AuthData) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/v1/auth/login", authData.credentials, {
    headers: {
      "Content-Type": "application/json",
    },
    params: authData.params || {},
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const signupUser = async (authData: AuthData) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/v1/auth/signup", authData.credentials, {
    headers: {
      "Content-Type": "application/json",
    },
    params: authData.params || {},
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const refreshToken = async () => {
  const response = await authenticatedAxios.get<ApiResponse<RefreshResponse>>("/api/v1/auth/refresh", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<RefreshResponse>;
};
