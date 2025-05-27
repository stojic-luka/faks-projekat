using System.Text.Json;
using AugmentedCooking.src.Constants;
using AugmentedCooking.src.Managers;
using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Models.Response;

namespace AugmentedCooking.src.Repositories.RecipeRepositories;

public interface IRecipeRepository {
    Task<ApiResponse<List<Recipe>>> GetAllRecipesAsync(int page, byte limit);
    Task<ApiResponse<Recipe>> GetRandomRecipeAsync();
    Task<ApiResponse<List<Recipe>>> GetRecipesByIngredientsAsync(int page, byte limit, string[] ingredients);
    Task<ApiResponse<Recipe>> AddRecipeAsync(Recipe recipe);
    Task<ApiResponse<Recipe>> DeleteRecipeAsync(string recipeId);
}


public class RecipeRepository(INetworkManager networkManager) : IRecipeRepository {
    private readonly INetworkManager _networkManager = networkManager;

    public async Task<ApiResponse<List<Recipe>>> GetAllRecipesAsync(int page, byte limit) {
        string url = string.Format(ApiRoutes.Recipes.ListAll, page, limit);
        return await _networkManager.GetAsync<List<Recipe>>(url);
    }

    public async Task<ApiResponse<Recipe>> GetRandomRecipeAsync() {
        return await _networkManager.GetAsync<Recipe>(ApiRoutes.Recipes.Random);
    }

    public async Task<ApiResponse<List<Recipe>>> GetRecipesByIngredientsAsync(int page, byte limit, string[] ingredients) {
        string url = string.Format(ApiRoutes.Recipes.ListByIngredients, page, limit, string.Join(",", ingredients));
        return await _networkManager.GetAsync<List<Recipe>>(url);
    }

    public async Task<ApiResponse<Recipe>> AddRecipeAsync(Recipe recipe) {
        string url = ApiRoutes.Recipes.Create;
        return await _networkManager.PostAsync<Recipe>(url, JsonSerializer.Serialize(recipe));
    }

    public async Task<ApiResponse<Recipe>> DeleteRecipeAsync(string recipeId) {
        string url = string.Format(ApiRoutes.Recipes.DeleteById, recipeId);
        return await _networkManager.DeleteAsync<Recipe>(url);
    }
}
