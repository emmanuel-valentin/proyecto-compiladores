import java.util.*;
import java.util.regex.Pattern;

public class Scanner {
  private final String source;

  private final List<Token> tokens;

  public static int numberLine;

  private static final Map<String, TokenType> keywords;

  private final StringBuilder lexeme;

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
  }

  Scanner(String source) {
    this.source = source;
    this.tokens = new ArrayList<>();
    numberLine = 1;
    this.lexeme = new StringBuilder();
  }

  public List<Token> scanTokens() {
    int state = 0;

    for (int i = 0; i <= source.length(); i++) {
      char currentCharacter = getCurrentCharacter(i, source.length());
      numberLine = incrementNumberLine(currentCharacter);

      switch (state) {
        case 0:
          if (currentCharacter == '<') {
            state = 1;
            lexeme.append(currentCharacter);
          } 
          else if (currentCharacter == '=') {
            state = 2;
            lexeme.append(currentCharacter);
          } 
          else if (currentCharacter == '>') {
            state = 3;
            lexeme.append(currentCharacter);
          } 
          else if (currentCharacter == '!') {
            state = 4;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == '(') {
            state = 5;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == ')') {
            state = 6;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == '[') {
            state = 7;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == ']') {
            state = 8;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == '{') {
            state = 9;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == '}') {
            state = 10;
            lexeme.append(currentCharacter);
          }
          else if (currentCharacter == '\0');
          else {
            throw new RuntimeException("Unable to parse: " + currentCharacter);
          }
          break;
        // Operadores relacionales
        // -------------------------------------------------------------------------------------
        case 1:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.LESS_EQUAL, lexeme.toString());
          } 
          else {
            i--;
            addToken(TokenType.LESS, lexeme.toString());
          }
          break;
        case 2:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.EQUAL, lexeme.toString());
          } 
          else {
            i--;
            addToken(TokenType.ASSIGN, lexeme.toString());
          }
          break;
        case 3:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.GREATER_EQUAL, lexeme.toString());
          } 
          else {
            i--;
            addToken(TokenType.GREATER, lexeme.toString());
          }
          break;
        case 4:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.NOT_EQUAL, lexeme.toString());
          } 
          else {
            i--;
            addToken(TokenType.NOT, lexeme.toString());
          }
          break;
        // -------------------------------------------------------------------------------------------------------------
        // Paréntesis y Corchetes y llaves
        case 5:
          i--; state = 0; addToken(TokenType.LEFT_PAREN, lexeme.toString());
          break;
        case 6:
          i--; state = 0; addToken(TokenType.RIGHT_PAREN, lexeme.toString());
          break;
        case 7:
          i--; state = 0; addToken(TokenType.LEFT_CORCH, lexeme.toString());
          break;
        case 8:
          i--; state = 0; addToken(TokenType.RIGHT_CORCH, lexeme.toString());
          break;
        case 9:
          i--; state = 0; addToken(TokenType.LEFT_BRACE, lexeme.toString());
          break;
        case 10:
          i--; state = 0; addToken(TokenType.RIGHT_BRACE, lexeme.toString());
          break;
        // Strings
        case 11:
          if (currentCharacter != '"') {
            lexeme.append(currentCharacter);
          }
          else {
            state = 0;
            lexeme.append(currentCharacter);
            addToken(
                TokenType.STRING,
                lexeme.toString(),
                lexeme.substring(1, lexeme.length() - 1)
            );
          }
          break;
        default:
          throw new RuntimeException("Unable to parse: " + currentCharacter);
      }
    }
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }

  private void addToken(TokenType type, String lexeme) {
    if (type == TokenType.IDENTIFIER) {
      type = keywords.getOrDefault(lexeme, TokenType.IDENTIFIER);
    }
    tokens.add(new Token(type, lexeme, null, numberLine));
    this.lexeme.delete(0, this.lexeme.length());
  }

  private void addToken(TokenType type, String lexeme, Object literal) {
    tokens.add(new Token(type, lexeme, literal, numberLine));
    this.lexeme.delete(0, this.lexeme.length());
  }

  private int incrementNumberLine(char current) {
    if (current == '\n')
      numberLine++;
    return numberLine;
  }

  private char getCurrentCharacter(int index, int sourceLength) {
    if (index >= sourceLength) {
      return '\0';
    }
    return source.charAt(index);
  }
}
