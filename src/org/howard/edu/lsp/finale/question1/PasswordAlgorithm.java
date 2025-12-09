package org.howard.edu.lsp.finale.question1;

/**
 * Strategy interface for password generation algorithms.
 */
public interface PasswordAlgorithm {
  /**
   * Generate a password string of the given length.
   *
   * @param length desired length (must be > 0)
   * @return generated password
   */
  String generate(int length);
}
