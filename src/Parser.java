import java.text.Normalizer.Form;
import java.util.List;

public class Parser {

  private final List<Token> tokens;
  private Token EOF = new Token(TokenType.EOF, "");
  private Token CLASS = new Token(TokenType.CLASS, "class");
  private Token ELSE = new Token(TokenType.ELSE, "else");
  private Token FOR = new Token(TokenType.FOR, "for");
  private Token FUNC = new Token(TokenType.FUNC, "Funtion");
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
  private Token LEFT_PAREN = new Token(TokenType.LEFT_PAREN, "left_paren");
  private Token RIGHT_PAREN = new Token(TokenType.RIGHT_PAREN, "right_paren");
  private Token LEFT_BRACE = new Token(TokenType.LEFT_BRACE, "left_brace");
  private Token RIGHT_BRACE = new Token(TokenType.RIGHT_BRACE, "right_brace");
  private Token RIGHT_CORCH = new Token(TokenType.RIGHT_CORCH, "eight_corch");
  private Token LEFT_CORCH = new Token(TokenType.LEFT_CORCH, "left_corch");
  private Token COMMA = new Token(TokenType.COMMA, "comma");
  private Token DOT = new Token(TokenType.DOT, "dot");
  private Token SEMICOLON = new Token(TokenType.SEMICOLON, "semicolon");
  private Token MINUS = new Token(TokenType.MINUS, "minus");
  private Token PERCENT = new Token(TokenType.PERCENT, "percent");
  private Token MINUS_EQUAL = new Token(TokenType.MINUS_EQUAL, "minus_equal");
  private Token PLUS = new Token(TokenType.PLUS, "plus");
  private Token PLUS_EQUAL = new Token(TokenType.PLUS_EQUAL, "plus_equal");
  private Token MULTIPLY = new Token(TokenType.MULTIPLY, "multiply");
  private Token MULTIPLY_EQUAL = new Token(TokenType.MULTIPLY_EQUAL, "multiply_equal");
  private Token DIVIDE = new Token(TokenType.DIVIDE, "divide");
  private Token DIVIDE_EQUAL = new Token(TokenType.DIVIDE_EQUAL, "divide_equal");
  private Token MOD = new Token(TokenType.MOD, "mod");
  private Token MOD_EQUAL = new Token(TokenType.MOD_EQUAL, "mod_equal");
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
    startProduction();
    // Ejecutar la primera producción: Por cada no terminal crear una función (Ver código SQL)
    // Por cada terminal ejecutar la función match
  }

  private void match(Token  terminal) {
    if(Main.errors) return;
    if (lookahead == terminal) {
      i++;
      lookahead =tokens.get(i);
    }
    else {
      Main.errors = true;
      Main.error(lookahead.getNumberLine(), "Error en la posición " +  i + ", se esperaba un " + identifier2.type);
    }
  }

private void startProduction(){
  //empezar la gramatica de automata start->Q'
  Q();
}

/*private void Q() {
  if(lookahead.getType() == keywords.SELECT){
    //Q->SELECT D from T
    match(keywords.SELECT);
    D();
    match(keywords.FROM);
    T();
  }
  else{
    //errores
  }
  boolean error = true;
  System.out.println("Error en la posición " + lookahead.literal + ". Se esperaba la palabra reservada SELECT.");
  }
   */

private void D() {
  if (lookahead.getType() == TokenType.DISTINCT) {
    // D -> distinct P
    match(TokenType.DISTINCT);
    P();
  } else if (lookahead.getType() == TokenType.IDENTIFIER) {
    // D -> P
    P();
  } else {
    //errores
  }
}

private void P() {
  if (lookahead.getType() == TokenType.ASTERISK) {
    // P -> *
    match(TokenType.ASTERISK);
  } else if (lookahead.getType() == TokenType.IDENTIFIER) {
    // P -> A
    A();
  } else {
    // error sintáctico
  }
}


private void match(TokenType asterisk) {
}

private void A() {
  if (lookahead.getType() == TokenType.IDENTIFIER) {
    // A -> A2A1
    A2();
    A1();
  } else {
    //error sintáctico
  }
}

private void A2() {
  if (lookahead.getType() == TokenType.IDENTIFIER) {
    // A2 -> idA3
    match(TokenType.IDENTIFIER);
    A3();
  } else {
    //  error sintáctico
  }
}

private void A3() {
  if (lookahead.getType() == TokenType.DOT) {
    // A3 -> .id
    match(TokenType.DOT);
    match(TokenType.IDENTIFIER);
  } else {
    // error sintáctico
  }
}

private void T() {
  if (lookahead.getType() == TokenType.IDENTIFIER) {
    // T -> T2T1
    T2();
    T1();
  } else {
    //  error sintáctico
  }
}

private void T2() {
  if (lookahead.getType() == TokenType.IDENTIFIER) {
    // T2 -> idT3
    match(TokenType.IDENTIFIER);
    T3();
  } else {
    //  error sintáctico
  }
}

private void T3() {
  if (lookahead.getType() == TokenType.IDENTIFIER) {
    // T3 -> id
    match(TokenType.IDENTIFIER);
  } else {
    // error sintáctico
  }
}
private void A1() {
  if (lookahead.getType() == TokenType.COMMA) {
    // A1 -> , A
    match(TokenType.COMMA);
    A();
  } else {
    // error sintáctico
  }
}

private void T1() {
  if (lookahead.getType() == TokenType.COMMA) {
    // T1 -> , T
    match(TokenType.COMMA);
    T();
  } else {
    // T1 -> ε 
  }
}
}






