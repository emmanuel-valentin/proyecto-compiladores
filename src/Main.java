import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  static boolean hasErrors = false;
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.out.println("Correct use");
      System.exit(64);
    }
    else if (args.length == 1) {
      // Run file with source code
    }
    else {
      // Execute language prompt
    }
  }

  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    // Call run function
    if (hasErrors) {
      System.exit(65);
    }
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for(;;) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) {
        break;
      }
      // Call run function
      hasErrors = false;
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    for(Token token : tokens) {
      System.out.println(token);
    }
  }
}
