import java.util.ArrayList;
import java.util.List;

public class Calcs {

    public static String calculateExpression (String expression) {
        try {
            List<Double> numbers = new ArrayList<>();
            List<Character> operators = new ArrayList<>();

            StringBuilder actualNumber = new StringBuilder();
            boolean isFirstSimbolPlusOrMinus = true;
            boolean isSymbolFollowedOtherSymbol;
            char lastCharacter = '0';

            for (char character : expression.toCharArray()) {
                isSymbolFollowedOtherSymbol = ((lastCharacter == '*' || lastCharacter == '/') && (character == '+' || character == '-'));
                isFirstSimbolPlusOrMinus = (isFirstSimbolPlusOrMinus && (character == '+' || character == '-'));
                if (isSymbolFollowedOtherSymbol || (isFirstSimbolPlusOrMinus) || Character.isDigit(character) || character == '.') {
                    actualNumber.append(character);
                } else {
                    numbers.add(Double.parseDouble(actualNumber.toString()));
                    actualNumber = new StringBuilder();
                    operators.add(character);
                }
                isFirstSimbolPlusOrMinus = false;
                lastCharacter = character;
            }
            numbers.add(Double.parseDouble(actualNumber.toString()));

            if (numbers.size() == operators.size() + 1) {
                double resultado = 0;

                for (int i = 0; i < operators.size(); ) {
                    char operator = operators.get(i);
                    if (operator == '*') {
                        resultado = numbers.get(i) * numbers.get(i + 1);
                        removeElementsAndAddResult(operators, numbers, i,  resultado);
                    } else if (operator == '/') {
                        resultado = numbers.get(i) / numbers.get(i + 1);
                        removeElementsAndAddResult(operators, numbers, i,  resultado);
                    } else break;
                }

                for (int i = 0; i < operators.size(); ) {
                    char operator = operators.get(i);
                    if (operator == '+') {
                        resultado = numbers.get(i) + numbers.get(i + 1);
                        removeElementsAndAddResult(operators, numbers, i,  resultado);
                    } else if (operator == '-') {
                        resultado = numbers.get(i) - numbers.get(i + 1);
                        removeElementsAndAddResult(operators, numbers, i,  resultado);
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

    private static void removeElementsAndAddResult(List<Character> operators, List<Double> numbers, int index, double result) {
        operators.remove(index);
        numbers.remove(index);
        numbers.remove(index);
        numbers.add(index, result);
    }
}

