package com.reon.recipeapp.controller.recipe;

import com.reon.recipeapp.dto.recipe.CreateRecipe;
import com.reon.recipeapp.dto.recipe.ViewRecipe;
import com.reon.recipeapp.service.recipe.impl.RecipeServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ViewRecipe> createNewRecipe(@Valid @RequestBody CreateRecipe createRecipe){
        logger.info("Controller :: Incoming request for creating new recipe.");
        ViewRecipe newRecipe = recipeService.createRecipe(createRecipe);
        logger.info("Controller :: New Recipe saved with title: " + createRecipe.getTitle());
        return ResponseEntity.ok().body(newRecipe);
    }
}
