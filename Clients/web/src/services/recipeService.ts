import authenticatedAxios from "../api/authenticatedAxios";
import { Recipe, RecipeFilterArguments, RecipeInput } from "../types/recipesTypes";
import { Pagination } from "../types/requestTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const getRecipes = async (pagination: Pagination): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe[]>>("/api/v1/recipe", {
    headers: {
      "Content-Type": "application/json",
    },
    params: pagination,
  });

  return response.data as ApiResponseData<Recipe[]>;
};

export const getRandomRecipe = async (): Promise<ApiResponseData<Recipe>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe>>("/api/v1/recipe/random", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<Recipe>;
};

export const getRecipesByIngredients = async (filters: RecipeFilterArguments): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe[]>>("/api/v1/recipe", {
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      ingredients: filters.ingredients,
      limit: filters.limit,
      page: filters.page,
    },
    paramsSerializer: { indexes: null },
  });

  return response.data as ApiResponseData<Recipe[]>;
};

export const getFavoriteRecipes = async (pagination: Pagination): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe[]>>("/api/v1/recipe/favorite", {
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      limit: pagination.limit,
      page: pagination.page,
    },
  });

  return response.data as ApiResponseData<Recipe[]>;
};

export const submitRecipe = async (recipe: RecipeInput): Promise<ApiResponseData<Recipe>> => {
  const response = await authenticatedAxios.post<ApiResponse<Recipe>>("/api/v1/recipe", recipe, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<Recipe>;
};

export const updateRecipe = async ({ recipeId, recipe }: { recipeId: string; recipe: RecipeInput }): Promise<ApiResponseData<Recipe>> => {
  const response = await authenticatedAxios.put<ApiResponse<Recipe>>("/api/v1/recipe", recipe, {
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      id: recipeId,
    },
  });

  return response.data as ApiResponseData<Recipe>;
};

export const deleteRecipe = async (recipeId: string): Promise<ApiResponseData<Recipe>> => {
  const response = await authenticatedAxios.delete<ApiResponse<Recipe>>(`/api/v1/recipe`, {
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      id: recipeId,
    },
  });

  return response.data as ApiResponseData<Recipe>;
};
