package com.augmentedcooking.Services.Recipe;

import java.util.List;
import java.util.Optional;

import com.augmentedcooking.Models.Database.Recipe.Recipe;

public interface IRecipeService {

    List<Recipe> getAllRecipes(int page, int limit);

    Optional<Recipe> getRandomRecipe();

    List<Recipe> getRecipesByIngredients(List<String> ingredients, int page, int limit);

    List<Recipe> getUserFavoriteRecipes(String id, int page, int limit);

    Recipe addRecipe(Recipe recipe);

    Recipe deleteRecipe(String recipeId);
}
