package com.augmentedcooking.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.Http.NotFoundException;
import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Models.Request.Recipe.RecipeRequestBody;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.Recipe.RecipeResponseBody;
import com.augmentedcooking.Services.Recipe.IRecipeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/api/v1/recipe")
public class RecipeController {

    private final IRecipeService recipeService;

    @Autowired
    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {
        List<Recipe> recipes = recipeService.getAllRecipes(page, limit);

        List<RecipeResponseBody> responseBody = recipes.stream()
                .map(r -> new RecipeResponseBody(r))
                .collect(Collectors.toList());

        return ResponseWrapper.success(responseBody);
    }

    @GetMapping(path = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRandomRecipe() {
        Recipe recipe = recipeService.getRandomRecipe();
        return ResponseWrapper.success(recipe == null ? null : new RecipeResponseBody(recipe));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecipesByIngredients(
            @RequestBody RecipeRequestBody body,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {
        if (body == null || body.getIngredients().isEmpty())
            throw (BaseResponseException) new NotFoundException();

        List<Recipe> recipes = recipeService.getRecipesByIngredients(body.getIngredients(), page, limit);

        List<RecipeResponseBody> responseBody = recipes.stream()
                .map(r -> new RecipeResponseBody(r))
                .collect(Collectors.toList());

        return ResponseWrapper.success(responseBody);
    }

    @GetMapping(path = "/favorite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFavoriteRecipe(UsernamePasswordAuthenticationToken authentication) {
        try {
            Recipe recipe = recipeService.getFavoriteRecipe();
            return ResponseWrapper.success(new RecipeResponseBody(recipe));
        } catch (Exception e) {
            throw (BaseResponseException) new NotFoundException();
        }
    }
}
