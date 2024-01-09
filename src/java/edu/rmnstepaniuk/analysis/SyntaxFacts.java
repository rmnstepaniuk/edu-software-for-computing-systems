package edu.rmnstepaniuk.analysis;

public class SyntaxFacts {
    public static int getBinaryOperatorPrecedence(SyntaxType type) {
        return switch (type) {
            case MULTIPLY_TOKEN, DIVIDE_TOKEN -> 2;
            case PLUS_TOKEN, MINUS_TOKEN -> 1;
            default -> 0;
        };
    }
}
