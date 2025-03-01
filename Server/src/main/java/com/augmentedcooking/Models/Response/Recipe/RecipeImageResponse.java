package com.augmentedcooking.Models.Response.Recipe;

import java.util.Base64;

import org.bson.types.Binary;
import org.bson.types.ObjectId;

import com.augmentedcooking.Models.Database.Recipe.RecipeImage;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecipeImageResponse {

    // private String id;
    private String contentType;
    private String data;
    private RecipeImage.Metadata metadata;

    public RecipeImageResponse(RecipeImage recipeImage) {
        // this.id = recipeImage.getId().toHexString();
        this.contentType = recipeImage.getContentType();
        byte[] encoded = Base64.getEncoder().encode(recipeImage.getData().getData());
        this.data = new String(encoded);
        this.metadata = recipeImage.getMetadata();
    }

    public RecipeImageResponse(ObjectId id, String contentType, Binary data, RecipeImage.Metadata metadata) {
        // this.id = id.toHexString();
        this.contentType = contentType;
        byte[] encoded = Base64.getEncoder().encode(data.getData());
        this.data = new String(encoded);
        this.metadata = metadata;
    }
}
