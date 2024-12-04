package com.hjz.lox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class ScannerTest {
  String readSource(String source) {
    // Load the resource file from src/test/resources
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(source);

    // Ensure the resource is not null
    assert inputStream != null;

    // Read the content of the file
    java.util.Scanner scanner = new java.util.Scanner(inputStream);
    StringBuilder content = new StringBuilder();
    while (scanner.hasNextLine()) {
      content.append(scanner.nextLine());
    }
    scanner.close();

    // Assert the file content matches expected
    return content.toString().trim();
  }

  @Test
  void testExpressionEval() {
    Scanner scanner = new Scanner(readSource("test_lox/expressions/evaluate.lox"));
    List<Token> tokens = scanner.scanTokens();
    assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
  }
}
