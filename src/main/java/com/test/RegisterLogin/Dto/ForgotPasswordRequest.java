package com.test.RegisterLogin.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ForgotPasswordRequest {

    @NotNull(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
