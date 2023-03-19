import java.util.*;

public class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();

  private int numberLine = 1;

  private static final Map<String, TokenType> keywords;
  private static final Map<String, TokenType> tokensWithLexeme;

  static {
    // Set language keywords
    keywords = new HashMap<>();
    keywords.put("else", TokenType.ELSE);
    keywords.put("class", TokenType.CLASS);
    keywords.put("for", TokenType.FOR);
    keywords.put("func", TokenType.FUNC);
    keywords.put("if", TokenType.IF);
    keywords.put("null", TokenType.NULL);
    keywords.put("print", TokenType.PRINT);
    keywords.put("return", TokenType.RETURN);
    keywords.put("super", TokenType.SUPER);
    keywords.put("this", TokenType.THIS);
    keywords.put("true", TokenType.TRUE);
    keywords.put("false", TokenType.FALSE);
    keywords.put("while", TokenType.WHILE);
    keywords.put("var", TokenType.VAR);

    tokensWithLexeme = new HashMap<>();
    tokensWithLexeme.put("<", TokenType.LESS);
    tokensWithLexeme.put("<=", TokenType.LESS_EQUAL);
    tokensWithLexeme.put(">", TokenType.GREATER);
    tokensWithLexeme.put(">=", TokenType.GREATER_EQUAL);
    tokensWithLexeme.put("==", TokenType.EQUAL);
    tokensWithLexeme.put("!=", TokenType.NOT_EQUAL);
    tokensWithLexeme.put("!", TokenType.NOT);
    tokensWithLexeme.put("=", TokenType.ASSIGN);
  }

  Scanner(String source) {
    this.source = source;
  }

  public List<Token> scanTokens() {
    int state = 0;
    List<Integer> finalStates = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    StringBuffer buffer = new StringBuffer();

    for (char character : this.source.toCharArray()) {
      if (character == '\n') this.numberLine++;

      switch (state) {
        case 0 -> {
          if (character == '<') state = 1;
          else if (character == '!') state = 3;
          else if (character == '>') state = 5;
          else if (character == '=') state = 7;
        }
        case 1 -> {
          if (character == '=') state = 2;
        }
        case 3 -> {
          if (character == '=') state = 4;

        }
        case 5 -> {
          if (character == '=') state = 6;
        }
        case 7 -> {
          if (character == '=') state = 7;
        }
      }

      if (finalStates.contains(state)) {
        buffer.append(character);
        tokens.add(new Token(
            tokensWithLexeme.get(buffer.toString()),
            buffer.toString(),
            null,
            numberLine
        ));

        state = 0;
        buffer.delete(0, buffer.length());
      }
    }
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }
}
