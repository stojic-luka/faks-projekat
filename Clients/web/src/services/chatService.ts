import authenticatedAxios from "../api/authenticatedAxios";
import { ChatResponse, UserChatRequestBody } from "../types/chatTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const getAiResponse = async (userInput: UserChatRequestBody, signal: AbortSignal): Promise<ApiResponseData<ChatResponse>> => {
  const response = await authenticatedAxios.post<ApiResponse<ChatResponse>>(
    "/api/v1/chat",
    { message: userInput.message, streamed: false },
    {
      headers: {
        "Content-Type": "application/json",
      },
      signal: signal,
    }
  );

  return response.data as ApiResponseData<ChatResponse>;
};

export const getStreamedAiResponse = async function* (
  userInput: UserChatRequestBody,
  token: string,
  signal: AbortSignal
): AsyncGenerator<string, void, unknown> {
  const response = await fetch(import.meta.env.VITE_API_URL + "/api/v1//chat", {
    method: "POST",
    headers: {
      Authentication: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ message: userInput.message, streamed: true }),
    signal: signal,
  });

  if (!response.body) return;

  const reader = response.body.getReader();
  const decoder = new TextDecoder();

  try {
    while (true) {
      const { done, value } = await reader.read();

      if (done) break;

      yield decoder.decode(value);
    }
  } catch (error) {
    if (signal.aborted) {
      console.log("Fetch aborted");
    } else {
      throw error;
    }
  } finally {
    reader.releaseLock();
  }
};
