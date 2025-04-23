package com.reon.recipeapp.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic userCreateTopic(){
        return TopicBuilder.name("user.created")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic userUpdateTopic(){
        return TopicBuilder.name("user.updated")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic userDeletedTopic(){
        return TopicBuilder.name("user.deleted")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic recipeCreateTopic(){
        return TopicBuilder.name("recipe.created")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
