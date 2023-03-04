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

    keywords.put("clase", TokenType.CLASE);
    keywords.put("clase", TokenType.CLASE);
    // TODO: Add more keywords here
  }

  Scanner(String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    tokens.add(new Token(TokenType.EOF, "", null, numberLine));
    return tokens;
  }
}
