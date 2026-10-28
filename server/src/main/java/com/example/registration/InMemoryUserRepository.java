package com.example.registration;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepository implements UserRepository {

  private final Map<String, User> byEmail = new ConcurrentHashMap<>();
  private final AtomicLong ids = new AtomicLong();

  @Override
  public Optional<User> findByEmail(String email) {
    return Optional.ofNullable(byEmail.get(email));
  }

  /** Atomic: throws IllegalStateException if the email is already taken. */
  @Override
  public User save(String fullName, String email, String passwordHash) {
    User user = new User(ids.incrementAndGet(), fullName, email, passwordHash, Instant.now());
    if (byEmail.putIfAbsent(email, user) != null) {
      throw new IllegalStateException("Email already registered");
    }
    return user;
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(byEmail.values());
  }
}
