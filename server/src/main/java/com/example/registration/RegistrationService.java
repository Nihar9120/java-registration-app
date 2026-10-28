package com.example.registration;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationService {

  private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
  static final int MIN_PASSWORD_LENGTH = 8;

  private final UserRepository repository;

  public RegistrationService(UserRepository repository) {
    this.repository = repository;
  }

  public RegistrationResult register(RegistrationRequest request) {
    Map<String, String> errors = new LinkedHashMap<>();

    String name = request.fullName() == null ? "" : request.fullName().strip();
    String email = request.email() == null ? "" : request.email().strip().toLowerCase(Locale.ROOT);
    String password = request.password() == null ? "" : request.password();

    if (name.isEmpty()) {
      errors.put("fullName", "Full name is required.");
    }
    if (email.isEmpty()) {
      errors.put("email", "Email is required.");
    } else if (!EMAIL.matcher(email).matches()) {
      errors.put("email", "Enter a valid email address.");
    }
    if (password.length() < MIN_PASSWORD_LENGTH) {
      errors.put("password", "Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
    } else if (!password.equals(request.confirmPassword())) {
      errors.put("confirmPassword", "Passwords do not match.");
    }
    if (!errors.containsKey("email") && repository.findByEmail(email).isPresent()) {
      errors.put("email", "This email is already registered.");
    }
    if (!errors.isEmpty()) {
      return RegistrationResult.failure(errors);
    }

    try {
      return RegistrationResult.success(repository.save(name, email, PasswordHasher.hash(password)));
    } catch (IllegalStateException duplicate) {
      return RegistrationResult.failure(Map.of("email", "This email is already registered."));
    }
  }
}
