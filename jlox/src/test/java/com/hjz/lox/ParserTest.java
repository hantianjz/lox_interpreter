package com.hjz.lox;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.io.InputStream;

public class ParserTest {
  List<Token> parseSource(String source) {
    // Load the resource file from src/test/resources
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(source);

    // Ensure the resource is not null
    assert inputStream != null;

    // Read the content of the file
    java.util.Scanner streamScanner = new java.util.Scanner(inputStream);
    StringBuilder content = new StringBuilder();
    while (streamScanner.hasNextLine()) {
      content.append(streamScanner.nextLine());
      content.append("\n");
    }
    streamScanner.close();

    Scanner scanner = new Scanner(content.toString().trim());
    return scanner.scanTokens();
  }

  void verifyParse(List<String> expectedLines, String path) {
    List<Token> tokens = parseSource(path);
    Parser parser = new Parser(tokens);
    List<Stmt> statements = parser.parse();
    List<String> ast = new AstPrinter().printLines(statements);

    assertLinesMatch(expectedLines, ast);
  }

  @BeforeEach
  void setupEach() {
    Lox.clearError();
  }

  @Test
  void testScanNil() {
    List<Token> tokens = parseSource("test_lox/nil/literal.lox");
    Parser parser = new Parser(tokens);
    List<Stmt> statements = parser.parse();

    String ast = new AstPrinter().print(statements);
    assertEquals("(print nil)", ast);
  }

  @Test
  void testAssignment() {
    verifyParse(
        Arrays.asList("(declare a \"!@34\")", "(print a)"),
        "test_lox/assignment/simple.lox");

    verifyParse(
        Arrays.asList("(declare a \"a\")", "(declare b \"b\")",
            "(declare c \"c\")", "(assign a (assign b c))",
            "(print a)", "(print b)", "(print c)"),
        "test_lox/assignment/associativity.lox");

    verifyParse(
        Arrays.asList("(declare a \"before\")", "(print a)", "(assign a \"after\")",
            "(print a)", "(print (assign a \"arg\"))", "(print a)"),
        "test_lox/assignment/global.lox");
  }

  @Test
  void testOperatorAdd() {
    verifyParse(
        Arrays.asList("(print (+ 123.0 456.0))", "(print (+ \"str\" \"ing\"))"),
        "test_lox/operator/add.lox");
  }

  @Test
  void testOperatorAddNil() {
    verifyParse(
        Arrays.asList("(+ true nil)"),
        "test_lox/operator/add_bool_nil.lox");
    verifyParse(
        Arrays.asList("(+ true 123.0)"),
        "test_lox/operator/add_bool_num.lox");
    verifyParse(
        Arrays.asList("(+ true \"s\")"),
        "test_lox/operator/add_bool_string.lox");
    verifyParse(
        Arrays.asList("(+ nil nil)"),
        "test_lox/operator/add_nil_nil.lox");
    verifyParse(
        Arrays.asList("(+ 1.0 nil)"),
        "test_lox/operator/add_num_nil.lox");
    verifyParse(
        Arrays.asList("(+ \"s\" nil)"),
        "test_lox/operator/add_string_nil.lox");
  }

  @Test
  void testBlock() {
    verifyParse(
        Arrays.asList("(declare a \"outer\")",
            "{\n(declare a \"inner\")\n{\n(print a)\n}\n}",
            "(print a)"),
        "test_lox/block/nested.lox");

    verifyParse(
        Arrays.asList("(declare a \"outer\")",
            "{\n(declare a \"inner\")\n(print a)\n}",
            "(print a)"),
        "test_lox/block/scope.lox");
  }
}
