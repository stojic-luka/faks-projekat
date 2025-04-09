import { useMutation, UseMutationResult } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { getAiResponse, getStreamedAiResponse } from "../../services/chatService";
import { ChatDataParams, ChatRequestBody } from "../../types/chatTypes/chatRequestTypes";
import { useAuth } from "../../contexts/useAuth";
import { useRef } from "react";
import { useChatSettings } from "../../contexts/useChatSettings";
import { AiChatCompleteResponse } from "../../types/chatTypes/chatResponseTypes";

type ApiResponse<T extends boolean> = T extends true ? AsyncGenerator<string, never, void> : ApiResponseData<AiChatCompleteResponse>;

export const useChat = <T extends boolean>(
  stream: T
): UseMutationResult<ApiResponse<T>, ApiResponseError, ChatDataParams, unknown> & {
  abort: { (reason?: unknown): void } | undefined;
} => {
  const { token } = useAuth();
  const { selectedModel } = useChatSettings();
  const abortControllerRef = useRef<AbortController | null>(null);

  const mutation = useMutation<
    ApiResponse<T>, // TData
    ApiResponseError, // TError
    ChatDataParams, // TVariables
    unknown // TContext
  >({
    mutationKey: ["chat-response"],
    mutationFn: async (userInput: ChatDataParams) => {
      const controller = abortControllerRef.current;
      if (!controller) throw new Error("AbortController is not initialized");

      const { signal } = controller;

      const requestBody: ChatRequestBody = {
        model: selectedModel,
        prompt: userInput.message,
        imageb64: userInput.image,
      };

      if (stream) {
        return getStreamedAiResponse(requestBody, token, signal) as ApiResponse<T>;
      } else {
        return (await getAiResponse(requestBody, signal)) as ApiResponse<T>;
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
