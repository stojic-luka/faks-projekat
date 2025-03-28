package com.augmentedcooking.Repositories.Recipe;

import java.util.List;
import java.util.Optional;

import com.augmentedcooking.Models.Database.Recipe.Recipe;

public interface IRecipeRepository {

    List<Recipe> findAll(int page, int limit);

    Optional<Recipe> findRandom();

    List<Recipe> findByIngredients(List<String> ingredients, int page, int limit);

    List<Recipe> findUserFavorites(String id, int page, int limit);
}
