package org.howard.edu.lsp.finale.question1;

/**
 * Singleton service that delegates password generation to a swappable strategy.
 */
public class PasswordGeneratorService {
  /*
   * Design Patterns Used:
   * - Singleton: single shared access point via getInstance().
   * - Strategy: swappable password-generation behaviors selected at runtime; allows
   *   future algorithms without client changes.
   */
  private static final PasswordGeneratorService INSTANCE = new PasswordGeneratorService();
  private PasswordAlgorithm currentAlgorithm;

  private PasswordGeneratorService() {}

  /**
   * Provide the single shared instance of the password generator service.
   *
   * @return singleton instance
   */
  public static PasswordGeneratorService getInstance() {
    return INSTANCE;
  }

  /**
   * Select the password generation algorithm by name.
   *
   * @param name must be one of "basic", "enhanced", or "letters"
   * @throws IllegalArgumentException if name is null or unknown
   */
  public void setAlgorithm(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Algorithm name cannot be null");
    }
    switch (name.toLowerCase()) {
      case "basic": currentAlgorithm = new BasicPasswordAlgorithm(); break;
      case "enhanced": currentAlgorithm = new EnhancedPasswordAlgorithm(); break;
      case "letters": currentAlgorithm = new LettersPasswordAlgorithm(); break;
      default: throw new IllegalArgumentException("Unknown algorithm: " + name);
    }
  }

  /**
   * Generate a password using the currently selected algorithm.
   *
   * @param length desired password length
   * @return generated password string
   * @throws IllegalStateException if no algorithm has been selected
   */
  public String generatePassword(int length) {
    if (currentAlgorithm == null) {
      throw new IllegalStateException("No algorithm selected. Call setAlgorithm(name) first.");
    }
    return currentAlgorithm.generate(length);
  }

  // Package-private helper for tests to reset singleton state.
  void clearAlgorithmSelection() {
    currentAlgorithm = null;
  }
}
