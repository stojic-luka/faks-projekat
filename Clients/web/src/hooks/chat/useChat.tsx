import { useMutation, UseMutationResult } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { getAiResponse, getStreamedAiResponse } from "../../services/chatService";
import { ChatResponse, UserChatRequestBody } from "../../types/chatTypes";
import { useAuth } from "../../contexts/useAuth";
import { useRef } from "react";

type ApiResponse<T extends boolean> = T extends true ? AsyncGenerator<string, never, void> : ApiResponseData<ChatResponse>;

export const useChat = <T extends boolean>(
  stream: T
): UseMutationResult<ApiResponse<T>, ApiResponseError, UserChatRequestBody, unknown> & {
  abort: { (reason?: unknown): void } | undefined;
} => {
  const { token } = useAuth();
  const abortControllerRef = useRef<AbortController | null>(null);

  const mutation = useMutation<
    ApiResponse<T>, // TData
    ApiResponseError, // TError
    UserChatRequestBody, // TVariables
    unknown // TContext
  >({
    mutationKey: ["chat-response"],
    mutationFn: async (userInput: UserChatRequestBody) => {
      const controller = abortControllerRef.current;
      if (!controller) throw new Error("AbortController is not initialized");

      const { signal } = controller;

      if (stream) {
        return getStreamedAiResponse(userInput, token, signal) as ApiResponse<T>;
      } else {
        return (await getAiResponse(userInput, signal)) as ApiResponse<T>;
      }
    },
    onMutate: () => {
      const controller = new AbortController();
      abortControllerRef.current = controller;
      return { abort: controller.abort };
    },
    onError: () => {
      if (abortControllerRef.current) {
        abortControllerRef.current.abort();
      }
    },
  });

  return { ...mutation, abort: abortControllerRef.current?.abort };
};
