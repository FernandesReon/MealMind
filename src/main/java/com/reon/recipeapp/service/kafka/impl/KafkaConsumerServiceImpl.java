package com.reon.recipeapp.service.kafka.impl;

import com.reon.recipeapp.dto.recipe.RecipeEventDTO;
import com.reon.recipeapp.dto.user.UserEventDTO;
import com.reon.recipeapp.service.kafka.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);
    @Override
    @KafkaListener(topics = "user.created", groupId = "mindmeal-group")
    public void consumeUserCreatedEvent(UserEventDTO userEvent) {
        logger.info("Received user created event: User ID: {}, Email: {}",
                userEvent.getId(), userEvent.getEmail());
    }

    @Override
    @KafkaListener(topics = "user.updated", groupId = "mindmeal-group")
    public void consumeUserUpdatedEvent(UserEventDTO userEvent) {
        logger.info("Received user updated event: User ID: {}, Email: {}",
                userEvent.getId(), userEvent.getEmail());
    }

    @Override
    @KafkaListener(topics = "user.deleted", groupId = "mindmeal-group")
    public void consumeUserDeletedEvent(String id) {
        logger.info("Received user deleted event: User ID: {}", id);
    }

    @Override
    @KafkaListener(topics = "recipe.created", groupId = "mindmeal-group")
    public void consumeRecipeCreatedEvent(RecipeEventDTO recipeEvent) {
        logger.info("Received recipe created event: Recipe ID: {}, Title: {}, User ID: {}",
                recipeEvent.getId(), recipeEvent.getTitle(), recipeEvent.getUserId());
    }
}
