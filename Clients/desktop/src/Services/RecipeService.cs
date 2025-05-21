using AugmentedCooking.src.Models;
using AugmentedCooking.src.Repositories;
using Newtonsoft.Json;

namespace AugmentedCooking.src.Services;

public interface IRecipeService {
    Task<Recipe?> GetRandomRecipeAsync();
    Task<Recipe[]?> GetRecipeByIngredientsAsync(string[] ingredients, int page, byte limit);
}

public class RecipeService(RecipeRepository recipeRepository) : IRecipeService {
    private readonly RecipeRepository _recipeRepository = recipeRepository;

    public async Task<Recipe?> GetRandomRecipeAsync() {
        try {
            var (statusCode, apiResponse) = await _recipeRepository.GetRandomRecipeAsync();

            if (statusCode == 200 && apiResponse.Data != null)
                return apiResponse.Data;

            return null;
        }
        catch (Exception ex) {
            System.Diagnostics.Trace.WriteLine(ex.Message);
            return null;
        }
    }

    public async Task<Recipe[]?> GetRecipeByIngredientsAsync(string[] ingredients, int page, byte limit) {
        try {
            string body = JsonConvert.SerializeObject(new { Ingredients = ingredients });
            var (statusCode, apiResponse) =
                await _recipeRepository.GetRecipesByIngredientsAsync(body, page, limit);

            if (statusCode == 200 && apiResponse.Data != null)
                return [.. apiResponse.Data];

            return null;
        }
        catch (Exception ex) {
            System.Diagnostics.Trace.WriteLine(ex.Message);
            return null;
        }
    }
}
