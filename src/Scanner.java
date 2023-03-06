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
    // TODO: Add more keywords here
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
    keywords.put("var", TokenType.VAR);
    keywords.put("plus", TokenType.PLUS);
    keywords.put("minus", TokenType.MINUS);
    keywords.put("star", TokenType.STAR);
    keywords.put("slash", TokenType.SLASH);
    keywords.put("percent", TokenType.PERCENT);
    keywords.put("equal", TokenType.EQUAL);
    keywords.put("equal_equal", TokenType.EQUAL_EQUAL);
    keywords.put("greater", TokenType.GREATER);
    keywords.put("greater_equal", TokenType.GREATER_EQUAL);
    keywords.put("less", TokenType.LESS);
    keywords.put("less_equal", TokenType.LESS_EQUAL);
    keywords.put("not", TokenType.NOT);
    keywords.put("not_equal", TokenType.NOT_EQUAL);
    keywords.put("left_paren", TokenType.LEFT_PAREN);
    keywords.put("right_paren", TokenType.RIGHT_PAREN);
    keywords.put("left_brace", TokenType.LEFT_BRACE);
    keywords.put("right_brace", TokenType.RIGHT_BRACE);
    keywords.put("left_bracket", TokenType.LEFT_BRACKET);
    keywords.put("right_bracket", TokenType.RIGHT_BRACKET);
    keywords.put("comma", TokenType.COMMA);
    keywords.put("dot", TokenType.DOT);
    keywords.put("semicolon", TokenType.SEMICOLON);
  }

  Scanner(String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }
}
