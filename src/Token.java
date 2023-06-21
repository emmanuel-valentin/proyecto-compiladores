public class Token {
  final TokenType type;
  final private String lexeme;
  final Object literal;
  final private int numberLine;
  public TokenType t;

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

   // Métodos auxiliares
   public boolean isOperand(){
    switch (this.type){
        case IDENTIFIER:
        case NUMBER:
            return true;
        default:
            return false;
    }
}

public boolean isOperator(){
    switch (this.type){
        case PLUS:
        case MINUS:
        case MULTIPLY:
        case DIVIDE:
        case EQUAL:
        case GREATER:
        case GREATER_EQUAL:
            return true;
        default:
            return false;
    }
}

public boolean isKeyword(){
    switch (this.type){
        case VAR:
        case IF:
        case PRINT:
        case ELSE:
            return true;
        default:
            return false;
    }
}

public boolean isControlStructure(){
    switch (this.type){
        case IF:
        case ELSE:
            return true;
        default:
            return false;
    }
}

public boolean greater_equalPrecedence(Token t){
    return this.getPrecedence() >= t.getPrecedence();
}


private int getPrecedence(){
    switch (this.type){
        case MULTIPLY:
        case DIVIDE:
            return 3;
        case PLUS:
        case MINUS:
            return 2;
        case EQUAL:
            return 1;
        case GREATER:
        case GREATER_EQUAL:
            return 1;
      default:
        break;
    }

    return 0;
}

public int aridad(){
    switch (this.type) {
        case MULTIPLY:
        case DIVIDE:
        case PLUS:
        case MINUS:
        case EQUAL:
        case GREATER:
        case GREATER_EQUAL:
            return 2;
      default:
        break;
    }
    return 0;
}
}


