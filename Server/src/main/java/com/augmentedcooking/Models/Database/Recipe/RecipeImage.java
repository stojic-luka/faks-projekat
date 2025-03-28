package com.augmentedcooking.Models.Database.Recipe;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "recipeImages")
public class RecipeImage {

    @Id
    private CUID id;
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
