import { InfiniteData, QueryKey, useInfiniteQuery } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { Recipe } from "../../types/recipesTypes";
import { getRecipesByIngredients } from "../../services/recipeService";
import { GlobalConsts } from "../../global/globalConsts";

export const useFetchRecipes = (ingredients: string[]) => {
  return useInfiniteQuery<
    ApiResponseData<Recipe[]>, // TQueryFnData
    ApiResponseError, // TError
    InfiniteData<ApiResponseData<Recipe[]>, unknown>, // TData
    QueryKey, // TQueryKey
    number // TPageParam
  >({
    queryKey: ["fetch-recipes-ingredients", ingredients],
    queryFn: ({ pageParam }) => getRecipesByIngredients({ page: pageParam, limit: GlobalConsts.pageSize, ingredients }),
    initialPageParam: 0,
    getNextPageParam: (lastPage, _, lastPageParam) => (lastPage.data.length === 0 ? undefined : lastPageParam + 1),
    enabled: ingredients.length > 0,
    staleTime: 1000 * 60 * 5, // 5 minutes
  });
};
