public class Token {
  final TokenType type;
  final String lexeme;
  final Object literal;
  final int numberLine;

  Token(TokenType type, String lexeme, Object literal, int numberLine) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.numberLine = numberLine;
  }

  @Override
  public String toString() {
    return type + " " + lexeme + " " + literal;
  }
}
