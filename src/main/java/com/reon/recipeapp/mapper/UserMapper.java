package com.reon.recipeapp.mapper;

import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.model.User;

public class UserMapper {
    public static User mapToEntity(UserRegisterDTO register){
        User user = new User();
        user.setName(register.getName());
        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        return user;
    }

    public static UserResponseDTO responseToUser(User user){
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getEntityUsername());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles());
        response.setRecipeList(user.getRecipeList());
        response.setAccountEnabled(user.isAccountEnabled());
        response.setEmailVerified(user.isEmailVerified());
        response.setProvider(user.getProvider());
        response.setCreatedOn(user.getCreatedOn());
        response.setUpdatedOn(user.getUpdatedOn());
        return response;
    }

    public static void applyUpdates(User user, UserRegisterDTO update){
        if (update.getName() != null && !update.getName().isBlank()){
            user.setName(update.getName());
        }
        if (update.getUsername() != null && !update.getUsername().isBlank()){
            user.setUsername(update.getUsername());
        }
        if (update.getPassword() != null && !update.getPassword().isBlank()){
            user.setPassword(update.getPassword());
        }
    }
}
