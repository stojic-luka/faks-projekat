import authenticatedAxios from "../api/authenticatedAxios";
import { ChatRequestBody } from "../types/chatTypes/chatRequestTypes";
import { AiChatCompleteResponse } from "../types/chatTypes/chatResponseTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const getAiResponse = async (requestBody: ChatRequestBody, signal: AbortSignal): Promise<ApiResponseData<AiChatCompleteResponse>> => {
  const response = await authenticatedAxios.post<ApiResponse<AiChatCompleteResponse>>(
    "/api/v1/chat",
    { ...requestBody, streamed: false },
    {
      headers: {
        "Content-Type": "application/json",
      },
      signal: signal,
    }
  );

  return response.data as ApiResponseData<AiChatCompleteResponse>;
};

export const getStreamedAiResponse = async function* (
  requestBody: ChatRequestBody,
  token: string,
  signal: AbortSignal
): AsyncGenerator<string, void, unknown> {
  const response = await fetch(import.meta.env.VITE_API_URL + "/api/v1/chat", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ ...requestBody, streamed: true }),
    signal: signal,
  });

  // New: Check if response is OK
  if (!response.ok) {
    throw new Error(`Network response was not ok: ${response.statusText}`);
  }

  if (!response.body) return;

  const reader = response.body.getReader();
  const decoder = new TextDecoder();

  try {
    while (true) {
      const { done, value } = await reader.read();
      if (done) break;
      yield decoder.decode(value);
    }
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (signal.aborted) {
      // console.log("Fetch aborted");
    } else if (error.message && error.message.includes("ERR_INCOMPLETE_CHUNKED_ENCODING")) {
      console.warn("Incomplete chunked encoding, terminating stream.");
    } else {
      throw error;
    }
  } finally {
    reader.releaseLock();
  }
};

export const getAvailableModels = async (): Promise<ApiResponseData<string[]>> => {
  const response = await authenticatedAxios.get<ApiResponse<string[]>>("/api/v1/chat/models", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<string[]>;
};
