package com.augmentedcooking.Repositories.Recipe;

import java.util.List;

import com.augmentedcooking.Models.Database.Recipe.Recipe;

public interface IRecipeRepository {

    List<Recipe> findAll(int page, int limit);

    Recipe findRandom();

    List<Recipe> findByIngredients(List<String> ingredients, int page, int limit);
}
