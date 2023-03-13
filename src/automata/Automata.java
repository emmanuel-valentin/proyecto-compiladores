package automata;

import java.util.HashMap;
import java.util.Map;

public class Automata {
  private Map<Integer, Map<String, Integer>> automata;
  private Map<Integer, String> finalStates;
  private Integer initialState;

  public Automata(Map<Integer, String> finalStates, Integer initialState) {
    this.automata = new HashMap<>();
    this.finalStates = finalStates;
    this.initialState = initialState;
  }

  public Automata() {
    this.automata = new HashMap<>();
    this.finalStates = new HashMap<>();
    this.initialState = 0;
  }

  public void setFinalStates(Map<Integer, String> finalStates) {
    this.finalStates = finalStates;
  }

  public void setInitialState(Integer initialState) {
    this.initialState = initialState;
  }

  public void addTransition(Integer currentState, Integer nextState, String transition) {
    // Si el estado actual no existe, entonces lo crea y le añade la transición.
    /* if (this.automata.get(currentState) == null) {
      Map<String, Integer> transitions = new HashMap<>();
      this.automata.put(currentState, transitions);
      return;
    }
    // Si ya existe la transición, entoces no la añade al estado indicado.
    else if (this.automata.get(currentState).containsKey(transition)) return;
    // Si el estado actual existe, entonces le añade la transición.
    this.automata.get(currentState).put(transition, nextState); */
    Map<String, Integer> transitions = this.automata.get(currentState) == null ?
        new HashMap<>() : this.automata.get(currentState);
    transitions.put(transition, nextState);
    this.automata.put(currentState, transitions);
  }

  public boolean isValid(String word) {
    Integer currentState = this.initialState;
    for (char c : word.toCharArray()) {
      // TODO: Itera cada caracter de la palabra a validar y verifica
      //  si el último estado al que se mueve, es un estado final
      System.out.println(c);
    }
    return true;
  }

  @Override
  public String toString() {
    return this.automata.toString();
  }
}
