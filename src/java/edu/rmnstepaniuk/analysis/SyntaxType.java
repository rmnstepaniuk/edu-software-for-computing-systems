package edu.rmnstepaniuk.analysis;

public enum SyntaxType {
    // Tokens
    BAD_TOKEN,
    END_OF_FILE_TOKEN,
    WHITESPACE_TOKEN,
    NUMBER_TOKEN,
    PLUS_TOKEN,
    MINUS_TOKEN,
    DIVIDE_TOKEN,
    MULTIPLY_TOKEN,
    OPEN_PARENTHESIS_TOKEN,
    CLOSE_PARENTHESIS_TOKEN,

    // Expressions
    LITERAL_EXPRESSION,
    BINARY_EXPRESSION,
    PARENTHESIZED_EXPRESSION
}
