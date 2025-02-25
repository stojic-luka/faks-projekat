package com.augmentedcooking.Services.Recipe;

import java.util.List;

import com.augmentedcooking.Models.Database.Recipe.Recipe;

public interface IRecipeService {

    List<Recipe> getAllRecipes(int page, int limit);

    Recipe getRandomRecipe();

    List<Recipe> getRecipesByIngredients(List<String> ingredients, int page, int limit);
}
