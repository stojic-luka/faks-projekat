import { useMutation } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { Recipe } from "../../types/recipesTypes";
import { getRandomRecipe } from "../../services/recipeService";

export const useFetchRandomRecipe = () => {
  return useMutation<
    ApiResponseData<Recipe>, // TData
    ApiResponseError, // TError
    void, // TVariables
    unknown // TContext
  >({
    mutationKey: ["fetch-random-recipe"],
    mutationFn: getRandomRecipe,
  });
};
