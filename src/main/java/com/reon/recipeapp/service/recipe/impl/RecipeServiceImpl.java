package com.reon.recipeapp.service.recipe.impl;

import com.reon.recipeapp.dto.recipe.CreateRecipe;
import com.reon.recipeapp.dto.recipe.ViewRecipe;
import com.reon.recipeapp.exception.recipe.TitleAlreadyExistsException;
import com.reon.recipeapp.mapper.RecipeMapper;
import com.reon.recipeapp.model.Recipe;
import com.reon.recipeapp.model.User;
import com.reon.recipeapp.repository.RecipeRepository;
import com.reon.recipeapp.repository.UserRepository;
import com.reon.recipeapp.service.recipe.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public ViewRecipe createRecipe(CreateRecipe createRecipe) {

        if (recipeRepository.existsByTitle(createRecipe.getTitle())){
            throw new TitleAlreadyExistsException("Recipe with the current title already exists.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new SecurityException("User must be authenticated to create a recipe");
        }

        User authenticatedUser = (User) authentication.getPrincipal();
        logger.info("Service :: Creating new recipe with title: {} for user: {}",
                createRecipe.getTitle(), authenticatedUser.getEmail());

        Recipe recipe = RecipeMapper.mapToRecipe(createRecipe);

        recipe.setId(UUID.randomUUID().toString());
        recipe.setUser(authenticatedUser);
        recipe.setCreatedOn(LocalDateTime.now());
        recipe.setUpdatedOn(LocalDateTime.now());

        Recipe saveRecipe = recipeRepository.save(recipe);
        logger.info("Recipe saved with ID: {}", saveRecipe.getId());
        return RecipeMapper.recipeResponse(saveRecipe);
    }

    @Override
    public List<ViewRecipe> getAllRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll();
        return recipeList.stream().map(RecipeMapper :: recipeResponse).collect(Collectors.toList());
    }
}
