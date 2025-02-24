import { InfiniteData, QueryKey, useInfiniteQuery } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { Recipe } from "../../types/recipesTypes";
import { getFavoriteRecipes } from "../../services/recipeService";
import { GlobalConsts } from "../../global/globalConsts";

export const useFetchFavoriteRecipes = () => {
  return useInfiniteQuery<
    ApiResponseData<Recipe[]>, // TQueryFnData
    ApiResponseError, // TError
    InfiniteData<Recipe, unknown>, // TData
    QueryKey, // TQueryKey
    number // TPageParam
  >({
    queryKey: ["fetch-favorite-recipes"],
    queryFn: ({ pageParam }) => getFavoriteRecipes({ page: pageParam, limit: GlobalConsts.pageSize }),
    initialPageParam: 0,
    getNextPageParam: (lastPage, _, lastPageParam) => (lastPage.data.length === 0 ? undefined : lastPageParam + 1),
  });
};
