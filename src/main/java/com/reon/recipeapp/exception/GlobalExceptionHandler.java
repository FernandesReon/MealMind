package com.reon.recipeapp.exception;

import com.reon.recipeapp.exception.recipe.RecipeNotFoundException;
import com.reon.recipeapp.exception.recipe.TitleAlreadyExistsException;
import com.reon.recipeapp.exception.user.EmailAlreadyExistsException;
import com.reon.recipeapp.exception.user.UserNameAlreadyExistsException;
import com.reon.recipeapp.exception.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailException(EmailAlreadyExistsException emailException){
        logger.warn("Email Exception: {}", emailException.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "User with this email already exists.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameException(UserNameAlreadyExistsException usernameException){
        logger.warn("Username Exception: {}", usernameException.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "User with this username already exists.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException
    ){
        logger.warn("User Exception: {}", userNotFoundException.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "User not found");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(RestrictionException.class)
    public ResponseEntity<Map<String, String>> handleRestrictionException(
            RestrictionException restrict
    ){
        logger.warn("Restriction Exception: {}", restrict.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "This operations is restricted due to security reasons.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(TitleAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleTitleExisting(TitleAlreadyExistsException existsException){
        logger.warn("Title already exists: {}", existsException.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Title already exists. Try a new one.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRecipeNotFound(RecipeNotFoundException recipeNotFoundException){
        logger.warn("Recipe Exception: {}", recipeNotFoundException.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Recipe not found.");
        return ResponseEntity.badRequest().body(error);
    }
}
