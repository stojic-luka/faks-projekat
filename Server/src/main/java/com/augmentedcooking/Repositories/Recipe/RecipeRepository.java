package com.augmentedcooking.Repositories.Recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.augmentedcooking.Models.Database.Recipe.Recipe;
import com.augmentedcooking.Utils.impl.CUIDConverter;

import io.github.thibaultmeyer.cuid.CUID;

@Repository
public class RecipeRepository implements IRecipeRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RecipeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Recipe> findAll(int page, int limit) {
        if (page < 0 || limit <= 0)
            throw new IllegalArgumentException("Page index must not be negative and Limit must be greater than zero!");

        long skip = (long) page * limit;
        Query query = new Query()
                .skip(skip)
                .limit(limit);

        return mongoTemplate.find(query, Recipe.class);
    }

    @Override
    public Optional<Recipe> findRandom() {
        AggregationResults<Recipe> results = mongoTemplate.aggregate(
                Aggregation.newAggregation(Aggregation.sample(1)),
                mongoTemplate.getCollectionName(Recipe.class),
                Recipe.class);
        return Optional.ofNullable(results.getUniqueMappedResult());
    }

    @Override
    public List<Recipe> findByIngredients(List<String> ingredients, int page, int limit) {
        Criteria ingredientCriteria = new Criteria().andOperator(
                ingredients.stream()
                        .map(ingredient -> Criteria.where("ingredients").regex(".*" + ingredient + ".*", "i"))
                        .toArray(Criteria[]::new));

        Aggregation aggregation = Aggregation.newAggregation(
                match(ingredientCriteria),
                skip((long) page * limit),
                limit(limit));

        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, "recipes", Recipe.class);
        return results.getMappedResults();
    }

    @Override
    public List<Recipe> findUserFavorites(String userId, int page, int limit) {
        // Aggregation agg = newAggregation(
        // match(Criteria.where("_id")
        // .is(CUIDConverter.cuidToBytes(userId))),
        // lookup("recipes", "recipeId", "_id", "recipe"),
        // unwind("$recipe"),
        // replaceRoot("$recipe"),
        // skip((long) page * limit),
        // limit(limit));
        // AggregationResults<Recipe> results = mongoTemplate.aggregate(agg,
        // FAVORITES_COLLECTION, Recipe.class);
        // return results.getMappedResults();

        Query query = new Query(Criteria.where("_id").is(CUIDConverter.cuidToBytes(userId)))
                .skip((long) page * limit)
                .limit(limit);

        return mongoTemplate.find(query, Recipe.class);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipe == null)
            throw new IllegalArgumentException("Recipe must not be null!");

        recipe.setId(CUID.randomCUID2());
        return mongoTemplate.insert(recipe);
    }

    @Override
    public Recipe updateRecipe(String recipeId, Recipe recipe) {
        if (recipe == null)
            throw new IllegalArgumentException("Recipe must not be null!");

        Query query = Query.query(Criteria.where("id").is(recipeId));
        recipe.setId(CUID.fromString(recipeId));
        return mongoTemplate.findAndReplace(query, recipe);
    }

    @Override
    public Recipe deleteRecipe(String recipeId) {
        Query query = Query.query(Criteria.where("id").is(recipeId));
        return mongoTemplate.findAndRemove(query, Recipe.class);
    }
}
