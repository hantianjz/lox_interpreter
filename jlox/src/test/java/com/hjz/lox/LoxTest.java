package com.hjz.lox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class LoxTest {
  @Test
  void testHelloWorld() {
    String message = "Hello, World!";
    assertEquals("Hello, World!", message);
  }

  @Test
  void testWithResource() {
    // Load the resource file from src/test/resources
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test_lox/call/bool.lox");

    // Ensure the resource is not null
    assert inputStream != null;

    // Read the content of the file
    Scanner scanner = new Scanner(inputStream);
    StringBuilder content = new StringBuilder();
    while (scanner.hasNextLine()) {
      content.append(scanner.nextLine());
    }
    scanner.close();

    // Assert the file content matches expected
    assertEquals("true(); // expect runtime error: Can only call functions and classes.", content.toString().trim());
  }
}
