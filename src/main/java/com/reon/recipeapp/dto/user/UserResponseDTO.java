package com.reon.recipeapp.dto.user;

import com.reon.recipeapp.model.Provider;
import com.reon.recipeapp.model.Recipe;
import com.reon.recipeapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles;
    private List<Recipe> recipeList;
    private boolean accountEnabled;
    private boolean emailVerified;
    private Provider provider;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
