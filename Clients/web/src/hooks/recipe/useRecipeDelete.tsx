import { useMutation } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { Recipe } from "../../types/recipesTypes";
import { deleteRecipe } from "../../services/recipeService";

export const useRecipeDelete = () => {
  return useMutation<
    ApiResponseData<Recipe>, // TData
    ApiResponseError, // TError
    string, // TVariables
    unknown // TContext
  >({
    mutationKey: ["recipe-delete"],
    mutationFn: deleteRecipe,
  });
};
