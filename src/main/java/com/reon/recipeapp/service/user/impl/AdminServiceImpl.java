package com.reon.recipeapp.service.user.impl;

import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.exception.user.UserNotFoundException;
import com.reon.recipeapp.mapper.UserMapper;
import com.reon.recipeapp.model.Role;
import com.reon.recipeapp.model.User;
import com.reon.recipeapp.repository.UserRepository;
import com.reon.recipeapp.service.user.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDTO> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                UserMapper :: responseToUser
        ).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO fetchById(String id) {
        logger.info("Fetching user with id: " + id);
        User optionalUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id: " + id + " not found!")
        );
        return UserMapper.responseToUser(optionalUser);
    }

    @Override
    public UserResponseDTO fetchByEmail(String email) {
        logger.info("Fetching user with email: " + email);
        User optionalEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email: " + email + " not found!")
        );
        return UserMapper.responseToUser(optionalEmail);
    }

    @Override
    public UserResponseDTO fetchByUsername(String username) {
        logger.info("Fetching user with username: " + username);
        User optionalUsername = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username: " + username + " not found!")
        );
        return UserMapper.responseToUser(optionalUsername);
    }

    @Override
    public UserResponseDTO updateUserRole(String id, Role role) {
        logger.info("Accessing Administrative Service for promoting user with id: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return UserMapper.responseToUser(savedUser);
    }
}
