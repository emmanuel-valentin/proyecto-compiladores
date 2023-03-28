import java.util.*;
import java.util.regex.Pattern;

public class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();

  private int numberLine = 1;

  private static final Map<String, TokenType> keywords;
  private static final Map<String, TokenType> tokensWithLexeme;

  StringBuilder buffer;

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
    tokensWithLexeme.put("+", TokenType.PLUS);
    tokensWithLexeme.put("-", TokenType.MINUS);
    tokensWithLexeme.put("*", TokenType.MULTIPLY);
    tokensWithLexeme.put("/",TokenType.DIVIDE);
    tokensWithLexeme.put("-=",TokenType.MINUS_EQUAL);
    tokensWithLexeme.put("+=",TokenType.PLUS_EQUAL);
    tokensWithLexeme.put("*=",TokenType.MULTIPLY_EQUAL);
    tokensWithLexeme.put("/=",TokenType.DIVIDE_EQUAL);
  }

  Scanner(String source) {
    this.source = source;
    this.buffer = new StringBuilder();
  }

  private boolean validateTransition(char character, String regex) {
    return Pattern.compile(regex)
        .matcher(String.valueOf(character))
        .matches();
  }

  // TODO: Implementar los demás autómatas. Todos los autómatas deben estar integrado en uno solo
  // TODO: Evaluar palabras reservadas.
  public List<Token> scanTokens() {
    int state = 0;

    for (int i = 0; i <= this.source.length(); i++) {
      char currentCharacter = (i == this.source.length()) ?
          '\0' : this.source.charAt(i);

      numberLine = currentCharacter == '\n' ? ++numberLine : numberLine;

      switch (state) {
        case 0 -> {
          // Si existe la tansición, debemos agregar el caracter al buffer.
          if (validateTransition(currentCharacter, "<")) {
            state = 1;
            buffer.append(currentCharacter);
          }
          else if (validateTransition(currentCharacter, "=")) {
            state = 4;
            buffer.append(currentCharacter);
          }
          else if (validateTransition(currentCharacter, ">")) {
            state = 7;
            buffer.append(currentCharacter);
          }
          else if (validateTransition(currentCharacter, "!")) {
            state = 10;
            buffer.append(currentCharacter);
          }
          else if(validateTransition(currentCharacter,"\\+")){
            state = 12;
            buffer.append(currentCharacter);
          }
          else if(validateTransition(currentCharacter, "-")){
            state = 13;
            buffer.append(currentCharacter);
          }
          else if (validateTransition(currentCharacter, "\\*")){
            state = 14;
            buffer.append(currentCharacter);
          }
          else if(validateTransition(currentCharacter, "[^\\n]")){
            state = 28;
            buffer.append(currentCharacter);
          }
          else if (validateTransition(currentCharacter, "/")){
            state = 15;
            buffer.append(currentCharacter);
          }
          else if(validateTransition(currentCharacter,"\\d")){
            state = 20;
            buffer.append(buffer);
          }
        else if(validateTransition(currentCharacter, ".")){
            state= 21;
            buffer.append(buffer);
        }
        else if(validateTransition(currentCharacter,"\\d")){
          state = 22;
          // addToken(buffer.toString(), TokenType.NUMBER);
          buffer.append(buffer);
        }
        else if(validateTransition(currentCharacter, "[a-zA-Z]")){
          state = 25;
          buffer.append(buffer);
        }
        }
        case 1 -> {
          if (validateTransition(currentCharacter, "=")) {
            state = 2;
            buffer.append(currentCharacter);
          }
          // Estado de aceptación, pero no está declarado explícitamente
          else {
            state = 0;
            i--;
            addToken(buffer.toString());
          }
        }
        // Estos son estados de aceptación, debemos hacer lo mismo en todos.
        // Tal vez convenga refactorizar esto y moverlo a una función dado que
        // se repite constantemente
        case 2, 6, 8, 11 -> {
          state = 0;
          i--;
          addToken(buffer.toString());
        }
        case 4 -> {
          if (validateTransition(currentCharacter, "=")) {
            state = 6;
            buffer.append(currentCharacter);
          }
          // Estado de aceptación
          else {
            state = 0;
            i--;
            addToken(buffer.toString());
          }
        }
        case 7 -> {
          if (validateTransition(currentCharacter, "=")) {
            state = 8;
            buffer.append(currentCharacter);
          }
          // Estado de aceptación
          else {
            state = 0;
            i--;
            addToken(buffer.toString());
          }
        }
        case 10 -> {
          if (validateTransition(currentCharacter, "=")) {
            state = 11;
            buffer.append(currentCharacter);
          }
          // Estado de aceptación
          else {
            state = 0;
            i--;
            addToken(buffer.toString());
          }
        }
          case 12,13,14,15 ->{
            if(validateTransition(currentCharacter, "=")){
              state = 0;
              buffer.append(currentCharacter);
            }
            else {
              state = 0;
              i--;
              addToken(buffer.toString());
            }
        }
        default -> state = 0;
      }
    }
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }

  private void addToken(String token) {
    tokens.add(new Token(
        tokensWithLexeme.get(token),
        token,
        null,
        numberLine
    ));
    buffer.delete(0, buffer.length());
  }

  private void addToken(String lexeme, TokenType tokenType) {
    tokens.add(new Token(
        tokenType,
        lexeme,
        null,
        numberLine
    ));
    buffer.delete(0, buffer.length());
  }
}
