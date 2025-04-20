package com.reon.recipeapp.mapper;

import com.reon.recipeapp.dto.recipe.CreateRecipe;
import com.reon.recipeapp.dto.recipe.ViewRecipe;
import com.reon.recipeapp.model.Recipe;
import com.reon.recipeapp.model.User;

public class RecipeMapper {
    public static Recipe mapToRecipe(CreateRecipe newRecipe){
        Recipe recipe = new Recipe();
        recipe.setTitle(newRecipe.getTitle());
        recipe.setDescription(newRecipe.getDescription());
        recipe.setServings(newRecipe.getServings());
        recipe.setAdditional_notes(newRecipe.getAdditional_notes());
        recipe.setIngredients(newRecipe.getIngredients());
        recipe.setInstructions(newRecipe.getInstructions());
        recipe.setFavouriteRecipe(newRecipe.isFavouriteRecipe());
        return recipe;
    }

    public static ViewRecipe recipeResponse(Recipe recipe){
        ViewRecipe viewRecipe = new ViewRecipe();
        viewRecipe.setId(recipe.getId());
        viewRecipe.setTitle(recipe.getTitle());
        viewRecipe.setDescription(recipe.getDescription());
        viewRecipe.setServings(recipe.getServings());
        viewRecipe.setAdditional_notes(recipe.getAdditional_notes());
        viewRecipe.setIngredients(recipe.getIngredients());
        viewRecipe.setInstructions(recipe.getInstructions());
        viewRecipe.setFavouriteRecipe(recipe.isFavouriteRecipe());
        viewRecipe.setCreatedOn(recipe.getCreatedOn());
        viewRecipe.setUpdatedOn(recipe.getUpdatedOn());

        User user = recipe.getUser();
        if (user != null){
            viewRecipe.setUserId(user.getId());
        }
        return viewRecipe;
    }

    public static void applyUpdates(Recipe recipe, CreateRecipe update) {
        if (update.getTitle() != null && !update.getTitle().isBlank()) {
            recipe.setTitle(update.getTitle());
        }
        if (update.getDescription() != null && !update.getDescription().isBlank()) {
            recipe.setDescription(update.getDescription());
        }
        if (update.getServings() != null) {
            recipe.setServings(update.getServings());
        }
        if (update.getAdditional_notes() != null && !update.getAdditional_notes().isBlank()) {
            recipe.setAdditional_notes(update.getAdditional_notes());
        }
        if (update.getIngredients() != null && !update.getIngredients().isEmpty()) {
            recipe.setIngredients(update.getIngredients());
        }
        if (update.getInstructions() != null && !update.getInstructions().isEmpty()) {
            recipe.setInstructions(update.getInstructions());
        }
        recipe.setFavouriteRecipe(update.isFavouriteRecipe());
    }
}
