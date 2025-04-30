package com.reon.recipeapp.controller.recipe;

import com.reon.recipeapp.dto.recipe.CreateRecipe;
import com.reon.recipeapp.dto.recipe.ViewRecipe;
import com.reon.recipeapp.service.recipe.impl.RecipeServiceImpl;
import com.reon.recipeapp.validators.CreateRecipeValidator;
import com.reon.recipeapp.validators.UpdateRecipeValidator;
import jakarta.validation.groups.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RecipeController {
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ViewRecipe> createNewRecipe(
            @Validated({CreateRecipeValidator.class, Default.class}) @RequestBody CreateRecipe createRecipe){
        logger.info("Controller :: Incoming request for creating new recipe.");
        ViewRecipe newRecipe = recipeService.createRecipe(createRecipe);
        logger.info("Controller :: New Recipe saved with title: " + createRecipe.getTitle());
        return ResponseEntity.ok().body(newRecipe);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ViewRecipe> updateExistingRecipe(
            @Validated(UpdateRecipeValidator.class) @RequestBody CreateRecipe updateRecipe, @PathVariable String id){
        logger.info("Controller :: Incoming request for updating existing recipe with id: " + id);
        ViewRecipe updateExistingRecipe = recipeService.updateRecipe(id, updateRecipe);
        logger.info("Controller :: Updated existing recipe with id: " + id);
        return ResponseEntity.ok().body(updateExistingRecipe);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id){
        logger.warn("Controller :: Incoming request for deleting recipe with id: " + id);
        recipeService.deleteRecipe(id);
        logger.info("Controller :: Deleted recipe with id: " + id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ViewRecipe>> getAllRecipes(){
        List<ViewRecipe> allRecipes = recipeService.getAllRecipes();
        return ResponseEntity.ok().body(allRecipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewRecipe> getRecipeById(@PathVariable String id) {
        logger.info("Controller :: Fetching recipe with id: " + id);
        ViewRecipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok().body(recipe);
    }
}
