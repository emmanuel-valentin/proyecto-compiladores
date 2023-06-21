import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GeneradorPostfija {

    private final List<Token> infija;
    private final Stack<Token> pila;
    private final List<Token> postfija;

    public GeneradorPostfija(List<Token> infija) {
        this.infija = infija;
        this.pila = new Stack<>();
        this.postfija = new ArrayList<>();
    }

    public List<Token> convertir(){
        boolean ControlStructure = false;
        Stack<Token> stackControlStructure = new Stack<>();

        for(int i=0; i<infija.size(); i++){
            Token t = infija.get(i);

            if(t.type == TokenType.EOF){
                break;
            }

            if(t.isKeyword()){
                /*
                Si el token actual es una palabra reservada, se va directo a la
                lista de salida.
                 */
                postfija.add(t);
                if (t.isControlStructure()){
                    ControlStructure = true;
                    stackControlStructure.push(t);
                }
            }
            else if(t.isOperand()){
                postfija.add(t);
            }
            else if(t.type == TokenType.LEFT_PAREN){
                pila.push(t);
            }
            else if(t.type == TokenType.RIGHT_PAREN){
                while(!pila.isEmpty() && pila.peek().type != TokenType.LEFT_PAREN){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                if(pila.peek().type == TokenType.LEFT_PAREN){
                    pila.pop();
                }
                if(ControlStructure){
                    postfija.add(new Token(TokenType.SEMICOLON, ";", null, i));
                }
            }
            else if(t.isOperator()){
                while(!pila.isEmpty() && pila.peek().greater_equalPrecedence(t)){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                pila.push(t);
            }
            else if(t.type == TokenType.SEMICOLON){
                while(!pila.isEmpty() && pila.peek().t != TokenType.LEFT_BRACE){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                postfija.add(t);
            }
            else if(t.type == TokenType.LEFT_BRACE){
                // Se mete a la pila, tal como el parentesis. Este paso
                // pudiera omitirse, sólo hay que tener cuidado en el manejo
                // del "}".
                pila.push(t);
            }
            else if(t.type == TokenType.RIGHT_BRACE && ControlStructure){

                // Primero verificar si hay un else:
                if(infija.get(i + 1).type== TokenType.ELSE){
                    // Sacar el "{" de la pila
                    pila.pop();
                }
                else{
                    // En este punto, en la pila sólo hay un token: "{"
                    // El cual se extrae y se añade un ";" a cadena postfija,
                    // El cual servirá para indicar que se finaliza la estructura
                    // de control.
                    pila.pop();
                    postfija.add(new Token(TokenType.SEMICOLON, ";", null, i));

                    // Se extrae de la pila de estrucuras de control, el elemento en el tope
                    stackControlStructure.pop();
                    if(stackControlStructure.isEmpty()){
                        ControlStructure = false;
                    }
                }


            }
        }
        while(!pila.isEmpty()){
            Token temp = pila.pop();
            postfija.add(temp);
        }

        while(!stackControlStructure.isEmpty()){
            stackControlStructure.pop();
            postfija.add(new Token(TokenType.SEMICOLON, ";", null, 0));
        }

        return postfija;
    }

}
