package edu.rmnstepaniuk.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private final String text;
    private int position;

    private final List<String> diagnostics = new ArrayList<>();

    private static final Map<String, SyntaxType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("sin", SyntaxType.SIN_FUNCTION_TOKEN);
        KEYWORDS.put("cos", SyntaxType.COS_FUNCTION_TOKEN);
        KEYWORDS.put("tan", SyntaxType.TAN_FUNCTION_TOKEN);
    }

    public Lexer(String text) {
        this.text = text;
    }

    private char currentChar() {
        if (position > text.length() - 1) return '\0';
        else return text.charAt(position);
    }

    private void next() {
        position++;
    }

    public SyntaxToken lex() {

        if (position > text.length() - 1)
            return new SyntaxToken("\0", position, SyntaxType.END_OF_FILE_TOKEN, null);

        if (Character.isDigit(currentChar()) || currentChar() == '.') {
            return lexNumber();
        }

        if (Character.isLetter(currentChar())) {
            return lexIdentifierOrKeyword();
        }

        if (Character.isWhitespace(currentChar())) {
            return lexWhitespace();
        }

        return lexOperatorOrSymbol();
    }

    private SyntaxToken lexNumber() {
        int start = position;
        while (Character.isDigit(currentChar()) || currentChar() == '.') {
            next();
        }
        String txt = text.substring(start, position);

        if (tryParse(txt)) {
            float value = Float.parseFloat(txt);
            return new SyntaxToken(txt, start, SyntaxType.NUMBER_TOKEN, value);
        } else {
            diagnostics.add("LEXICAL ERROR: The number " + txt + " isn't a valid Float");
            return new SyntaxToken(txt, position++, SyntaxType.BAD_TOKEN, null);
        }
    }

    private SyntaxToken lexIdentifierOrKeyword() {
        int start = position;
        while (Character.isLetterOrDigit(currentChar())) {
            next();
        }
        String txt = text.substring(start, position);

        SyntaxType tokenType = KEYWORDS.getOrDefault(txt.toLowerCase(), SyntaxType.IDENTIFIER_TOKEN);
        return new SyntaxToken(txt, start, tokenType);
    }

    private SyntaxToken lexWhitespace() {
        int start = position;
        while (Character.isWhitespace(currentChar())) {
            next();
        }
        String txt = text.substring(start, position);
        return new SyntaxToken(txt, start, SyntaxType.WHITESPACE_TOKEN);
    }

    private SyntaxToken lexOperatorOrSymbol() {
        int start = position;
        next();
        String txt = text.substring(start, position);

        switch (txt) {
            case "+":
                return new SyntaxToken(txt, start, SyntaxType.PLUS_TOKEN, null);
            case "-":
                return new SyntaxToken(txt, start, SyntaxType.MINUS_TOKEN, null);
            case "/":
                return new SyntaxToken(txt, start, SyntaxType.DIVIDE_TOKEN, null);
            case "*":
                return new SyntaxToken(txt, start, SyntaxType.MULTIPLY_TOKEN, null);
            case "(":
                return new SyntaxToken(txt, start, SyntaxType.OPEN_PARENTHESIS_TOKEN, null);
            case ")":
                return new SyntaxToken(txt, start, SyntaxType.CLOSE_PARENTHESIS_TOKEN, null);
            default:
                diagnostics.add("LEXICAL ERROR: Unrecognized character: '" + currentChar() + "' at position " + position);
                return new SyntaxToken(String.valueOf(currentChar()), position++, SyntaxType.BAD_TOKEN, null);
        }
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    private boolean tryParse(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
