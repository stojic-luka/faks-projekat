import { useMutation } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { updateRecipe } from "../../services/recipeService";
import { Recipe, RecipeInput } from "../../types/recipesTypes";

export const useRecipeUpdate = () => {
  return useMutation<
    ApiResponseData<Recipe>, // TData
    ApiResponseError, // TError
    { recipeId: string; recipe: RecipeInput }, // TVariables
    unknown // TContext
  >({
    mutationKey: ["recipe-update"],
    mutationFn: updateRecipe,
  });
};
