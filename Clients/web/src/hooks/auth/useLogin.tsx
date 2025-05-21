import { useMutation } from "@tanstack/react-query";
import { loginUser } from "../../services/authService";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { AuthData, AuthResponse, OnError, OnSuccess } from "../../types/authTypes";

export const useLogin = (onSuccess: OnSuccess<AuthResponse>, onError: OnError) => {
  return useMutation<
    ApiResponseData<AuthResponse>, // TData
    ApiResponseError, // TError
    AuthData, // TVariables
    unknown // TContext
  >({
    mutationKey: ["login-user"],
    mutationFn: loginUser,
    onSuccess,
    onError,
  });
};
