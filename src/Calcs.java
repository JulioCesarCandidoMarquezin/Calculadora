import java.util.ArrayList;
import java.util.List;

public class Calcs {

    public static String calculateExpression (String expression) {
        try {
            List<Double> numbers = new ArrayList<>();
            List<Character> operators = new ArrayList<>();

            // Faz um boolean que verifica se tem um -+ depois de um /* se sim, adiciona no numero

            StringBuilder actualNumber = new StringBuilder();
            for (char c : expression.toCharArray()) {
                if (Character.isDigit(c) || c == '.') {
                    actualNumber.append(c);
                } else {
                    numbers.add(Double.parseDouble(actualNumber.toString()));
                    actualNumber = new StringBuilder();
                    operators.add(c);
                }
            }
            numbers.add(Double.parseDouble(actualNumber.toString()));

            if (numbers.size() == operators.size() + 1) {
                double resultado = 0;

                for (int i = 0; i < operators.size(); ) {
                    char operator = operators.get(i);
                    if (operator == '*') {
                        resultado = numbers.get(i) * numbers.get(i + 1);
                        operators.remove(i);
                        numbers.remove(i);
                        numbers.remove(i);
                        numbers.add(i, resultado);
                    } else if (operator == '/') {
                        resultado = numbers.get(i) / numbers.get(i + 1);
                        operators.remove(i);
                        numbers.remove(i);
                        numbers.remove(i);
                        numbers.add(i, resultado);
                    } else break;
                }

                for (int i = 0; i < operators.size(); ) {
                    char operator = operators.get(i);
                    if (operator == '+') {
                        resultado = numbers.get(i) + numbers.get(i + 1);
                        operators.remove(i);
                        numbers.remove(i);
                        numbers.remove(i);
                        numbers.add(i, resultado);
                    } else if (operator == '-') {
                        resultado = numbers.get(i) - numbers.get(i + 1);
                        operators.remove(i);
                        numbers.remove(i);
                        numbers.remove(i);
                        numbers.add(i, resultado);
                    }
                }

                return String.valueOf(resultado);
            } else {
                return "NaN";
            }
        } catch (Exception e) {
            return "NaN";
        }
    }
}

