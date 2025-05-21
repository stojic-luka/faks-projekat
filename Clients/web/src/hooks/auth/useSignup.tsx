import { useMutation } from "@tanstack/react-query";
import { signupUser } from "../../services/authService";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { AuthData, AuthResponse, OnError, OnSuccess } from "../../types/authTypes";

export const useSignup = (onSuccess: OnSuccess<AuthResponse>, onError: OnError) => {
  return useMutation<
    ApiResponseData<AuthResponse>, // TData
    ApiResponseError, // TError
    AuthData, // TVariables
    unknown // TContext
  >({
    mutationKey: ["signup-user"],
    mutationFn: signupUser,
    onSuccess,
    onError,
  });
};
