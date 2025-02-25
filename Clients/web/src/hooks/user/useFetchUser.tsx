import { QueryKey, useQuery } from "@tanstack/react-query";
import { fetchUser } from "../../services/userService";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { UserResponse } from "../../types/authTypes";

export const useFetchUser = () => {
  return useQuery<
    ApiResponseData<UserResponse>, // TQueryFnData
    ApiResponseError, // TError
    void, // TData = TQueryFnData
    QueryKey // QueryKey
  >({
    queryKey: ["fetch-user"],
    queryFn: fetchUser,
    staleTime: Infinity,
  });
};
