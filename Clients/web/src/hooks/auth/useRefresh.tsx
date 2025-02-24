import { useMutation } from "@tanstack/react-query";
import { refreshToken } from "../../services/authService";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { OnError, OnSuccess, RefreshResponse } from "../../types/authTypes";

export const useRefresh = (onSuccess: OnSuccess<RefreshResponse>, onError: OnError) => {
  return useMutation<
    ApiResponseData<RefreshResponse>, // TData
    ApiResponseError, // TError
    void, // TVariables
    unknown // TContext
  >({
    mutationKey: ["refresh-token"],
    mutationFn: refreshToken,
    onSuccess,
    onError,
  });
};
