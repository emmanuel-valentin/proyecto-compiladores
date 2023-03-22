import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    tokensWithLexeme.put("<>", TokenType.NOT_EQUAL);
    tokensWithLexeme.put("!", TokenType.NOT);
    tokensWithLexeme.put("=", TokenType.ASSIGN);
  }

  Scanner(String source) {
    this.source = source.trim();
  }

  private boolean isExpression(char character, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(String.valueOf(character));

    return matcher.matches();
  }

  public List<Token> scanTokens() {
    int state = 0;
    List<Integer> finalStates = List.of(2, 3, 4, 5, 7, 8);
    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < this.source.length(); i++) {
      char currentCharacter = this.source.charAt(i);
      switch (state) {
        case 0 -> {
          if (isExpression(currentCharacter, "<")) {
            state = 1;
          }
          else if (isExpression(currentCharacter, "=")) {
            state = 5;
          }
          else if (isExpression(currentCharacter, ">")) {
            state = 6;
          }
        }
        case 1 -> {
          if (isExpression(currentCharacter, "=")) {
            state = 2;
          }
          else if (isExpression(currentCharacter, ">")) {
            state = 3;
          }
          else {
            state = 4;
            currentCharacter = this.source.charAt(--i);
          }
        }
        case 6 -> {
          if (isExpression(currentCharacter, "=")) {
            state = 7;
          }
          else {
            state = 8;
            currentCharacter = this.source.charAt(--i);
          }
        }
        default -> state = 0;
      }

      buffer.append(currentCharacter);
      if (finalStates.contains(state)) {
        String lexeme = buffer.toString();
        TokenType type = tokensWithLexeme.get(lexeme);
        tokens.add(new Token(type, lexeme, null, numberLine));
        buffer.delete(0, buffer.length());
        state = 0;
      }
    }
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }
}
