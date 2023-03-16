package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Automata {
  private final Map<Integer, Map<String, Integer>> automata;
  private final ArrayList<Integer> finalStates;
  private final Integer initialState;

  public Automata() {
    this.automata = new HashMap<>();
    this.finalStates = new ArrayList<>();
    this.initialState = 0;
  }

  public void addFinalState(Integer state) {
    this.finalStates.add(state);
  }

  public void addTransition(Integer currentState, Integer nextState, String transition) {
    Map<String, Integer> transitions = this.automata.get(currentState) == null ?
        new HashMap<>() : this.automata.get(currentState);
    transitions.put(transition, nextState);
    this.automata.put(currentState, transitions);
  }

  public boolean evaluate(String word) {
    Integer currentState = this.initialState;
    for (char c : word.toCharArray()) {
      currentState = this.automata.get(currentState).get(String.valueOf(c));
    }
    return this.finalStates.contains(currentState);
  }

  @Override
  public String toString() {
    return this.automata.toString();
  }
}
