package com.reon.recipeapp.service.user;

import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRegisterDTO registerDTO);
    UserResponseDTO updateUser(String id, UserRegisterDTO updateDTO);
    void deleteUser(String id);
    UserResponseDTO findByEmail(String email);
    void logoutUser();
}
