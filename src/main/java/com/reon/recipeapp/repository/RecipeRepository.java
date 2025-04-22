package com.reon.recipeapp.repository;

import com.reon.recipeapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {
    boolean existsByTitle(String title);
    List<Recipe> findByUserId(String id);
}
