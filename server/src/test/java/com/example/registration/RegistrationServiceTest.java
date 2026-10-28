package com.example.registration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {

  private RegistrationService service;

  @BeforeEach
  void setUp() {
    service = new RegistrationService(new InMemoryUserRepository());
  }

  private static RegistrationRequest req(String name, String email, String pw, String confirm) {
    return new RegistrationRequest(name, email, pw, confirm);
  }

  @Test
  void registersValidUserAndHashesPassword() {
    RegistrationResult r = service.register(req(" Nihar ", "Nihar@Example.com", "secret123", "secret123"));
    assertTrue(r.isSuccess());
    assertEquals("Nihar", r.user().fullName());
    assertEquals("nihar@example.com", r.user().email());
    assertNotEquals("secret123", r.user().passwordHash());
    assertTrue(PasswordHasher.matches("secret123", r.user().passwordHash()));
    assertFalse(PasswordHasher.matches("wrong", r.user().passwordHash()));
  }

  @Test
  void rejectsMissingAndInvalidFields() {
    RegistrationResult r = service.register(req("", "not-an-email", "short", "short"));
    assertFalse(r.isSuccess());
    assertTrue(r.errors().containsKey("fullName"));
    assertTrue(r.errors().containsKey("email"));
    assertTrue(r.errors().containsKey("password"));
  }

  @Test
  void rejectsPasswordMismatch() {
    RegistrationResult r = service.register(req("A", "a@b.co", "secret123", "secret124"));
    assertEquals("Passwords do not match.", r.errors().get("confirmPassword"));
  }

  @Test
  void rejectsDuplicateEmailCaseInsensitively() {
    assertTrue(service.register(req("A", "a@b.co", "secret123", "secret123")).isSuccess());
    RegistrationResult dup = service.register(req("B", "A@B.CO", "secret123", "secret123"));
    assertFalse(dup.isSuccess());
    assertTrue(dup.errors().containsKey("email"));
  }
}
