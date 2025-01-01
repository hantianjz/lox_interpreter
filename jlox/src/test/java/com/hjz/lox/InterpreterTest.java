package com.hjz.lox;

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
    String outString = runSource(path);
    List<String> outLines = Arrays.asList(outString.split("\\R"));

    assertLinesMatch(expectedLines, outLines);
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

  // @Test
  // void testOperatorAdd() {
  // verifyRun(
  // Arrays.asList("(print (+ 123.0 456.0))", "(print (+ \"str\" \"ing\"))"),
  // "test_lox/operator/add.lox");
  // }
  //
  // @Test
  // void testOperatorAddNil() {
  // verifyRun(
  // Arrays.asList("(+ true nil)"),
  // "test_lox/operator/add_bool_nil.lox");
  // verifyRun(
  // Arrays.asList("(+ true 123.0)"),
  // "test_lox/operator/add_bool_num.lox");
  // verifyRun(
  // Arrays.asList("(+ true \"s\")"),
  // "test_lox/operator/add_bool_string.lox");
  // verifyRun(
  // Arrays.asList("(+ nil nil)"),
  // "test_lox/operator/add_nil_nil.lox");
  // verifyRun(
  // Arrays.asList("(+ 1.0 nil)"),
  // "test_lox/operator/add_num_nil.lox");
  // verifyRun(
  // Arrays.asList("(+ \"s\" nil)"),
  // "test_lox/operator/add_string_nil.lox");
  // }
  //
  // @Test
  // void testBlock() {
  // verifyRun(
  // Arrays.asList("(declare IDENTIFIER a \"outer\")",
  // "{\n(declare IDENTIFIER a \"inner\")\n{\n(print IDENTIFIER a)\n}\n}",
  // "(print IDENTIFIER a)"),
  // "test_lox/block/nested.lox");
  //
  // verifyRun(
  // Arrays.asList("(declare IDENTIFIER a \"outer\")",
  // "{\n(declare IDENTIFIER a \"inner\")\n(print IDENTIFIER a)\n}",
  // "(print IDENTIFIER a)"),
  // "test_lox/block/scope.lox");
  // }
}
