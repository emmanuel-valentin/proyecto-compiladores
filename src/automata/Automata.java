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
    if (this.automata.get(currentState).containsKey(transition)) return;
    this.automata.get(currentState).put(transition, nextState);
  }

  public boolean isValid(String word) {
    Integer currentState = this.initialState;
    for (char c : word.toCharArray()) {
      // TODO: Iterate every character and move to the next state. Finally check if the last state is a final state.
      System.out.println(c);
    }
    return true;
  }
}
