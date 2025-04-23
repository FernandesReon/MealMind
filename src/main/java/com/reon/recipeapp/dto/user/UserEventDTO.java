package com.reon.recipeapp.dto.user;

import com.reon.recipeapp.model.Provider;
import com.reon.recipeapp.model.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserEventDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles;
    private boolean accountEnabled;
    private boolean emailVerified;
    private Provider provider;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
