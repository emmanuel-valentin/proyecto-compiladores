public class Token {
  private final TokenType type;
  private final String lexeme;
  private final Object literal;
  private final int numberLine;

  public Token(TokenType type, String lexeme, Object literal, int numberLine) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.numberLine = numberLine;
  }

  public Token(TokenType type, String lexeme) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = null;
    this.numberLine = 0;
  }

  public int getNumberLine() {
    return this.numberLine;
  }

  public TokenType getType() {
    return type;
  }

  public String getLexeme() {
    return lexeme;
  }

  public Object getLiteral() {
    return literal;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Token)) {
      return false;
    }

    return this.type == ((Token) obj).type;
  }

  @Override
  public String toString() {
    return "Line[" + numberLine + "]: " + type + " " + lexeme + " " + literal;
  }

  public boolean isOperand() {
    switch (this.type) {
      case IDENTIFIER:
      case NUMBER:
      case STRING:
      case TRUE:
      case FALSE:
        return true;
      default:
        return false;
    }
  }

  public boolean isOperator() {
    switch (this.type) {
      case PLUS:
      case MINUS:
      case MULTIPLY:
      case DIVIDE:
      case EQUAL:
      case NOT_EQUAL:
      case GREATER:
      case GREATER_EQUAL:
      case LESS:
      case LESS_EQUAL:
      case AND:
      case OR:
      case ASSIGN:
        return true;
      default:
        return false;
    }
  }

  public boolean isKeyword() {
    switch (this.type) {
      case VAR:
      case IF:
      case PRINT:
      case ELSE:
      case WHILE:
      case FOR:
        return true;
      default:
        return false;
    }
  }

  public boolean isControlStructure() {
    switch (this.type) {
      case FOR:
      case WHILE:
      case IF:
      case ELSE:
        return true;
      default:
        return false;
    }
  }

  public boolean greaterEqualPrecedence(Token t) {
    return this.getPrecedence() >= t.getPrecedence();
  }


  private int getPrecedence() {
    switch (this.type) {
      case MULTIPLY:
      case DIVIDE:
        return 7;
      case PLUS:
      case MINUS:
        return 6;
      case LESS:
      case LESS_EQUAL:
      case GREATER:
      case GREATER_EQUAL:
        return 5;
      case EQUAL:
      case NOT_EQUAL:
        return 4;
      case AND:
      case OR:
        return 3;
      case ASSIGN:
        return 1;
    }

    return 0;
  }

  public int aridity() {
    switch (this.type) {
      case MULTIPLY:
      case DIVIDE:
      case PLUS:
      case MINUS:
      case EQUAL:
      case ASSIGN:
      case NOT_EQUAL:
      case GREATER:
      case GREATER_EQUAL:
      case LESS:
      case LESS_EQUAL:
      case AND:
      case OR:
        return 2;
      default:
        break;
    }
    return 0;
  }
}


