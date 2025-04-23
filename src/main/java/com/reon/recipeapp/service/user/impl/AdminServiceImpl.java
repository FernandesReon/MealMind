package com.reon.recipeapp.service.user.impl;

import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.exception.user.EmailAlreadyExistsException;
import com.reon.recipeapp.exception.user.UserNameAlreadyExistsException;
import com.reon.recipeapp.exception.user.UserNotFoundException;
import com.reon.recipeapp.mapper.UserMapper;
import com.reon.recipeapp.model.Role;
import com.reon.recipeapp.model.User;
import com.reon.recipeapp.repository.UserRepository;
import com.reon.recipeapp.service.user.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(value = "userCache", key = "'userInfo'")
    public List<UserResponseDTO> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                UserMapper :: responseToUser
        ).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserResponseDTO fetchById(String id) {
        logger.info("Fetching user with id: " + id);
        User optionalUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id: " + id + " not found!")
        );
        return UserMapper.responseToUser(optionalUser);
    }

    @Override
    @Cacheable(value = "users", key = "#email")
    public UserResponseDTO fetchByEmail(String email) {
        logger.info("Fetching user with email: " + email);
        User optionalEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email: " + email + " not found!")
        );
        return UserMapper.responseToUser(optionalEmail);
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public UserResponseDTO fetchByUsername(String username) {
        logger.info("Fetching user with username: " + username);
        User optionalUsername = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username: " + username + " not found!")
        );
        return UserMapper.responseToUser(optionalUsername);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserResponseDTO updateUserRole(String id, Role role) {
        logger.info("Accessing Administrative Service for promoting user with id: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return UserMapper.responseToUser(savedUser);
    }

    @Override
    @Cacheable(value = "userCache", key = "'userInfo'")
    public List<UserResponseDTO> multiRegistration(List<UserRegisterDTO> registerDTOS) {
        logger.info("Attempting to register {} users", registerDTOS.size());

        if (registerDTOS == null || registerDTOS.isEmpty()){
            throw new IllegalArgumentException("User list cannot be empty or null");
        }

        Set<String> emails = new HashSet<>();
        Set<String> usernames = new HashSet<>();

        for (UserRegisterDTO dto : registerDTOS) {
            if (!emails.add(dto.getEmail())) {
                throw new EmailAlreadyExistsException("Duplicate email in input: " + dto.getEmail());
            }
            if (!usernames.add(dto.getUsername())) {
                throw new UserNameAlreadyExistsException("Duplicate username in input: " + dto.getUsername());
            }
        }

        for (UserRegisterDTO userRegisterDTO : registerDTOS){
            if (userRepository.existsByEmail(userRegisterDTO.getEmail())){
                throw new EmailAlreadyExistsException(
                        "A user with this email already exists: " + userRegisterDTO.getEmail());
            }

            if (userRepository.existsByUsername(userRegisterDTO.getUsername())){
                throw new UserNameAlreadyExistsException(
                        "A user with this username already exists: " + userRegisterDTO.getUsername()
                );
            }
        }

        List<UserResponseDTO> savedUsers = new ArrayList<>();
        for (UserRegisterDTO userRegisterDTO: registerDTOS){
            logger.info("Creating user with email: {}", userRegisterDTO.getEmail());
            User user = UserMapper.mapToEntity(userRegisterDTO);

            String id = UUID.randomUUID().toString();
            user.setId(id);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(EnumSet.of(Role.USER));
            user.setAccountEnabled(true);
            user.setEmailVerified(true);

            User savedUser = userRepository.save(user);
            logger.info("User saved with id: {}", id);

            savedUsers.add(UserMapper.responseToUser(savedUser));
        }

        logger.info("Successfully registered {} users", savedUsers.size());
        return savedUsers;
    }
}
