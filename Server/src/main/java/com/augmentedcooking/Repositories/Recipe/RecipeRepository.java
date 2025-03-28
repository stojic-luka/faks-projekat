package com.augmentedcooking.Repositories.Recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Utils.impl.CUIDConverter;

@Repository
public class RecipeRepository implements IRecipeRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RecipeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Recipe> findAll(int page, int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                lookup("recipeImages", "recipeImage", "_id", "image"),
                skip((long) page * limit),
                limit(limit));

        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, "recipes", Recipe.class);

        return results.getMappedResults();
    }

    @Override
    public Optional<Recipe> findRandom() {
        Aggregation aggregation = Aggregation.newAggregation(
                sample(1),
                lookup("recipeImages", "recipeImage", "_id", "image"));

        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, "recipes", Recipe.class);

        List<Recipe> recipes = results.getMappedResults();
        return Optional.ofNullable(recipes.isEmpty() ? null : recipes.get(0));
    }

    @Override
    public List<Recipe> findByIngredients(List<String> ingredients, int page, int limit) {
        Criteria ingredientCriteria = new Criteria();
        ingredientCriteria.andOperator(
                ingredients.stream()
                        .map(ingredient -> Criteria.where("ingredients").regex(".*" + ingredient + ".*", "i"))
                        .toArray(Criteria[]::new));

        Aggregation aggregation = Aggregation.newAggregation(
                match(ingredientCriteria),
                lookup("recipeImages", "recipeImage", "_id", "image"),
                skip((long) page * limit),
                limit(limit));

        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, "recipes", Recipe.class);
        return results.getMappedResults();
    }

    @Override
    public List<Recipe> findUserFavorites(String id, int page, int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("_id").is(CUIDConverter.cuidToBytes(id))),
                lookup("recipes", "recipeId", "_id", "recipe"),
                unwind("$recipe"),
                lookup("recipeImages", "recipe.recipeImage", "_id", "recipe.image"),
                skip((long) page * limit),
                limit(limit),
                project("recipe"));

        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, "favoriteRecipes", Recipe.class);
        return results.getMappedResults();
    }
}
