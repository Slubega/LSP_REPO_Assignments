package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * "letters" algorithm: letters only (A-Z, a-z).
 */
public class LettersPasswordAlgorithm implements PasswordAlgorithm {
  private static final String LETTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
      "abcdefghijklmnopqrstuvwxyz";

  private final Random random = new Random();

  @Override
  /**
   * Generate a letters-only password (A-Z, a-z) of the requested length.
   *
   * @param length desired length (> 0)
   * @return letters-only password
   */
  public String generate(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("length must be > 0");
    }
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int idx = random.nextInt(LETTERS.length());
      sb.append(LETTERS.charAt(idx));
    }
    return sb.toString();
  }
}
