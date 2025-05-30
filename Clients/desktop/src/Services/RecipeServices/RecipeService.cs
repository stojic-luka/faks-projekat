﻿using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Repositories.RecipeRepositories;
using Newtonsoft.Json;

namespace AugmentedCooking.src.Services.RecipeServices;

public interface IRecipeService {
    Task<Recipe?> GetRandomRecipeAsync();
    Task<Recipe[]?> GetRecipeByIngredientsAsync(int page, byte limit, string[] ingredients);
    Task<Recipe?> AddRecipeAsync(Recipe recipe);
    Task<Recipe?> DeleteRecipeAsync(string recipeId);
}

public class RecipeService(IRecipeRepository recipeRepository) : IRecipeService {
    private readonly IRecipeRepository _recipeRepository = recipeRepository;

    public async Task<Recipe?> GetRandomRecipeAsync() {
        try {
            var apiResponse = await _recipeRepository.GetRandomRecipeAsync();

            if (apiResponse.IsSuccess)
                return apiResponse.Data;

            return null;
        }
        catch (Exception ex) {
            System.Diagnostics.Trace.WriteLine(ex.Message);
            return null;
        }
    }

    public async Task<Recipe[]?> GetRecipeByIngredientsAsync(int page, byte limit, string[] ingredients) {
        try {
            string body = JsonConvert.SerializeObject(new { Ingredients = ingredients });
            var apiResponse = await _recipeRepository.GetRecipesByIngredientsAsync(page, limit, ingredients);

            if (apiResponse.IsSuccess)
                return [.. apiResponse.Data!];

            return null;
        }
        catch (Exception ex) {
            System.Diagnostics.Trace.WriteLine(ex.Message);
            return null;
        }
    }

    public async Task<Recipe?> AddRecipeAsync(Recipe recipe) {
        try {
            var apiResponse = await _recipeRepository.AddRecipeAsync(recipe);

            if (apiResponse.IsSuccess)
                return apiResponse.Data!;

            return null;
        }
        catch (Exception ex) {
            System.Diagnostics.Trace.WriteLine(ex.Message);
            return null;
        }
    }

    public async Task<Recipe?> DeleteRecipeAsync(string recipeId) {
        try {
            var apiResponse = await _recipeRepository.DeleteRecipeAsync(recipeId);

            if (apiResponse.IsSuccess)
                return apiResponse.Data!;

            return null;
        }
        catch (Exception ex) {
            System.Diagnostics.Trace.WriteLine(ex.Message);
            return null;
        }
    }
}
