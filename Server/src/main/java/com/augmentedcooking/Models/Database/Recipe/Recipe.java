package com.augmentedcooking.Models.Database.Recipe;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "recipes")
public class Recipe {

    @Id
    private CUID id;
    private String title;
    private List<String> ingredients;
    private String instructions;
    @DBRef
    private List<RecipeImage> image;
}
