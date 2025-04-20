package com.reon.recipeapp.exception.recipe;

public class TitleAlreadyExistsException extends RuntimeException{
    public TitleAlreadyExistsException(String message) {
        super(message);
    }
}
