package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Automata {
  private Map<Integer, Map<String, Integer>> automata;
  private ArrayList<Integer> finalStates;
  private Integer initialState;

  public Automata(ArrayList<Integer> finalStates, Integer initialState) {
    this.automata = new HashMap<>();
    this.finalStates = finalStates;
    this.initialState = initialState;
  }

  public Automata() {
    this.automata = new HashMap<>();
    this.finalStates = new ArrayList<>();
    this.initialState = 0;
  }

  public void setFinalStates(ArrayList<Integer> finalStates) {
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
      currentState = this.automata.get(currentState).get(String.valueOf(c));
      System.out.println(currentState);
    }
    return this.finalStates.contains(currentState);
  }

  @Override
  public String toString() {
    return this.automata.toString();
  }
}
