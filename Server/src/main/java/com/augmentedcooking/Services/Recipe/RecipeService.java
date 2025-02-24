package com.augmentedcooking.Services.Recipe;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Repositories.Recipe.IRecipeRepository;

@Service
public class RecipeService implements IRecipeService {
    private final IRecipeRepository recipeRepository;

    @Autowired
    public RecipeService(final IRecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes(int page, int limit) {
        return recipeRepository.findAll(page, limit);
    }

    @Override
    public Recipe getRandomRecipe() {
        return recipeRepository.findRandom();
    }

    @Override
    public List<Recipe> getRecipesByIngredients(List<String> ingredients, int page, int limit) {
        return recipeRepository.findByIngredients(ingredients, page, limit);
    }
}
