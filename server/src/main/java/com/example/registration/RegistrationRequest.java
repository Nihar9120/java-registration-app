package com.example.registration;

/** Raw input from the registration form. */
public record RegistrationRequest(String fullName, String email, String password, String confirmPassword) {
}
