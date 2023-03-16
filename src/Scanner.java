import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();

  private int numberLine = 1;

  private static final Map<String, TokenType> keywords;

  static {
    keywords = new HashMap<>();

    keywords.put("and", TokenType.AND);
    keywords.put("class", TokenType.CLASS);
    keywords.put("else", TokenType.ELSE);
    keywords.put("false", TokenType.FALSE);
    keywords.put("true", TokenType.TRUE);
    keywords.put("for", TokenType.FOR);
    keywords.put("new", TokenType.NEW);
    keywords.put("null", TokenType.NULL);
    keywords.put("or", TokenType.OR);
    keywords.put("while", TokenType.WHILE);
    keywords.put("case", TokenType.CASE);
    keywords.put("switch", TokenType.SWITCH);
    keywords.put("break", TokenType.BREAK);
    keywords.put("super", TokenType.SUPER);
    keywords.put("return", TokenType.RETURN);
    keywords.put("true", TokenType.TRUE);
    keywords.put("if", TokenType.IF);
    keywords.put("do", TokenType.DO);
    keywords.put("print", TokenType.PRINT);
    keywords.put("this", TokenType.THIS);
    keywords.put("int", TokenType.INT);
    keywords.put("float", TokenType.FLOAT);
    keywords.put("double", TokenType.DOUBLE);
    keywords.put("boolean", TokenType.BOOLEAN);
    keywords.put("long", TokenType.LONG);
    keywords.put("string", TokenType.STRING);
    keywords.put("char", TokenType.CHAR);
    keywords.put("byte", TokenType.BYTE);
  }

  Scanner(String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }
}
