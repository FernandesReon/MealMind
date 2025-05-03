package com.reon.recipeapp.mapper;

import com.reon.recipeapp.dto.recipe.RecipeEventDTO;
import com.reon.recipeapp.dto.user.UserEventDTO;
import com.reon.recipeapp.model.Recipe;
import com.reon.recipeapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public static UserEventDTO toUserEventDTO(User user){
        UserEventDTO userEvent = new UserEventDTO();
        userEvent.setId(user.getId());
        userEvent.setName(user.getName());
        userEvent.setUsername(user.getEntityUsername());
        userEvent.setEmail(user.getEmail());
        userEvent.setRoles(user.getRoles());
        userEvent.setAccountEnabled(user.isAccountEnabled());
        userEvent.setEmailVerified(user.isEmailVerified());
        userEvent.setProvider(user.getProvider());
        userEvent.setCreatedOn(user.getCreatedOn());
        userEvent.setUpdatedOn(user.getUpdatedOn());
        return userEvent;
    }

    public static RecipeEventDTO toRecipeEventDTO(Recipe recipe){
        RecipeEventDTO recipeEvent = new RecipeEventDTO();
        recipeEvent.setId(recipe.getId());
        recipeEvent.setTitle(recipe.getTitle());
        recipeEvent.setDescription(recipe.getDescription());
        recipeEvent.setServings(recipe.getServings());
        recipeEvent.setPrep_time(recipe.getPrep_time());
        recipeEvent.setCook_time(recipe.getCook_time());
        recipeEvent.setAdditionalNotes(recipe.getAdditional_notes());
        recipeEvent.setIngredients(recipe.getIngredients());
        recipeEvent.setInstructions(recipe.getInstructions());
        recipeEvent.setFavouriteRecipe(recipe.isFavouriteRecipe());
        recipeEvent.setCreatedOn(recipe.getCreatedOn());
        recipeEvent.setUpdatedOn(recipe.getUpdatedOn());
        recipeEvent.setUserId(recipe.getUser() != null ? recipe.getUser().getId() : null);
        return recipeEvent;
    }
}
