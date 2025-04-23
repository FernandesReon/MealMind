package com.reon.recipeapp.dto.recipe;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipeEventDTO {
    private String id;
    private String title;
    private String description;
    private Integer servings;
    private String additionalNotes;
    private List<String> ingredients;
    private List<String> instructions;
    private boolean isFavouriteRecipe;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String userId;
}
