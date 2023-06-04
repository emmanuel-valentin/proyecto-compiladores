import java.text.Normalizer.Form;
import java.util.List;

public class Parser {

  private final List<Token> tokens;
  private Token EOF = new Token(TokenType.EOF, "");
  private Token CLASS = new Token(TokenType.CLASS, "class");
  private Token ELSE = new Token(TokenType.ELSE, "else");
  private Token FOR = new Token(TokenType.FOR, "for");
  private Token FUNC = new Token(TokenType.FUNC, "func");
  private Token IF = new Token(TokenType.IF, "if");
  private Token NULL = new Token(TokenType.NULL, "null");
  private Token PRINT = new Token(TokenType.PRINT, "print");
  private Token RETURN = new Token(TokenType.RETURN, "return");
  private Token SUPER = new Token(TokenType.SUPER, "super");
  private Token THIS = new Token(TokenType.THIS, "this");
  private Token TRUE = new Token(TokenType.TRUE, "true");
  private Token FALSE = new Token(TokenType.FALSE, "false");
  private Token WHILE = new Token(TokenType.WHILE, "while");
  private Token VAR = new Token(TokenType.VAR, "var");
  private Token AND = new Token(TokenType.AND, "and");
  private Token OR = new Token(TokenType.OR, "or");
  private Token NUMBER = new Token(TokenType.NUMBER, "number");
  private Token STRING = new Token(TokenType.STRING, "string");
  private Token IDENTIFIER = new Token(TokenType.IDENTIFIER, "id");
  private Token LEFT_PAREN = new Token(TokenType.LEFT_PAREN, "(");
  private Token RIGHT_PAREN = new Token(TokenType.RIGHT_PAREN, ")");
  private Token LEFT_BRACE = new Token(TokenType.LEFT_BRACE, "{");
  private Token RIGHT_BRACE = new Token(TokenType.RIGHT_BRACE, "}");
  private Token RIGHT_CORCH = new Token(TokenType.RIGHT_CORCH, "[");
  private Token LEFT_CORCH = new Token(TokenType.LEFT_CORCH, "]");
  private Token COMMA = new Token(TokenType.COMMA, "comma");
  private Token DOT = new Token(TokenType.DOT, "dot");
  private Token SEMICOLON = new Token(TokenType.SEMICOLON, "semicolon");
  private Token MINUS = new Token(TokenType.MINUS, "minus");
  private Token PERCENT = new Token(TokenType.PERCENT, "percent");
  private Token MINUS_EQUAL = new Token(TokenType.MINUS_EQUAL, "-=");
  private Token PLUS = new Token(TokenType.PLUS, "+");
  private Token PLUS_EQUAL = new Token(TokenType.PLUS_EQUAL, "+=");
  private Token MULTIPLY = new Token(TokenType.MULTIPLY, "*");
  private Token MULTIPLY_EQUAL = new Token(TokenType.MULTIPLY_EQUAL, "=");
  private Token DIVIDE = new Token(TokenType.DIVIDE, "/");
  private Token DIVIDE_EQUAL = new Token(TokenType.DIVIDE_EQUAL, "/=");
  private Token MOD = new Token(TokenType.MOD, "%");
  private Token MOD_EQUAL = new Token(TokenType.MOD_EQUAL, "%=");
  private Token NOT = new Token(TokenType.NOT, "not");
  private Token EQUAL = new Token(TokenType.EQUAL, "equal");
  private Token NOT_EQUAL = new Token(TokenType.NOT_EQUAL, "nor equal");
  private Token ASSIGN = new Token(TokenType.ASSIGN, "assign");
  private Token LESS = new Token(TokenType.LESS, "less");
  private Token LESS_EQUAL = new Token(TokenType.LESS_EQUAL, "less_equal");
  private Token GREATER = new Token(TokenType.GREATER, "grater");
  private Token GREATER_EQUAL = new Token(TokenType.GREATER_EQUAL, "greater_equal");

  private Token lookahead;
  private int i;

  // Servirá para ir obteniendo cada token de la lista de tokens generada en el análisis léxico
  

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  public void parse() {
    i = 0;
    lookahead = tokens.get(i);

    program();

    // Ejecutar la primera producción: Por cada no terminal crear una función (Ver código SQL)
    // Por cada terminal ejecutar la función match
    if (!Main.errors && !lookahead.equals(EOF)) {
      Main.error(lookahead.getNumberLine(), String.format(
          "Error en la posición %s. No se esperaba el token %s",
          lookahead.getNumberLine(),
          lookahead.getType()
      ));
    }
    else if (!Main.errors && lookahead.equals(EOF)) {
      System.out.println("Consulta válida");
    }
  }


  private void program() {
    declaration();
  }

  private void declaration() {}

  private void classDecl() {}

  private void classInher() {}

  private void funcDec() {}

  private void varDecl() {}

  private void varInit() {}

  private void statement() {}

  private void exprSTMT() {}

  private void forSTMT() {}

  private void forSTMT1() {}

  private void forSTMT2() {}

  private void forSTMT3() {}

  private void ifSTMT() {}

  private void elseStatement() {}

  private void printSTMT() {}

  private void returnSTMT() {}

  private void returnSTMTOpc() {}

  private void whileSTMT() {}

  private void block() {}

  private void blockDecl() {}

  private void expression() {}

  private void assigment() {}

  private void assigmentOpc() {}

  private void logicOr() {}

  private void logicOr2() {}

  private void logicAnd() {}

  private void logicAnd2() {}

  private void equality() {}

  private void equality2() {}

  private void comparison() {}

  private void comparison2() {}

  private void term() {}

  private void term2() {}

  private void factor() {}

  private void factor2() {}

  private void unary() {}

  private void call() {}

  private void call2() {}

  private void callOpc() {}

  private void primary() {}

  private void function() {}

  private void functions() {}

  private void parametersOpc() {}

  private void parameters() {}

  private void parameters2() {}

  private void argumentsOpc() {}

  private void arguments() {}

  private void arguments2() {}

  private void match(Token terminal) {
    if(Main.errors) return;
    if (lookahead == terminal) {
      i++;
      lookahead = tokens.get(i);
    }
    else {
      Main.errors = true;
      Main.error(lookahead.getNumberLine(), "Error en la posición " +  i + ", se esperaba un " + terminal);
    }
  }
}






