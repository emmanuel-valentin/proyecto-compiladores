package automata;

import java.util.ArrayList;
public class AutomataDefinition {
  /**
   * Este es ejemplo de un automata que valida cadenas que contengan un número par de 0,
   * y sin símbolos 1 consecutivos.
   */
  public static Automata automata;
  static {
    automata = new Automata();

    automata.addFinalState(2);
    automata.addFinalState(3);

    automata.addTransition(0, 1, "0");
    automata.addTransition(0, 3, "1");

    automata.addTransition(1, 2, "0");
    automata.addTransition(1, 5, "1");

    automata.addTransition(2, 4, "0");
    automata.addTransition(2, 3, "1");

    automata.addTransition(3, 1, "0");
    automata.addTransition(3, 6, "1");

    automata.addTransition(4, 2, "0");
    automata.addTransition(4, 5, "1");

    automata.addTransition(5, 2, "0");
    automata.addTransition(5, 6, "1");

    automata.addTransition(6, 6, "0");
    automata.addTransition(6, 6, "1");
  }
}
