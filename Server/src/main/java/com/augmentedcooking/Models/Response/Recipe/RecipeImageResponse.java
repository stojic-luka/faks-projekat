package com.augmentedcooking.Models.Response.Recipe;

import org.bson.types.Binary;
import org.bson.types.ObjectId;

import com.augmentedcooking.Models.Database.Recipe.RecipeImage;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecipeImageResponse {

    private String id;
    private String contentType;
    private Binary data;
    private RecipeImage.Metadata metadata;

    public RecipeImageResponse(RecipeImage recipeImage) {
        this.id = recipeImage.getId().toHexString();
        this.contentType = recipeImage.getContentType();
        this.data = recipeImage.getData();
        this.metadata = recipeImage.getMetadata();
    }

    public RecipeImageResponse(ObjectId id, String contentType, Binary data, RecipeImage.Metadata metadata) {
        this.id = id.toHexString();
        this.contentType = contentType;
        this.data = data;
        this.metadata = metadata;
    }
}
