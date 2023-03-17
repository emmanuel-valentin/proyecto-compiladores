package automata;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AutomataDefinition {

  public static Automata automata;

  static {
    automata = new Automata();

    automata.addTransition(0, 1, Pattern.compile("/"));
    automata.addTransition(1, 2, Pattern.compile("[*]"));
    automata.addTransition(2, 2, Pattern.compile("[*]"));
    automata.addTransition(2, 3, Pattern.compile("."));
    automata.addTransition(3, 3, Pattern.compile("[^*]"));
    automata.addTransition(3, 4, Pattern.compile("[*]"));
    automata.addTransition(4, 4, Pattern.compile("[*]"));
    automata.addTransition(4, 5, Pattern.compile("/"));

    automata.addFinalState(5);
  }
}
