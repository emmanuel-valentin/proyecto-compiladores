import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
  private final Map<String, Object> symbolTable = new HashMap<>();
  private static SymbolTable instance;

  public static SymbolTable getInstance() {
    if (instance == null) {
      instance = new SymbolTable();
    }
    return instance;
  }

  public boolean isDefined(String identifier) {
    return symbolTable.containsKey(identifier);
  }

  public Object getValue(String identifier) {
    if (isDefined(identifier)) {
      return symbolTable.get(identifier);
    }
    throw new RuntimeException(identifier + " is not defined");
  }

  public void setValue(String identifier, Object value) {
    if (isDefined(identifier)) {
      symbolTable.put(identifier, value);
    }
    else {
      throw new RuntimeException(identifier + " is not defined");
    }
  }

  public void addVar(String identifier, Object value) {
    if (isDefined(identifier)) {
      throw new RuntimeException(identifier + " is already defined");
    }
    symbolTable.put(identifier, value);
  }

}
