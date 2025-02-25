package com.augmentedcooking.Models.Request.Recipe;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeRequestBody {
    private List<String> ingredients;
}
