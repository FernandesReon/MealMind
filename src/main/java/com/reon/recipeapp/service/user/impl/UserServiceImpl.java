package com.reon.recipeapp.service.user.impl;

import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.exception.user.EmailAlreadyExistsException;
import com.reon.recipeapp.exception.RestrictionException;
import com.reon.recipeapp.exception.user.UserNameAlreadyExistsException;
import com.reon.recipeapp.exception.user.UserNotFoundException;
import com.reon.recipeapp.mapper.UserMapper;
import com.reon.recipeapp.model.User;
import com.reon.recipeapp.repository.UserRepository;
import com.reon.recipeapp.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO registerUser(UserRegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())){
            throw new EmailAlreadyExistsException("A user with this email already exists: " + registerDTO.getEmail());
        }
        if (userRepository.existsByUsername(registerDTO.getUsername())){
            throw new UserNameAlreadyExistsException("A user with this username already exists: " + registerDTO.getUsername());
        }

        logger.info("Service :: Creating user with emailId: " + registerDTO.getEmail());
        User user = UserMapper.mapToEntity(registerDTO);

        String userId = UUID.randomUUID().toString();
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedOn(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        logger.info("User saved.");

        return UserMapper.responseToUser(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(String id, UserRegisterDTO updateDTO) {
        logger.info("Service :: Updating an user with id: " + id);
        User exisitingUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );

        if (updateDTO.getEmail() != null && !updateDTO.getEmail().isBlank()){
            throw new RestrictionException("Updating emailId is not allowed.");
        }
        else {
            UserMapper.applyUpdates(exisitingUser, updateDTO);
            exisitingUser.setUpdatedOn(LocalDateTime.now());
            User savedUser = userRepository.save(exisitingUser);
            logger.info("User updated successfully [id]: " + id);
            return UserMapper.responseToUser(savedUser);
        }
    }

    @Override
    public void deleteUser(String id) {
        logger.warn("Deleting user with id: " + id);
        userRepository.deleteById(id);
    }
}
