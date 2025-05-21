import { useMutation } from "@tanstack/react-query";
import { ApiResponseData, ApiResponseError } from "../../types/responseTypes";
import { submitRecipe } from "../../services/recipeService";
import { Recipe, RecipeInput } from "../../types/recipesTypes";

export const useRecipeSubmit = () => {
  return useMutation<
    ApiResponseData<Recipe>, // TData
    ApiResponseError, // TError
    RecipeInput, // TVariables
    unknown // TContext
  >({
    mutationKey: ["recipe-submit"],
    mutationFn: submitRecipe,
  });
};
