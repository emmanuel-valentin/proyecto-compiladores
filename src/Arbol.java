public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            try {
                Token t = n.getValue();
                switch (t.getType()) {
                    // Operadores aritméticos
                    case PLUS:
                    case MINUS:
                    case MULTIPLY:
                    case DIVIDE:
                    case LESS:
                    case GREATER:
                    case LESS_EQUAL:
                    case GREATER_EQUAL:
                    case EQUAL:
                    case NOT_EQUAL:
                    case AND:
                    case OR:
                        SolverAritmetico solver = new SolverAritmetico(n);
                        solver.solve();
                        break;

                    case VAR:
                        VarSolver varSolver = new VarSolver(n);
                        varSolver.solve();
                        break;
                    case IF:
                        IfSolver ifSolver = new IfSolver(n);
                        ifSolver.solve();
                        break;
                    case FOR:
                        ForSolver forSolver = new ForSolver(n);
                        forSolver.solve();
                        break;
                    case WHILE:
                        WhileSolver whileSolver = new WhileSolver(n);
                        whileSolver.solve();
                        break;
                    case PRINT:
                        PrintSolver printSolver = new PrintSolver(n);
                        printSolver.solve();
                        break;
                    case ASSIGN:
                        AssingSolver assingSolver = new AssingSolver(n);
                        assingSolver.solve();
                    default:
                        break;

                }
            }
            catch (Exception e){
                Main.error(n.getValue().getNumberLine(), e.getMessage());
                break;
            }

        }
    }

}

