import java.util.List;

public class Parser {
  private final List<Token> tokens;
  private final Token EOF = new Token(TokenType.EOF, "");
  private final Token CLASS = new Token(TokenType.CLASS, "class");
  private final Token ELSE = new Token(TokenType.ELSE, "else");
  private final Token FOR = new Token(TokenType.FOR, "for");
  private final Token FUNC = new Token(TokenType.FUNC, "func");
  private final Token IF = new Token(TokenType.IF, "if");
  private final Token NULL = new Token(TokenType.NULL, "null");
  private final Token PRINT = new Token(TokenType.PRINT, "print");
  private final Token RETURN = new Token(TokenType.RETURN, "return");
  private final Token SUPER = new Token(TokenType.SUPER, "super");
  private final Token THIS = new Token(TokenType.THIS, "this");
  private final Token TRUE = new Token(TokenType.TRUE, "true");
  private final Token FALSE = new Token(TokenType.FALSE, "false");
  private final Token WHILE = new Token(TokenType.WHILE, "while");
  private final Token VAR = new Token(TokenType.VAR, "var");
  private final Token AND = new Token(TokenType.AND, "and");
  private final Token OR = new Token(TokenType.OR, "or");
  private final Token NUMBER = new Token(TokenType.NUMBER, "number");
  private final Token STRING = new Token(TokenType.STRING, "string");
  private final Token IDENTIFIER = new Token(TokenType.IDENTIFIER, "identificador");
  private final Token LEFT_PAREN = new Token(TokenType.LEFT_PAREN, "(");
  private final Token RIGHT_PAREN = new Token(TokenType.RIGHT_PAREN, ")");
  private final Token LEFT_BRACE = new Token(TokenType.LEFT_BRACE, "{");
  private final Token RIGHT_BRACE = new Token(TokenType.RIGHT_BRACE, "}");
  private final Token COMMA = new Token(TokenType.COMMA, ",");
  private final Token DOT = new Token(TokenType.DOT, ".");
  private final Token SEMICOLON = new Token(TokenType.SEMICOLON, ";");
  private final Token MINUS = new Token(TokenType.MINUS, "-");
  private final Token PLUS = new Token(TokenType.PLUS, "+");
  private final Token MULTIPLY = new Token(TokenType.MULTIPLY, "*");
  private final Token DIVIDE = new Token(TokenType.DIVIDE, "/");
  private final Token NOT = new Token(TokenType.NOT, "not");
  private final Token NOT_EQUAL = new Token(TokenType.NOT_EQUAL, "!=");
  private final Token EQUAL = new Token(TokenType.EQUAL, "==");
  private final Token ASSIGN = new Token(TokenType.ASSIGN, "=");
  private final Token LESS = new Token(TokenType.LESS, "less");
  private final Token LESS_EQUAL = new Token(TokenType.LESS_EQUAL, "less_equal");
  private final Token GREATER = new Token(TokenType.GREATER, "grater");
  private final Token GREATER_EQUAL = new Token(TokenType.GREATER_EQUAL, "greater_equal");

  private Token lookahead;
  private int i;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  public void parse() {
    i = 0;
    lookahead = tokens.get(i);

    program();

    if (!Main.errors && !lookahead.equals(EOF)) {
      Main.error(lookahead.getNumberLine(), "Unexpected token " + lookahead.getLexeme());
    }
  }

  private void program() {
    if (lookahead.equals(CLASS) ||
        lookahead.equals(FUNC) ||
        lookahead.equals(VAR) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(PLUS) ||
        lookahead.equals(FOR) ||
        lookahead.equals(IF) ||
        lookahead.equals(PRINT) ||
        lookahead.equals(RETURN) ||
        lookahead.equals(WHILE) ||
        lookahead.equals(LEFT_BRACE) ||
        lookahead.equals(NOT) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER))
      declaration();
    else {
      Main.error(lookahead.getNumberLine(), "Expected expression");
    }
  }

  private void declaration() {
    if (Main.errors)
      return;

    if (lookahead.equals(CLASS)) {
      classDecl();
      declaration();
    } else if (lookahead.equals(FUNC)) {
      funcDecl();
      declaration();
    } else if (lookahead.equals(VAR)) {
      varDecl();
      declaration();
    } else if (lookahead.equals(MINUS) ||
        lookahead.equals(PLUS) ||
        lookahead.equals(FOR) ||
        lookahead.equals(IF) ||
        lookahead.equals(PRINT) ||
        lookahead.equals(RETURN) ||
        lookahead.equals(WHILE) ||
        lookahead.equals(LEFT_BRACE) ||
        lookahead.equals(NOT) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      statement();
      declaration();
    }
  }

  private void classDecl() {
    if (Main.errors)
      return;

    if (lookahead.equals(CLASS)) {
      match(CLASS);
      match(IDENTIFIER);
      classInher();
      match(LEFT_BRACE);
      functions();
      match(RIGHT_BRACE);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword class");
    }
  }

  private void classInher() {
    if (Main.errors)
      return;

    if (lookahead.equals(LESS)) {
      match(LESS);
      match(IDENTIFIER);
    }
  }

  private void funcDecl() {
    if (Main.errors)
      return;

    if (lookahead.equals(FUNC)) {
      match(FUNC);
      function();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword fun");
    }
  }

  private void varDecl() {
    if (Main.errors)
      return;

    if (lookahead.equals(VAR)) {
      match(VAR);
      match(IDENTIFIER);
      varInit();
      match(SEMICOLON);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword var");
    }
  }

  private void varInit() {
    if (Main.errors)
      return;

    if (lookahead.equals(ASSIGN)) {
      match(ASSIGN);
      expression();
    }
  }

  private void statement() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      exprSTMT();
    } else if (lookahead.equals(FOR)) {
      forSTMT();
    } else if (lookahead.equals(IF)) {
      ifSTMT();
    } else if (lookahead.equals(PRINT)) {
      printSTMT();
    } else if (lookahead.equals(RETURN)) {
      returnSTMT();
    } else if (lookahead.equals(WHILE)) {
      whileSTMT();
    } else if (lookahead.equals(LEFT_BRACE)) {
      block();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected statement");
    }
  }

  private void exprSTMT() {
    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      expression();
      match(SEMICOLON);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected expression");
    }
  }

  private void forSTMT() {
    if (Main.errors)
      return;

    if (lookahead.equals(FOR)) {
      match(FOR);
      match(LEFT_PAREN);
      forSTMT1();
      forSTMT2();
      forSTMT3();
      match(RIGHT_PAREN);
      statement();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword for");
    }
  }

  private void forSTMT1() {
    if (Main.errors)
      return;

    if (lookahead.equals(VAR)) {
      varDecl();
    } else if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      exprSTMT();
    } else if (lookahead.equals(SEMICOLON)) {
      match(SEMICOLON);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected declaration or expression or \";\"");
    }
  }

  private void forSTMT2() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      expression();
      match(SEMICOLON);
    } else if (lookahead.equals(SEMICOLON)) {
      match(SEMICOLON);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected expression");
    }
  }

  private void forSTMT3() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      expression();
    }
  }

  private void ifSTMT() {
    if (Main.errors)
      return;

    if (lookahead.equals(IF)) {
      match(IF);
      match(LEFT_PAREN);
      expression();
      match(RIGHT_PAREN);
      statement();
      elseStatement();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword if");
    }
  }

  private void elseStatement() {
    if (Main.errors)
      return;

    if (lookahead.equals(ELSE)) {
      match(ELSE);
      statement();
    }
  }

  private void printSTMT() {
    if (Main.errors)
      return;

    if (lookahead.equals(PRINT)) {
      match(PRINT);
      expression();
      match(SEMICOLON);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword print");
    }
  }

  private void returnSTMT() {
    if (Main.errors)
      return;

    if (lookahead.equals(RETURN)) {
      match(RETURN);
      returnSTMTOpc();
      match(SEMICOLON);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword return");
    }
  }

  private void returnSTMTOpc() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      expression();
    }
  }

  private void whileSTMT() {
    if (Main.errors)
      return;

    if (lookahead.equals(WHILE)) {
      match(WHILE);
      match(LEFT_PAREN);
      expression();
      match(RIGHT_PAREN);
      statement();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected keyword while");
    }
  }

  private void block() {
    if (Main.errors)
      return;

    if (lookahead.equals(LEFT_BRACE)) {
      match(LEFT_BRACE);
      blockDecl();
      match(RIGHT_BRACE);
    } else {
      Main.error(lookahead.getNumberLine(), "Expected \"{\"");
    }
  }

  private void blockDecl() {
    if (Main.errors)
      return;

    if (lookahead.equals(CLASS) ||
        lookahead.equals(FUNC) ||
        lookahead.equals(VAR) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(PLUS) ||
        lookahead.equals(FOR) ||
        lookahead.equals(IF) ||
        lookahead.equals(PRINT) ||
        lookahead.equals(RETURN) ||
        lookahead.equals(WHILE) ||
        lookahead.equals(LEFT_BRACE) ||
        lookahead.equals(NOT) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      declaration();
      blockDecl();
    }
  }

  private void expression() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      assignment();
    }
  }

  private void assignment() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      logicOr();
      assignmentOpc();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void assignmentOpc() {
    if (Main.errors)
      return;

    if (lookahead.equals(ASSIGN)) {
      match(ASSIGN);
      expression();
    }
  }

  private void logicOr() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      logicAnd();
      logicOr2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void logicOr2() {
    if (Main.errors)
      return;

    if (lookahead.equals(OR)) {
      match(OR);
      logicAnd();
      logicOr2();
    }
  }

  private void logicAnd() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      equality();
      logicAnd2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void logicAnd2() {
    if (Main.errors)
      return;

    if (lookahead.equals(AND)) {
      match(AND);
      equality();
      logicAnd2();
    }
  }

  private void equality() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      comparison();
      equality2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void equality2() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT_EQUAL)) {
      match(NOT_EQUAL);
      comparison();
      equality2();
    } else if (lookahead.equals(EQUAL)) {
      match(EQUAL);
      comparison();
      equality2();
    }
  }

  private void comparison() {
    if (Main.errors)
      return;
    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      term();
      comparison2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void comparison2() {
    if (Main.errors)
      return;

    if (lookahead.equals(GREATER)) {
      match(GREATER);
      term();
      comparison2();
    } else if (lookahead.equals(GREATER_EQUAL)) {
      match(GREATER_EQUAL);
      term();
      comparison2();
    } else if (lookahead.equals(LESS)) {
      match(LESS);
      term();
      comparison2();
    } else if (lookahead.equals(LESS_EQUAL)) {
      match(LESS_EQUAL);
      term();
      comparison2();
    }
  }

  private void term() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      factor();
      term2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void term2() {
    if (Main.errors)
      return;

    if (lookahead.equals(MINUS)) {
      match(MINUS);
      factor();
      term2();
    } else if (lookahead.equals(PLUS)) {
      match(PLUS);
      factor();
      term2();
    }
  }

  private void factor() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      unary();
      factor2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void factor2() {
    if (Main.errors)
      return;

    if (lookahead.equals(DIVIDE)) {
      match(DIVIDE);
      unary();
      factor2();
    } else if (lookahead.equals(MULTIPLY)) {
      match(MULTIPLY);
      unary();
      factor2();
    }
  }

  private void unary() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT)) {
      match(NOT);
      unary();
    } else if (lookahead.equals(MINUS)) {
      match(MINUS);
      unary();
    } else if (lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      call();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void call() {
    if (Main.errors)
      return;

    if (lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      primary();
      call2();
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void call2() {
    if (Main.errors)
      return;

    if (lookahead.equals(LEFT_PAREN)) {
      match(LEFT_PAREN);
      argumentsOpc();
      match(RIGHT_PAREN);
      call2();
    } else if (lookahead.equals(DOT)) {
      match(DOT);
      match(IDENTIFIER);
      call2();
    }
  }

  private void primary() {
    if (Main.errors)
      return;

    if (lookahead.equals(TRUE))
      match(TRUE);
    else if (lookahead.equals(FALSE))
      match(FALSE);
    else if (lookahead.equals(NULL))
      match(NULL);
    else if (lookahead.equals(THIS))
      match(THIS);
    else if (lookahead.equals(NUMBER))
      match(NUMBER);
    else if (lookahead.equals(STRING))
      match(STRING);
    else if (lookahead.equals(IDENTIFIER))
      match(IDENTIFIER);
    else if (lookahead.equals(LEFT_PAREN)) {
      match(LEFT_PAREN);
      expression();
      match(RIGHT_PAREN);
    } else if (lookahead.equals(SUPER)) {
      match(SUPER);
      match(DOT);
      match(IDENTIFIER);
    } else {
      Main.error(lookahead.getNumberLine(), "Unexpected " + lookahead.getLexeme());
    }
  }

  private void function() {
    if (Main.errors)
      return;

    if (lookahead.equals(IDENTIFIER)) {
      match(IDENTIFIER);
      match(LEFT_PAREN);
      parametersOpc();
      match(RIGHT_PAREN);
      block();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected identifier");
    }
  }

  private void functions() {
    if (Main.errors)
      return;

    if (lookahead.equals(FUNC)) {
      funcDecl();
      functions();
    }
  }

  private void parametersOpc() {
    if (Main.errors)
      return;

    if (lookahead.equals(IDENTIFIER)) {
      parameters();
    }
  }

  private void parameters() {
    if (Main.errors)
      return;

    if (lookahead.equals(IDENTIFIER)) {
      match(IDENTIFIER);
      parameters2();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected identifier");
    }
  }

  private void parameters2() {
    if (Main.errors)
      return;

    if (lookahead.equals(COMMA)) {
      match(COMMA);
      match(IDENTIFIER);
      parameters2();
    }
  }

  private void argumentsOpc() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      arguments();
    }
  }

  private void arguments() {
    if (Main.errors)
      return;

    if (lookahead.equals(NOT) ||
        lookahead.equals(MINUS) ||
        lookahead.equals(TRUE) ||
        lookahead.equals(FALSE) ||
        lookahead.equals(NULL) ||
        lookahead.equals(THIS) ||
        lookahead.equals(NUMBER) ||
        lookahead.equals(STRING) ||
        lookahead.equals(IDENTIFIER) ||
        lookahead.equals(LEFT_PAREN) ||
        lookahead.equals(SUPER)) {
      expression();
      arguments2();
    } else {
      Main.error(lookahead.getNumberLine(), "Expected arguments");
    }
  }

  private void arguments2() {
    if (Main.errors)
      return;

    if (lookahead.equals(COMMA)) {
      match(COMMA);
      expression();
      arguments2();
    }
  }

  private void match(Token terminal) {
    if (Main.errors)
      return;
    if (lookahead.equals(terminal)) {
      i++;
      lookahead = tokens.get(i);
    } else {
      Main.errors = true;
      Main.error(lookahead.getNumberLine(), "Expected " + terminal.getLexeme());
    }
  }
}
