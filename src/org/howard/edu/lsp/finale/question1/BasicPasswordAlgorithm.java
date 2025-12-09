package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * "basic" algorithm: digits only using java.util.Random.
 */
public class BasicPasswordAlgorithm implements PasswordAlgorithm {
  private static final String DIGITS = "0123456789";
  private final Random random = new Random();

  @Override
  /**
   * Generate a digit-only password of the requested length.
   *
   * @param length desired length (> 0)
   * @return password consisting of digits 0-9
   */
  public String generate(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("length must be > 0");
    }
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int idx = random.nextInt(DIGITS.length());
      sb.append(DIGITS.charAt(idx));
    }
    return sb.toString();
  }
}
