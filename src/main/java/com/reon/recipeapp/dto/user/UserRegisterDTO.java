package com.reon.recipeapp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    @NotBlank(
            message = "Name is required")
    private String name;

    @NotBlank(
            message = "Username is required")
    @Size(max = 10, message = "Username can be maximum of 10 characters")
    private String username;

    @NotBlank(
            message = "Email is required")
    @Email(message = "Invalid format")
    private String email;

    @NotBlank(
            message = "Password is required")
    @Size(min = 10, max = 64, message = "Password has to be minimum of 10 characters")
    private String password;
}
