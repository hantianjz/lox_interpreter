package com.hjz.lox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.InputStream;

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
      content.append("\n");
    }
    scanner.close();

    // Assert the file content matches expected
    return content.toString().trim();
  }

  @Test
  void testScanEmptyFile() {
    Scanner scanner = new Scanner(readSource("test_lox/empty_file.lox"));
    List<Token> tokens = scanner.scanTokens();
    assertEquals(TokenType.EOF, tokens.get(0).type);
  }

  @Test
  void testScanNil() {
    Scanner scanner = new Scanner(readSource("test_lox/nil/literal.lox"));
    List<Token> tokens = scanner.scanTokens();
    ArrayList<TokenType> expected = new ArrayList<TokenType>(
        Arrays.asList(TokenType.PRINT, TokenType.NIL, TokenType.SEMICOLON, TokenType.EOF));
    assertEquals(expected.size(), tokens.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), tokens.get(i).type);
    }
  }

  @Test
  void testAssignmentSimple() {
    Scanner scanner = new Scanner(readSource("test_lox/assignment/simple.lox"));
    List<Token> tokens = scanner.scanTokens();
    ArrayList<TokenType> expected = new ArrayList<TokenType>(
        Arrays.asList(
            TokenType.VAR,
            TokenType.IDENTIFIER,
            TokenType.EQUAL,
            TokenType.STRING,
            TokenType.SEMICOLON,
            TokenType.PRINT,
            TokenType.IDENTIFIER,
            TokenType.SEMICOLON,
            TokenType.EOF));
    assertEquals(expected.size(), tokens.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), tokens.get(i).type);
    }
  }
}
