package com.augmentedcooking.Models.Database.Recipe;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recipeImages")
public class RecipeImage {

    @Id
    private ObjectId id;
    private String contentType;
    private Binary data;
    private RecipeImage.Metadata metadata;

    @Getter
    @Setter
    @ToString
    public static class Metadata {
        private int height;
        private int width;
    }
}
