package com.augmentedcooking.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.Http.BadRequestException;
import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.Recipe.RecipeResponseBody;
import com.augmentedcooking.Services.Recipe.IRecipeService;

import io.github.thibaultmeyer.cuid.CUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/api/v1/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

    private final IRecipeService recipeService;

    @Autowired
    public RecipeController(final IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllRecipes(
            @RequestParam(name = "ingredients", required = false) List<String> ingredients,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {
        if (page < 0 || limit <= 0)
            throw new BadRequestException("Invalid pagination parameters: page must be >= 0 and limit must be > 0.");

        List<Recipe> recipes;
        if (ingredients == null || ingredients.isEmpty())
            recipes = recipeService.getAllRecipes(page, limit);
        else
            recipes = recipeService.getRecipesByIngredients(ingredients, page, limit);

        List<RecipeResponseBody> responseBody = recipes.stream()
                .map(r -> new RecipeResponseBody(r))
                .collect(Collectors.toList());

        return ResponseWrapper.success(responseBody);
    }

    @GetMapping(path = "/random")
    public ResponseEntity<?> getRandomRecipe() {
        Optional<Recipe> recipe = recipeService.getRandomRecipe();
        if (recipe.isEmpty())
            return ResponseWrapper.success(null);

        return ResponseWrapper.success(new RecipeResponseBody(recipe.get()));
    }

    @GetMapping(path = "/favorite")
    public ResponseEntity<?> getFavoriteRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            UsernamePasswordAuthenticationToken authentication) {
        List<Recipe> recipes = recipeService.getUserFavoriteRecipes(authentication.getName(), page, limit);

        List<RecipeResponseBody> responseBody = recipes.stream()
                .map(r -> new RecipeResponseBody(r))
                .collect(Collectors.toList());

        return ResponseWrapper.success(responseBody);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRecipe(
            @RequestBody Recipe body,
            UsernamePasswordAuthenticationToken authentication) {
        if (body == null)
            throw (BaseResponseException) new BadRequestException();

        Recipe recipe = recipeService.addRecipe(body);
        return ResponseWrapper.success(new RecipeResponseBody(recipe));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRecipe(
            @RequestParam String id,
            UsernamePasswordAuthenticationToken authentication) {
        if (!CUID.isValid(id))
            throw (BaseResponseException) new BadRequestException();

        Recipe recipe = recipeService.deleteRecipe(id);
        return ResponseWrapper.success(recipe);
    }
}
