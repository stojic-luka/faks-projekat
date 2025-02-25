<<<<<<< Updated upstream
import authenticatedAxios from "../api/authenticatedAxios";
import { Recipe, RecipeFilterArguments } from "../types/recipesTypes";
import { Pagination } from "../types/requestTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const getRecipes = async (pagination: Pagination): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe[]>>("/recipe", {
    headers: {
      "Content-Type": "application/json",
    },
    params: pagination,
  });

  return response.data as ApiResponseData<Recipe[]>;
};

export const getRandomRecipe = async (): Promise<ApiResponseData<Recipe>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe>>("/recipe/random", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<Recipe>;
};

export const getRecipesByIngredients = async (filters: RecipeFilterArguments): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.post<ApiResponse<Recipe[]>>(
    "/api/v1/recipe",
    { ingredients: filters.ingredients },
    {
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        limit: filters.limit,
        page: filters.page,
      },
    }
  );

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
=======
import authenticatedAxios from "../api/authenticatedAxios";
import { Recipe, RecipeFilterArguments } from "../types/recipesTypes";
import { Pagination } from "../types/requestTypes";
import { ApiResponse, ApiResponseData } from "../types/responseTypes";

export const getRecipes = async (pagination: Pagination): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe[]>>("/recipe", {
    headers: {
      "Content-Type": "application/json",
    },
    params: pagination,
  });

  return response.data as ApiResponseData<Recipe[]>;
};

export const getRandomRecipe = async (): Promise<ApiResponseData<Recipe>> => {
  const response = await authenticatedAxios.get<ApiResponse<Recipe>>("/recipe/random", {
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response.data as ApiResponseData<Recipe>;
};

export const getRecipesByIngredients = async (filters: RecipeFilterArguments): Promise<ApiResponseData<Recipe[]>> => {
  const response = await authenticatedAxios.post<ApiResponse<Recipe[]>>(
    "/api/v1/recipe",
    { ingredients: filters.ingredients },
    {
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        limit: filters.limit,
        page: filters.page,
      },
    }
  );

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
>>>>>>> Stashed changes
