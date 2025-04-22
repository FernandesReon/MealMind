package com.reon.recipeapp.service.recipe.impl;

import com.reon.recipeapp.dto.recipe.CreateRecipe;
import com.reon.recipeapp.dto.recipe.ViewRecipe;
import com.reon.recipeapp.exception.recipe.RecipeNotFoundException;
import com.reon.recipeapp.exception.recipe.TitleAlreadyExistsException;
import com.reon.recipeapp.mapper.RecipeMapper;
import com.reon.recipeapp.model.Recipe;
import com.reon.recipeapp.model.User;
import com.reon.recipeapp.repository.RecipeRepository;
import com.reon.recipeapp.service.recipe.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @CacheEvict(value = "recipeCache", key = "'recipeInfo'")
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
    @Cacheable(value = "recipeCache", key = "'recipeInfo'")
    public List<ViewRecipe> getAllRecipes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new SecurityException("User must be authenticated to view recipes");
        }
        User authenticatedUser = (User) authentication.getPrincipal();
        logger.info("Service :: Fetching recipes for user: {}", authenticatedUser.getEmail());

        List<Recipe> recipeList = recipeRepository.findByUserId(authenticatedUser.getId());
        return recipeList.stream()
                .map(RecipeMapper :: recipeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            put = @CachePut(value = "recipe", key = "#id"),
            evict = @CacheEvict(value = "recipeCache", key = "'recipeInfo'")
    )
    public ViewRecipe updateRecipe(String id, CreateRecipe updateRecipe) {
        logger.info("Service :: Updating recipe for user with id: {}", id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User must be authenticated to update a recipe");
        }

        User authenticatedUser = (User) authentication.getPrincipal();
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with id: " + id + " not found!"));

        if (!existingRecipe.getUser().getId().equals(authenticatedUser.getId())) {
            throw new SecurityException("User not authorized to update this recipe");
        }

        RecipeMapper.applyUpdates(existingRecipe, updateRecipe);
        existingRecipe.setUpdatedOn(LocalDateTime.now());
        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        logger.info("Recipe updated successfully for id: {}", id);
        return RecipeMapper.recipeResponse(updatedRecipe);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "recipe", key = "#id"),
                    @CacheEvict(value = "recipeCache", key = "'recipeInfo'")
            }
    )
    public void deleteRecipe(String id) {
        logger.info("Service :: Deleting recipe with id: " + id);
        recipeRepository.deleteById(id);
    }
}
