import { QueryKey, useQuery } from "@tanstack/react-query";
import { fetchUser } from "../../services/userService";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { UserResponse } from "../../types/authTypes";

export const useFetchUser = () => {
  return useQuery<
    ApiResponseData<UserResponse>, // TQueryFnData
    ApiResponseError, // TError
    UserResponse, // TData = TQueryFnData
    QueryKey // QueryKey
  >({
    queryKey: ["fetch-user"],
    queryFn: fetchUser,
    // select: (data) => data.data,
    select: (data) => {
      const user = data.data;
      user.roles.push("admin");
      return user;
    },
    staleTime: Infinity,
  });
};
