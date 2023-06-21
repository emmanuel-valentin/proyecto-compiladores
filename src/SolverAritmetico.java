public class SolverAritmetico {

    private final Nodo nodo;

    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().type == TokenType.NUMBER || n.getValue().type == TokenType.STRING){
                return n.getValue().literal;
            }
            else if(n.getValue().type == TokenType.IDENTIFIER){
                // Ver la tabla de símbolos
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().type){
                case PLUS:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case MINUS:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case MULTIPLY:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case DIVIDE:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
                default:
                    break;
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().type == TokenType.PLUS){
                // Ejecutar la concatenación
            }
        }
        else{
            // Error por diferencia de tipos
        }

        return null;
    }
}

