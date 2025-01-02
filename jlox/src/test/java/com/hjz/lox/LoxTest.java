package com.hjz.lox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Scanner;

public class LoxTest extends Lox {

  @BeforeEach
  void setupEach() {
    Lox.clearError();
  }

  @Test
  void testWithResource() {
    // Load the resource file from src/test/resources
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test_lox/empty_file.lox");

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
    run(content.toString().trim());

    assertFalse(Lox.hasError());
  }
}
