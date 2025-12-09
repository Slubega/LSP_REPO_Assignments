package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

  private PasswordGeneratorService service;

  @BeforeEach
  public void setup() {
    service = PasswordGeneratorService.getInstance();
    // Reset singleton state so test order doesn't matter
    service.clearAlgorithmSelection();
  }

  @Test
  public void checkInstanceNotNull() {
    assertNotNull(service);
  }

  @Test
  public void checkSingleInstanceBehavior() {
    PasswordGeneratorService second = PasswordGeneratorService.getInstance();
    // Must confirm true singleton behavior (same object in memory)
    assertSame(service, second);
  }

  @Test
  public void generateWithoutSettingAlgorithmThrowsException() {
    PasswordGeneratorService s = PasswordGeneratorService.getInstance();
    s.clearAlgorithmSelection();
    assertThrows(IllegalStateException.class, () -> s.generatePassword(10));
  }

  @Test
  public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
    service.setAlgorithm("basic");
    String p = service.generatePassword(10);

    assertEquals(10, p.length());
    assertTrue(p.matches("\\d{10}"), "basic must generate digits only");
  }

  @Test
  public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
    service.setAlgorithm("enhanced");
    String p = service.generatePassword(12);

    assertEquals(12, p.length());
    assertTrue(p.matches("[A-Za-z0-9]{12}"), "enhanced must be alphanumeric only");
  }

  @Test
  public void lettersAlgorithmGeneratesLettersOnly() {
    service.setAlgorithm("letters");
    String p = service.generatePassword(8);

    assertEquals(8, p.length());
    assertTrue(p.matches("[A-Za-z]{8}"), "letters must generate letters only");
  }

  @Test
  public void switchingAlgorithmsChangesBehavior() {
    service.setAlgorithm("basic");
    String p1 = service.generatePassword(10);

    service.setAlgorithm("letters");
    String p2 = service.generatePassword(10);

    service.setAlgorithm("enhanced");
    String p3 = service.generatePassword(10);

    assertTrue(p1.matches("\\d{10}"), "basic should be digits only");
    assertTrue(p2.matches("[A-Za-z]{10}"), "letters should be letters only");
    assertTrue(p3.matches("[A-Za-z0-9]{10}"), "enhanced should be alphanumeric only");
  }
}
