package com.example.registration;

import java.util.List;
import java.util.Optional;

/** Storage abstraction; swap the in-memory implementation for a database later. */
public interface UserRepository {

  Optional<User> findByEmail(String email);

  User save(String fullName, String email, String passwordHash);

  List<User> findAll();
}
