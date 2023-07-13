public class Calcs {
    public static double calcularExpressao(String expressao) {
        try {

            // Verifica se a expressão é vazia
            if (expressao.isEmpty()) {
                return Double.NaN;
            }

            // Remove espaços em branco da expressão
            expressao = expressao.replace(" ", "");

            // Verifica se a expressão contém apenas caracteres válidos
            if (!expressao.matches("^[()\\d+\\-*/.]+$")) {
                return Double.NaN;
            }

            // Calcula e retorna o valor do cálculo
            return eval(expressao);
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private static double eval(final String expressao) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expressao.length()) ? expressao.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expressao.length()) {
                    throw new RuntimeException("Caractere inesperado: " + (char) ch);
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        x += parseTerm();
                    } else if (eat('-')) {
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) {
                        x *= parseFactor();
                    } else if (eat('/')) {
                        x /= parseFactor();
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    return parseFactor();
                }
                if (eat('-')) {
                    return -parseFactor();
                }

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(expressao.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Número inválido");
                }

                return x;
            }
        }.parse();
    }
}
