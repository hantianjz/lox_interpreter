package com.hjz.lox;

class Token {
  final TokenType type;
  final String lexeme;
  final Object literal;
  final int line;

  Token(TokenType type, String lexeme, Object literal, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.line = line;
  }

  public String toString() {
    if (type == TokenType.IDENTIFIER) {
      return lexeme;
    } else {
      return type + " " + lexeme + " " + literal;
    }
  }
}
