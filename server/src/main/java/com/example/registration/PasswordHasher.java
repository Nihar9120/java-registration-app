package com.example.registration;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** PBKDF2-HMAC-SHA256 password hashing. Format: iterations$salt$hash (Base64). */
public final class PasswordHasher {

  private static final int ITERATIONS = 210_000;
  private static final int KEY_BITS = 256;
  private static final SecureRandom RANDOM = new SecureRandom();

  private PasswordHasher() {
  }

  public static String hash(String password) {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);
    byte[] hash = derive(password, salt, ITERATIONS);
    return ITERATIONS + "$" + Base64.getEncoder().encodeToString(salt)
        + "$" + Base64.getEncoder().encodeToString(hash);
  }

  public static boolean matches(String password, String stored) {
    String[] parts = stored.split("\\$");
    if (parts.length != 3) {
      return false;
    }
    byte[] expected = Base64.getDecoder().decode(parts[2]);
    byte[] actual = derive(password, Base64.getDecoder().decode(parts[1]), Integer.parseInt(parts[0]));
    return MessageDigest.isEqual(expected, actual);
  }

  private static byte[] derive(String password, byte[] salt, int iterations) {
    try {
      PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, KEY_BITS);
      return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException("PBKDF2 unavailable", e);
    }
  }
}
