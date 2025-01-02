package com.hjz.lox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

public class InterpreterTest {
  String runSource(String source) {
    // Load the resource file from src/test/resources
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(source);
    try {
      return Lox.runInputStream(inputStream);
    } catch (IOException e) {
      System.err.println(e);
    }
    return "";
  }

  void verifyRun(List<String> expectedLines, String path) {
    Lox.clearError();
    String outString = runSource(path);
    List<String> outLines = Arrays.asList(outString.split("\\R"));

    assertLinesMatch(expectedLines, outLines);
  }

  @BeforeEach
  void setupEach() {
    Lox.clearError();
  }

  @Test
  void testScanNil() {
    verifyRun(Arrays.asList("nil"), "test_lox/nil/literal.lox");
  }

  @Test
  void testAssignment() {
    verifyRun(
        Arrays.asList("!@34"), "test_lox/assignment/simple.lox");

    verifyRun(
        Arrays.asList("c", "c", "c"),
        "test_lox/assignment/associativity.lox");

    verifyRun(
        Arrays.asList(
            "before", "after", "arg", "arg"),
        "test_lox/assignment/global.lox");
  }

  @Test
  void testOperatorAdd() {
    verifyRun(
        Arrays.asList("579", "string"),
        "test_lox/operator/add.lox");
  }

  @Test
  void testOperatorAddNil() {
    verifyRun(
        Arrays.asList("Runtime Error: Operands must be two numbers or two strings. [line 1]"),
        "test_lox/operator/add_bool_nil.lox");
    verifyRun(
        Arrays.asList("Runtime Error: Operands must be two numbers or two strings. [line 1]"),
        "test_lox/operator/add_bool_num.lox");
    verifyRun(
        Arrays.asList("Runtime Error: Operands must be two numbers or two strings. [line 1]"),
        "test_lox/operator/add_bool_string.lox");
    verifyRun(
        Arrays.asList("Runtime Error: Operands must be two numbers or two strings. [line 1]"),
        "test_lox/operator/add_nil_nil.lox");
    verifyRun(
        Arrays.asList("Runtime Error: Operands must be two numbers or two strings. [line 1]"),
        "test_lox/operator/add_num_nil.lox");
    verifyRun(
        Arrays.asList("Runtime Error: Operands must be two numbers or two strings. [line 1]"),
        "test_lox/operator/add_string_nil.lox");
  }

  @Test
  void testBlock() {
    verifyRun(Arrays.asList("inner", "outer"), "test_lox/block/nested.lox");

    verifyRun(Arrays.asList("inner", "outer"), "test_lox/block/scope.lox");
  }
}
