package com.reon.recipeapp.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewRecipe {
    private String id;
    private String title;
    private String description;
    private Integer servings;
    private Integer prep_time;
    private Integer cook_time;
    private String additional_notes;
    private List<String> ingredients;
    private List<String> instructions;
    private boolean isFavouriteRecipe;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String userId;
}
