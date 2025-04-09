import { QueryKey, useQuery } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { getAvailableModels } from "../../services/chatService";

export const useAvailableModels = () => {
  return useQuery<
    ApiResponseData<string[]>, // TQueryFnData
    ApiResponseError, // TError
    string[], // TData
    QueryKey // QueryKey
  >({
    queryKey: ["available-models"],
    queryFn: getAvailableModels,
    select: (data) => data.data,
    staleTime: 60 * 60 * 1000,
    refetchOnWindowFocus: false,
  });
};
