package com.augmentedcooking.Models.Database.Recipe;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.augmentedcooking.Constants.Constants;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Constants.DB.Collections.RECIPES)
public class Recipe {

    @Id
    private CUID id;
    private String title;
    private List<String> ingredients;
    private String instructions;
    private RecipeImage recipeImage;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecipeImage {

        private String contentType;
        private Binary data;
        private RecipeImage.Metadata metadata;

        @Getter
        @Setter
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Metadata {
            private int height;
            private int width;
        }
    }
}
