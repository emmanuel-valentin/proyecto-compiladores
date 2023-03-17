package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Automata {
  private final Map<Integer, Map<Pattern, Integer>> automata;
  private final ArrayList<Integer> finalStates;
  private final Integer initialState;
  private StringBuffer buffer;

  public Automata() {
    this.automata = new HashMap<>();
    this.finalStates = new ArrayList<>();
    this.initialState = 0;
    this.buffer = new StringBuffer();
  }

  public void addFinalState(Integer state) {
    this.finalStates.add(state);
  }

  public void addTransition(Integer currentState, Integer nextState, Pattern transition) {
    Map<Pattern, Integer> transitions = this.automata.get(currentState) == null ?
        new HashMap<>() : this.automata.get(currentState);
    transitions.put(transition, nextState);
    this.automata.put(currentState, transitions);
  }

  public boolean evaluate(String word) {
    AtomicInteger currentState = new AtomicInteger(this.initialState);

    for (char character : word.toCharArray()) {
      this.automata.get(currentState.intValue()).forEach((transition, nextState) -> {
        if (transition.matcher(String.valueOf(character)).matches()) {
          currentState.set(nextState);
          this.buffer.append(character);
        }
      });
    }
    return this.finalStates.contains(currentState.intValue());
  }

  public StringBuffer getBuffer() {
    return buffer;
  }

  @Override
  public String toString() {
    return this.automata.toString();
  }
}
