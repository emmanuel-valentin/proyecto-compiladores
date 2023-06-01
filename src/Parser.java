import java.util.List;

public class Parser {

  private final List<Token> tokens;
  private Token EOF = new Token(TokenType.EOF, "");
  private Token CLASS = new Token(TokenType.CLASS, "class");
  private Token ELSE = new Token(TokenType.ELSE, "else");
  private Token FOR = new Token(TokenType.FOR, "for");
  /**
   * TODO: Añadir todos los token de Token.java para compararlos con
   * con el token de preanálisis
   */


  private Token lookahead;

  // Servirá para ir obteniendo cada token de la lista de tokens generada en el análisis léxico
  private int i;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  public void parse() {
    i = 0;
    lookahead = tokens.get(i);
    // Ejecutar la primera producción: Por cada no terminal crear una función (Ver código SQL)
    // Por cada terminal ejecutar la función match
  }

  private void match(Token terminal) {
    if(Main.errors) return;
    if (lookahead == terminal) {
      i++;
      lookahead =tokens.get(i);
    }
    else {
      Main.errors = true;
      Main.error(lookahead.getNumberLine(), "Error en la posición " +  i + ", se esperaba un " + terminal.type);
    }
  }
}
