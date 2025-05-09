package com.augmentedcooking.Models.Response.Recipe;

import java.util.List;

import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Models.Database.Recipe.RecipeImage;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecipeResponseBody {

    private String title;
    private List<String> ingredients;
    private String instructions;
    private RecipeImageResponse image;

    public RecipeResponseBody(Recipe recipe) {
        this.title = recipe.getTitle();
        this.ingredients = recipe.getIngredients();
        this.instructions = recipe.getInstructions();
        this.image = (recipe.getImage() != null && !recipe.getImage().isEmpty())
                ? new RecipeImageResponse(recipe.getImage().get(0))
                : null;
    }

    public RecipeResponseBody(String title, List<String> ingredients, String instructions, RecipeImage image) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = new RecipeImageResponse(image);
    }

    public RecipeResponseBody(String title, List<String> ingredients, String instructions, RecipeImageResponse image) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = image;
    }
}
