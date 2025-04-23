package com.reon.recipeapp.service.kafka.impl;

import com.reon.recipeapp.dto.recipe.RecipeEventDTO;
import com.reon.recipeapp.dto.user.UserEventDTO;
import com.reon.recipeapp.service.kafka.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendUserCreatedEvent(UserEventDTO userEvent) {
        logger.info("Publishing user created event for user: {}", userEvent.getEmail());
        kafkaTemplate.send("user.created", userEvent.getId(), userEvent);
    }

    @Override
    public void sendUserUpdatedEvent(UserEventDTO userEvent) {
        logger.info("Publishing user updated event for user: {}", userEvent.getEmail());
        kafkaTemplate.send("user.updated", userEvent.getId(), userEvent);
    }

    @Override
    public void sendUserDeletedEvent(String id) {
        logger.info("Publishing user deleted event for id: {}", id);
        kafkaTemplate.send("user.deleted", id, id);
    }

    @Override
    public void sendRecipeCreatedEvent(RecipeEventDTO recipeEvent) {
        logger.info("Publishing recipe created event for recipe: {}", recipeEvent.getTitle());
        kafkaTemplate.send("recipe.created", recipeEvent.getId(), recipeEvent);
    }
}
