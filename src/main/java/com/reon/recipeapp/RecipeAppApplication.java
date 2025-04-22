package com.reon.recipeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecipeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeAppApplication.class, args);
    }

}
