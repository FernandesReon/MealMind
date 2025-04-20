package com.reon.recipeapp.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecipe {
    @NotBlank(
            message = "Recipe title of required."
    )
    private String title;

    @Size(
            message = "Maximum 1000 characters allowed.", max = 1000
    )
    private String description;
    private Integer servings;
    @Size(
            message = "Maximum 1000 characters allowed.", max = 1000
    )
    private String additional_notes;

    @NotEmpty(
            message = "Including at least 1 ingredient is must."
    )
    private List<String> ingredients;
    private List<String> instructions;
    private boolean isFavouriteRecipe;
}
