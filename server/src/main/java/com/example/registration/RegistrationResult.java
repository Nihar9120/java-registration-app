package com.example.registration;

import java.util.Map;

/** Outcome of a registration attempt: either a created user or field errors. */
public record RegistrationResult(User user, Map<String, String> errors) {

  public static RegistrationResult success(User user) {
    return new RegistrationResult(user, Map.of());
  }

  public static RegistrationResult failure(Map<String, String> errors) {
    return new RegistrationResult(null, Map.copyOf(errors));
  }

  public boolean isSuccess() {
    return user != null;
  }
}
