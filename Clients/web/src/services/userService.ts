import authenticatedAxios from "../api/authenticatedAxios";
import { UserResponse } from "../types/authTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const fetchUser = async () => {
  const response = await authenticatedAxios.get<ApiResponse<UserResponse>>("/api/self", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<UserResponse>;
};
