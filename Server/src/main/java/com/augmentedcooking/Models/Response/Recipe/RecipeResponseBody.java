package com.augmentedcooking.Models.Response.Recipe;

import java.util.List;
import java.util.Optional;

import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Models.Database.Recipe.Recipe.RecipeImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RecipeResponseBody {

    private String id;
    private String title;
    private List<String> ingredients;
    private String instructions;
    private RecipeImageResponse image;

    public RecipeResponseBody(Recipe recipe) {
        this.id = recipe.getId().toString();
        this.title = recipe.getTitle();
        this.ingredients = recipe.getIngredients();
        this.instructions = recipe.getInstructions();
        this.image = Optional.ofNullable(recipe.getRecipeImage())
                .map(RecipeImageResponse::new)
                .orElse(null);
    }

    public RecipeResponseBody(String id, String title, List<String> ingredients, String instructions,
            RecipeImage image) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = new RecipeImageResponse(image);
    }
}
