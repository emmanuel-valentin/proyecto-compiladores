import java.util.Objects;

public class SolverAritmetico {

  private final Nodo nodo;

  public SolverAritmetico(Nodo nodo) {
    this.nodo = nodo;
  }

  public Object solve() {
    return solve(nodo);
  }

  private Object solve(Nodo n) {
    SymbolTable symbolTable = SymbolTable.getInstance();
    // No tiene hijos, es un operando
    if (n.getHijos() == null) {
      if(n.getValue().getType() == TokenType.NUMBER || n.getValue().getType() == TokenType.STRING){
        return n.getValue().getLiteral();
      }
      else if(n.getValue().getType() == TokenType.IDENTIFIER){
        return symbolTable.getValue(n.getValue().getLexeme());
      }
      else if (n.getValue().getType() == TokenType.TRUE || n.getValue().getType() == TokenType.FALSE) {
        return Boolean.parseBoolean(n.getValue().getLexeme());
      }
    }

    // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
    Nodo izq = n.getHijos().get(0);
    Nodo der = n.getHijos().get(1);

    Object resultadoIzquierdo = solve(izq);
    Object resultadoDerecho = solve(der);

    // Operaciones con double
    if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double) {
      switch (n.getValue().getType()) {
        case PLUS:
          return ((Double) resultadoIzquierdo + (Double) resultadoDerecho);
        case MINUS:
          return ((Double) resultadoIzquierdo - (Double) resultadoDerecho);
        case MULTIPLY:
          return ((Double) resultadoIzquierdo * (Double) resultadoDerecho);
        case DIVIDE:
          return ((Double) resultadoIzquierdo / (Double) resultadoDerecho);
        case EQUAL:
          return resultadoIzquierdo.equals(resultadoDerecho);
        case NOT_EQUAL:
          return !resultadoIzquierdo.equals(resultadoDerecho);
        case LESS:
          return ((Double) resultadoIzquierdo < (Double) resultadoDerecho);
        case LESS_EQUAL:
          return ((Double) resultadoIzquierdo <= (Double) resultadoDerecho);
        case GREATER:
          return ((Double) resultadoIzquierdo > (Double) resultadoDerecho);
        case GREATER_EQUAL:
          return ((Double) resultadoIzquierdo >= (Double) resultadoDerecho);
      }
    }
    else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String) {
      boolean equals = String.valueOf(resultadoIzquierdo).equals(String.valueOf(resultadoDerecho));
      switch (n.getValue().getType()) {
        case PLUS:
          return String.valueOf(resultadoIzquierdo).concat(String.valueOf(resultadoDerecho));
        case EQUAL:
          return equals;
        case NOT_EQUAL:
          return !equals;
      }
    }
    else if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
      switch (n.getValue().getType()) {
        case AND:
          return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
        case OR:
          return ((Boolean) resultadoIzquierdo || (Boolean) resultadoDerecho);
        case EQUAL:
          return resultadoIzquierdo == resultadoDerecho;
        case NOT_EQUAL:
          return resultadoIzquierdo != resultadoDerecho;
      }
    }
    else if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof String ||
        resultadoIzquierdo instanceof String && resultadoDerecho instanceof Double) {
      if (Objects.requireNonNull(n.getValue().getType()) == TokenType.PLUS) {
        return resultadoIzquierdo + resultadoDerecho.toString();
      }
    }
    else {
      throw new RuntimeException("Type mismatch");
    }

    return null;
  }
}

