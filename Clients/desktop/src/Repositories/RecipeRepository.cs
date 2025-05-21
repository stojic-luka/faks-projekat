using AugmentedCooking.src.Managers;
using AugmentedCooking.src.Models;
using AugmentedCooking.src.Models.Deserialization;

namespace AugmentedCooking.src.Repositories;

public interface IRecipeRepository {
    Task<(int, ApiResponse<List<Recipe>>)> GetAllRecipesAsync(int page, byte limit);
    Task<(int, ApiResponse<Recipe>)> GetRandomRecipeAsync();
    Task<(int, ApiResponse<List<Recipe>>)> GetRecipesByIngredientsAsync(string jsonBody, int page, byte limit);
}


public class RecipeRepository(NetworkManager networkManager) : IRecipeRepository {
    private readonly NetworkManager _networkManager = networkManager;

    private const string BASE_URL = "https://localhost:8080/api/v1";

    public async Task<(int, ApiResponse<List<Recipe>>)> GetAllRecipesAsync(int page, byte limit) {
        string url = $"{BASE_URL}/recipe?page={page}&limit={limit}";
        return await _networkManager.GetAsync<List<Recipe>>(url);
    }

    public async Task<(int, ApiResponse<Recipe>)> GetRandomRecipeAsync() =>
        await _networkManager.GetAsync<Recipe>($"{BASE_URL}/recipe/random");

    public async Task<(int, ApiResponse<List<Recipe>>)> GetRecipesByIngredientsAsync(string jsonBody, int page, byte limit) {
        string url = $"{BASE_URL}/recipe?page={page}&limit={limit}";
        return await _networkManager.PostAsync<List<Recipe>>(url, jsonBody);
    }
}
