<<<<<<< Updated upstream
import authenticatedAxios from "../api/authenticatedAxios";
import { AuthCredentials, AuthResponse, RefreshResponse } from "../types/authTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const loginUser = async (credentials: AuthCredentials) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/v1/login", credentials, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const signupUser = async (credentials: AuthCredentials) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/v1/signup", credentials, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const refreshToken = async () => {
  const response = await authenticatedAxios.get<ApiResponse<RefreshResponse>>("/api/v1/refresh", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<RefreshResponse>;
};
=======
import authenticatedAxios from "../api/authenticatedAxios";
import { AuthCredentials, AuthResponse, RefreshResponse } from "../types/authTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const loginUser = async (credentials: AuthCredentials) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/v1/login", credentials, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const signupUser = async (credentials: AuthCredentials) => {
  const response = await authenticatedAxios.post<ApiResponse<AuthResponse>>("/api/v1/signup", credentials, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<AuthResponse>;
};

export const refreshToken = async () => {
  const response = await authenticatedAxios.get<ApiResponse<RefreshResponse>>("/api/v1/refresh", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<RefreshResponse>;
};
>>>>>>> Stashed changes
