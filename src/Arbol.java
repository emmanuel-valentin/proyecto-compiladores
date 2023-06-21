
import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.type){
                // Operadores aritméticos
                case PLUS:
                case MINUS:
                case MULTIPLY:
                case DIVIDE:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;

                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    break;
                case IF:
                    break;
                case FOR:
                    break;
                case WHILE:
                    break;
                case PRINT:
                    break;
                default:
                    break;

            }
        }
    }

}

