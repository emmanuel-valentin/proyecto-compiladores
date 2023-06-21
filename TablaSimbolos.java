import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private final Map<String, Object> values = new HashMap<>();

    boolean existeIdentificador(String identifier){
        return values.containsKey(identifier);
    }

    Object get(String identifier) {
        if (values.containsKey(identifier)) {
            return values.get(identifier);
        }
        throw new RuntimeException("Variable no definida '" + identifier + "'.");
    }

    void asignar(String identifier, Object value){
        values.put(identifier, value);
    }

}