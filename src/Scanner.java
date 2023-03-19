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
  }

  public List<Token> scanTokens() {
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    System.out.println(this.source);
    return tokens;
  }
}
