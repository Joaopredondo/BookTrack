package com.booktrack.core.dto;

import lombok.Data;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class UserDTO {
    @NotNull(message = "User ID cannot be null")
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String Name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Phone cannot be blank")
    private String phone;
    @NotNull(message = "Registration date cannot be null")
    private LocalDate registrationDate;
}
