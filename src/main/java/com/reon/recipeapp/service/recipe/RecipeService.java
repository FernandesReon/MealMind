package com.reon.recipeapp.service.recipe;

import com.reon.recipeapp.dto.recipe.CreateRecipe;
import com.reon.recipeapp.dto.recipe.ViewRecipe;

import java.util.List;

public interface RecipeService {
    ViewRecipe createRecipe(CreateRecipe createRecipe);
    List<ViewRecipe> getAllRecipes();
    ViewRecipe updateRecipe(String id, CreateRecipe updateRecipe);
    void deleteRecipe(String id);
    ViewRecipe getRecipeById (String id);
}
