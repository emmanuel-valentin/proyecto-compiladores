public class Token {
  final private TokenType type;
  final private String lexeme;
  final private Object literal;
  final private int numberLine;

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
}
