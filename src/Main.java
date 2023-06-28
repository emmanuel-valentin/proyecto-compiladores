import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
  public static boolean errors = false;

  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.exit(64);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }

  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes));
    if (errors) {
      System.exit(65);
    }
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (;;) {
      System.out.print("\n> ");
      String line = reader.readLine();
      if (line == null) {
        break;
      }
      run(line);
      errors = false;
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    Parser parser = new Parser(tokens);
    parser.parse();

    GeneradorPostfija gpf = new GeneradorPostfija(tokens);
    List<Token> postfija = gpf.convertir();

    GeneradorAST gast = new GeneradorAST(postfija);
    Arbol programa = gast.generarAST();
    programa.recorrer();
    
  }

      /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */

  public static void error(int numberLine, String message) {
    report(numberLine, "", message);
  }

  private static void report(int numberLine, String where, String message) {
    System.err.println(
        "[line " + numberLine + "] Error" + where + ": " + message);
    errors = true;
  }
}
