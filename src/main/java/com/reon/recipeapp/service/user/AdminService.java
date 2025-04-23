package com.reon.recipeapp.service.user;

import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.model.Role;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> fetchAllUsers();
    UserResponseDTO fetchById(String id);
    UserResponseDTO fetchByEmail(String email);
    UserResponseDTO fetchByUsername(String username);
    UserResponseDTO updateUserRole(String id, Role role);

    // Add multiple users at once
    List<UserResponseDTO> multiRegistration(List<UserRegisterDTO> registerDTOS);
}
