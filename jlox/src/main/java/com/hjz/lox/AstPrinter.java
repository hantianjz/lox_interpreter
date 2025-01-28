package com.hjz.lox;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }

  String print(Stmt stmt) {
    return stmt.accept(this);
  }

  String print(List<Stmt> stmts) {
    StringBuilder builder = new StringBuilder();
    for (Stmt stmt : stmts) {
      builder.append(stmt.accept(this));
    }
    return builder.toString();
  }

  List<String> printLines(List<Stmt> stmts) {
    List<String> lines = new ArrayList<String>();
    for (Stmt stmt : stmts) {
      lines.add(stmt.accept(this));
    }
    return lines;
  }

  @Override
  public String visitTernaryExpr(Expr.Ternary expr) {
    return parenthesize(expr.operator.lexeme,
        expr.eval, expr.left, expr.right);
  }

  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    return parenthesize("assign " + expr.name.lexeme,
        expr.value);
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme,
        expr.left, expr.right);
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null)
      return "nil";
    if (expr.value instanceof String) {
      return "\"" + expr.value.toString() + "\"";
    } else {
      return expr.value.toString();
    }
  }

  @Override
  public String visitLogicalExpr(Expr.Logical expr) {
    StringBuilder builder = new StringBuilder();
    builder.append(expr.left.accept(this));
    if (expr.operator.type == TokenType.OR) {
      builder.append(" || ");
    } else if (expr.operator.type == TokenType.AND) {
      builder.append(" && ");
    }
    builder.append(expr.right.accept(this));
    return builder.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }

  public String visitVariableExpr(Expr.Variable expr) {
    return expr.name.toString();
  }

  public String visitBlockStmt(Stmt.Block stmt) {
    return parenthesize(stmt.statements);
  }

  public String visitExpressionStmt(Stmt.Expression stmt) {
    return stmt.expression.accept(this);
  }

  public String visitPrintStmt(Stmt.Print stmt) {
    return parenthesize("print", stmt.expression);
  }

  public String visitIfStmt(Stmt.If stmt) {
    return parenthesize("if", stmt.condition, stmt.thenBranch, stmt.elseBranch);
  }

  public String visitVarStmt(Stmt.Var stmt) {
    return parenthesize("declare " + stmt.name.toString(), stmt.initializer);
  }

  private String parenthesize(List<Stmt> statements) {
    StringBuilder builder = new StringBuilder();

    builder.append("{");
    for (Stmt stmt : statements) {
      builder.append("\n");
      builder.append(stmt.accept(this));
    }
    builder.append("\n}");

    return builder.toString();
  }

  private String parenthesize(String name, Expr cond, Stmt thenBranch, Stmt elseBranch) {
    StringBuilder builder = new StringBuilder();

    builder.append(name).append(" (").append(cond.accept(this)).append(") ");
    builder.append(thenBranch.accept(this));
    if (elseBranch != null) {
      builder.append("else");
      builder.append(" { ").append(elseBranch.accept(this)).append(" }");
    }

    return builder.toString();
  }

  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    builder.append(")");

    return builder.toString();
  }

  public static void main(String[] args) throws IOException {
    String path = new String("");
    if (args.length > 1) {
      System.out.println("Usage: jlox [script]");
      System.exit(64);
    } else {
      path = new String(args[0]);
    }

    byte[] bytes = Files.readAllBytes(Paths.get(path));

    Scanner scanner = new Scanner(new String(bytes, Charset.defaultCharset()));
    List<Token> tokens = scanner.scanTokens();
    Parser parser = new Parser(tokens);
    List<Stmt> statements = parser.parse();

    List<String> lines = new AstPrinter().printLines(statements);

    if (Lox.hasError()) {
      System.err.println(Lox.getErrorString());
    } else {
      for (String line : lines) {
        System.out.println(line);
      }
    }
  }
}
