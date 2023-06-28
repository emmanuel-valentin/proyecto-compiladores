import java.util.*;

public class Scanner {
  private final String source;

  private final List<Token> tokens;

  public static int numberLine;

  private static final Map<String, TokenType> keywords;

  private final StringBuilder lexeme;

  static {
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
    keywords.put("from", TokenType.FROM);
    keywords.put("select", TokenType.SELECT);
    keywords.put("or", TokenType.OR);
    keywords.put("and", TokenType.AND);
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
          if (currentCharacter != '\0') {
            if (currentCharacter == '<') {
              state = 1;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '=') {
              state = 2;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '>') {
              state = 3;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '!') {
              state = 4;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '(') {
              state = 5;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == ')') {
              state = 6;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '[') {
              state = 7;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == ']') {
              state = 8;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '{') {
              state = 9;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '}') {
              state = 10;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '"') {
              state = 11;
              lexeme.append(currentCharacter);
            } else if (Character.isDigit(currentCharacter)) {
              state = 12;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '+') {
              state = 18;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '-') {
              state = 19;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '*') {
              state = 20;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '/') {
              state = 21;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '%') {
              state = 22;
              lexeme.append(currentCharacter);
            } else if (Character.isLetter(currentCharacter)) {
              state = 25;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == '_') {
              state = 26;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == ' ' ||
                currentCharacter == '\t' ||
                currentCharacter == '\n' ||
                currentCharacter == '\r') {
              state = 27;
              lexeme.append(currentCharacter);
            } else if (currentCharacter == ';') {
              lexeme.append(currentCharacter);
              addToken(TokenType.SEMICOLON, lexeme.toString());
            } else if (currentCharacter == ',') {
              lexeme.append(currentCharacter);
              addToken(TokenType.COMMA, lexeme.toString());
            } else if (currentCharacter == '.') {
              lexeme.append(currentCharacter);
              addToken(TokenType.DOT, lexeme.toString());
            } else {
              Main.error(numberLine, "Unable to parse: " + currentCharacter);
            }
          }
          break;
        // Operadores relacionales
        // -------------------------------------------------------------------------------------
        case 1:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.LESS_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.LESS, lexeme.toString());
          }
          break;
        case 2:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.ASSIGN, lexeme.toString());
          }
          break;
        case 3:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.GREATER_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.GREATER, lexeme.toString());
          }
          break;
        case 4:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.NOT_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.NOT, lexeme.toString());
          }
          break;
        // -------------------------------------------------------------------------------------------------------------
        // Paréntesis y Corchetes y llaves
        case 5:
          i--;
          state = 0;
          addToken(TokenType.LEFT_PAREN, lexeme.toString());
          break;
        case 6:
          i--;
          state = 0;
          addToken(TokenType.RIGHT_PAREN, lexeme.toString());
          break;
        case 7:
          i--;
          state = 0;
          addToken(TokenType.LEFT_CORCH, lexeme.toString());
          break;
        case 8:
          i--;
          state = 0;
          addToken(TokenType.RIGHT_CORCH, lexeme.toString());
          break;
        case 9:
          i--;
          state = 0;
          addToken(TokenType.LEFT_BRACE, lexeme.toString());
          break;
        case 10:
          i--;
          state = 0;
          addToken(TokenType.RIGHT_BRACE, lexeme.toString());
          break;
        // Strings
        case 11:
          if (currentCharacter != '"' && currentCharacter != '\0') {
            lexeme.append(currentCharacter);
          } else if (currentCharacter == '\0') {
            Main.error(numberLine, "Unable to parse: " + lexeme);
          } else {
            state = 0;
            lexeme.append(currentCharacter);
            addToken(
                TokenType.STRING,
                lexeme.toString(),
                lexeme.substring(1, lexeme.length() - 1));
          }
          break;
        // Numbers
        case 12:
          if (currentCharacter >= '0' && currentCharacter <= '9') {
            lexeme.append(currentCharacter);
          } else if (currentCharacter == '.') {
            state = 13;
            lexeme.append(currentCharacter);
          } else if (currentCharacter == 'e' || currentCharacter == 'E') {
            state = 15;
            lexeme.append(currentCharacter);
          } else {
            i--;
            state = 0;
            addToken(TokenType.NUMBER, lexeme.toString(), Double.parseDouble(lexeme.toString()));
          }
          break;
        case 13:
          if (currentCharacter >= '0' && currentCharacter <= '9') {
            state = 14;
            lexeme.append(currentCharacter);
          } else {
            Main.error(numberLine, "Unable to parse: " + lexeme);
          }
          break;
        case 14:
          if (currentCharacter >= '0' && currentCharacter <= '9') {
            lexeme.append(currentCharacter);
          } else if (currentCharacter == 'e' || currentCharacter == 'E') {
            state = 15;
            lexeme.append(currentCharacter);
          } else {
            i--;
            state = 0;
            addToken(TokenType.NUMBER, lexeme.toString(), Double.parseDouble(lexeme.toString()));
          }
          break;
        case 15:
          if (currentCharacter >= '0' && currentCharacter <= '9') {
            state = 17;
            lexeme.append(currentCharacter);
          } else if (currentCharacter == '+' || currentCharacter == '-') {
            state = 16;
            lexeme.append(currentCharacter);
          } else {
            Main.error(numberLine, "Unable to parse: " + lexeme);
          }
          break;
        case 16:
          if (currentCharacter >= '0' && currentCharacter <= '9') {
            state = 17;
            lexeme.append(currentCharacter);
          } else {
            throw new RuntimeException("Unable to parse: " + lexeme);
          }
          break;
        case 17:
          if (currentCharacter >= '0' && currentCharacter <= '9') {
            lexeme.append(currentCharacter);
          } else {
            i--;
            state = 0;
            addToken(TokenType.NUMBER, lexeme.toString(), Double.parseDouble(lexeme.toString()));
          }
          break;
        // Arithmetic operators
        case 18:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.PLUS_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.PLUS, lexeme.toString());
          }
          break;
        case 19:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.MINUS_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.MINUS, lexeme.toString());
          }
          break;
        case 20:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.MULTIPLY_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.MULTIPLY, lexeme.toString());
          }
          break;
        case 21:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.DIVIDE_EQUAL, lexeme.toString());
          } else if (currentCharacter == '/') {
            state = 28;
            lexeme.append(currentCharacter);
          } else if (currentCharacter == '*') {
            state = 29;
            lexeme.append(currentCharacter);
          } else {
            i--;
            addToken(TokenType.DIVIDE, lexeme.toString());
          }
          break;
        case 22:
          state = 0;
          if (currentCharacter == '=') {
            lexeme.append(currentCharacter);
            addToken(TokenType.MOD_EQUAL, lexeme.toString());
          } else {
            i--;
            addToken(TokenType.MOD, lexeme.toString());
          }
          break;
        // Identifiers
        case 25:
          if (currentCharacter >= 'a' &&
              currentCharacter <= 'z' ||
              currentCharacter >= 'A' &&
                  currentCharacter <= 'Z'
              ||
              currentCharacter >= '0' &&
                  currentCharacter <= '9'
              ||
              currentCharacter == '_') {
            lexeme.append(currentCharacter);
          } else {
            i--;
            state = 0;
            addToken(TokenType.IDENTIFIER, lexeme.toString());
          }
          break;
        case 26:
          if (currentCharacter >= 'a' &&
              currentCharacter <= 'z' ||
              currentCharacter >= 'A' &&
                  currentCharacter <= 'Z'
              ||
              currentCharacter >= '0' &&
                  currentCharacter <= '9') {
            state = 25;
            lexeme.append(currentCharacter);
          } else if (currentCharacter == '_') {
            lexeme.append(currentCharacter);
          } else {
            Main.error(numberLine, "Unable to parse: " + lexeme);
          }
          break;
        // Delimiters
        case 27:
          if (currentCharacter == ' ' ||
              currentCharacter == '\t' ||
              currentCharacter == '\n' ||
              currentCharacter == '\r') {
            lexeme.append(currentCharacter);
          } else {
            i--;
            state = 0;
            lexeme.delete(0, lexeme.length());
          }
          break;
        // Comments
        case 28:
          if (currentCharacter != '\n') {
            lexeme.append(currentCharacter);
          } else {
            i--;
            state = 0;
            lexeme.delete(0, lexeme.length());
          }
          break;
        case 29:
          if (currentCharacter == '*') {
            state = 30;
          } else {
            lexeme.append(currentCharacter);
          }
          break;
        case 30:
          if (currentCharacter == '/') {
            state = 31;
          }
          lexeme.append(currentCharacter);
          break;
        case 31:
          i--;
          state = 0;
          lexeme.delete(0, lexeme.length());
          break;
        default:
          Main.error(numberLine, "Unable to parse: " + currentCharacter);
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
