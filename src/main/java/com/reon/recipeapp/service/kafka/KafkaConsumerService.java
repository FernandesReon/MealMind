package com.reon.recipeapp.service.kafka;

import com.reon.recipeapp.dto.recipe.RecipeEventDTO;
import com.reon.recipeapp.dto.user.UserEventDTO;

public interface KafkaConsumerService {
    void consumeUserCreatedEvent(UserEventDTO userEvent);
    void consumeUserUpdatedEvent(UserEventDTO userEvent);
    void consumeUserDeletedEvent(String id);
    void consumeRecipeCreatedEvent(RecipeEventDTO recipeEvent);
}
