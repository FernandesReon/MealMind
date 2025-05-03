package com.reon.recipeapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String title;
    private String description;
    private Integer servings;
    private Integer prep_time;
    private Integer cook_time;
    private String additional_notes;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> ingredients;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> instructions;

    private boolean isFavouriteRecipe = false;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime createdOn;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
