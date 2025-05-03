package com.reon.recipeapp.controller.user;

import com.reon.recipeapp.dto.user.LoginResponseDTO;
import com.reon.recipeapp.dto.user.UserLoginDTO;
import com.reon.recipeapp.dto.user.UserRegisterDTO;
import com.reon.recipeapp.dto.user.UserResponseDTO;
import com.reon.recipeapp.exception.user.UserNotFoundException;
import com.reon.recipeapp.jwt.JwtService;
import com.reon.recipeapp.service.user.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserServiceImpl userService, JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO register){
        logger.info("Controller :: Incoming Registration request for user with emailId: " + register.getEmail());
        UserResponseDTO newUser = userService.registerUser(register);
        logger.info("Controller :: New user registered with emailId: " + register.getEmail());
        return ResponseEntity.ok().body(newUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody UserRegisterDTO update){
        logger.info("Controller :: Updating user with emailId: " + update.getEmail());
        UserResponseDTO updateUser = userService.updateUser(id, update);
        logger.info("Controller :: User updated successfully!");
        return ResponseEntity.ok().body(updateUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        logger.warn("Controller :: Request for deleting user with id: " + id);
        userService.deleteUser(id);
        logger.info("Controller :: User account with id: " + id + " deleted.");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginViaJwt(@RequestBody UserLoginDTO loginDTO) {
        logger.info("Login attempt for email: {}", loginDTO.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Generate JWT
            String token = jwtService.generateToken(loginDTO.getEmail(), userDetails.getAuthorities());
            // Fetch user details and map to UserResponseDTO
            UserResponseDTO userResponse = userService.findByEmail(loginDTO.getEmail());
            // Return token and user details
            LoginResponseDTO response = new LoginResponseDTO(token, userResponse);
            logger.info("Login successful for email: {}", loginDTO.getEmail());
            return ResponseEntity.ok(response);
        }
        throw new UserNotFoundException("Invalid email or password");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        userService.logoutUser();
        logger.info("User logged out");
        return ResponseEntity.ok("Logged out successfully");
    }
}
