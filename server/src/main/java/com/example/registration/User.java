package com.example.registration;

import java.time.Instant;

/** A registered user. The password is stored only as a salted hash. */
public record User(long id, String fullName, String email, String passwordHash, Instant registeredAt) {
}
