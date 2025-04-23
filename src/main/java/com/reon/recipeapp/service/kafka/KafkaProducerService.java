package com.reon.recipeapp.service.kafka;

import com.reon.recipeapp.dto.recipe.RecipeEventDTO;
import com.reon.recipeapp.dto.user.UserEventDTO;

public interface KafkaProducerService {
    void sendUserCreatedEvent(UserEventDTO userEvent);
    void sendUserUpdatedEvent(UserEventDTO userEvent);
    void sendUserDeletedEvent(String id);
    void sendRecipeCreatedEvent(RecipeEventDTO recipeEvent);
}
