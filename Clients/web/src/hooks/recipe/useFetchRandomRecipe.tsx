import { InfiniteData, QueryKey, useInfiniteQuery } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { Recipe } from "../../types/recipesTypes";
import { getRandomRecipe } from "../../services/recipeService";

export const useFetchRandomRecipes = () => {
  return useInfiniteQuery<
    ApiResponseData<Recipe>, // TQueryFnData
    ApiResponseError, // TError
    InfiniteData<ApiResponseData<Recipe>, unknown>, // TData
    QueryKey, // TQueryKey
    number // TPageParam
  >({
    queryKey: ["fetch-random-recipe"],
    queryFn: getRandomRecipe,
    initialPageParam: 0,
    getNextPageParam: (_, pages) => pages.length + 1,
  });
};
