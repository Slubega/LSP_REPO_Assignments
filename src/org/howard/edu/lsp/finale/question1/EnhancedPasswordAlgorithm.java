package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * "enhanced" algorithm: A-Z, a-z, 0-9 using java.security.SecureRandom.
 */
public class EnhancedPasswordAlgorithm implements PasswordAlgorithm {
  private static final String ALLOWED =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
      "abcdefghijklmnopqrstuvwxyz" +
      "0123456789";

  private final SecureRandom random = new SecureRandom();

  @Override
  /**
   * Generate an alphanumeric password (A-Z, a-z, 0-9) of the requested length.
   *
   * @param length desired length (> 0)
   * @return alphanumeric password
   */
  public String generate(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("length must be > 0");
    }
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int idx = random.nextInt(ALLOWED.length());
      sb.append(ALLOWED.charAt(idx));
    }
    return sb.toString();
  }
}
