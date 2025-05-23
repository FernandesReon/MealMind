package com.reon.recipeapp.controller.user;

import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.model.Role;
import com.reon.recipeapp.service.user.impl.AdminServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        List<UserResponseDTO> users = adminService.fetchAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id){
        logger.info("Fetching user with id: " + id);
        UserResponseDTO user = adminService.fetchById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        logger.info("Fetching user with email: " + email);
        UserResponseDTO user = adminService.fetchByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user with username: " + username);
        UserResponseDTO user = adminService.fetchByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Important Endpoint
    @PostMapping("/{userId}/promote-to-admin")
    public ResponseEntity<UserResponseDTO> promoteToAdmin(@PathVariable String userId){
        UserResponseDTO updatedUser = adminService.updateUserRole(userId, Role.ADMIN);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/register/bulk")
    public ResponseEntity<List<UserResponseDTO>> registerMultipleUsers(@Valid @RequestBody List<UserRegisterDTO> registerDTOs){
        logger.info("Bulk registration controller :: registering {} users", registerDTOs.size());
        List<UserResponseDTO> newUsers = adminService.multiRegistration(registerDTOs);
        logger.info("Successfully registered {} users", newUsers.size());
        return ResponseEntity.ok().body(newUsers);
    }
}
