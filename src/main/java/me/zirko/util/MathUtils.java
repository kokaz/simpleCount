package me.zirko.util;

import java.util.HashMap;
import java.util.Map;

public class MathUtils {
    public static HashMap<String, String> sOperators = new HashMap<>();

    static {
        sOperators.put("+", "add");
        sOperators.put("-", "subtraction");
        sOperators.put("×", "multiplication");
        sOperators.put("÷", "division");
        sOperators.put("mod", "modulo");
        sOperators.put("sin", "sin");
        sOperators.put("cos", "cos");
        sOperators.put("tan", "tan");
        sOperators.put("sinh", "sinh");
        sOperators.put("cosh", "cosh");
        sOperators.put("tanh", "tanh");
        sOperators.put("log", "log");
        sOperators.put("ln", "ln");
        sOperators.put("exp", "exp");
        sOperators.put("√", "sqrt");
        sOperators.put("²", "sqrt");
        sOperators.put("^", "pow");
    }

    /**
     * Return the litteral name of operation passed as parameter
     *
     * @param operator An operator
     * @return The common name of {@code operator}
     */
    public static String getLiteral(String operator) {
        for (Map.Entry<String, String> e : sOperators.entrySet()) {
            if (operator.equals(e.getKey())) {
                return e.getValue();
            }
        }
        return null;
    }
}
