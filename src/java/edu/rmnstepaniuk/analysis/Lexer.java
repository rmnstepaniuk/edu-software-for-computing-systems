package edu.rmnstepaniuk.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private final String text;
    private int position;

    private final List<String> diagnostics = new ArrayList<>();

    private static final Map<String, SyntaxType> FUNCTION_KEYWORDS = new HashMap<>();

    static {
        FUNCTION_KEYWORDS.put("sin", SyntaxType.SIN_FUNCTION_TOKEN);
        FUNCTION_KEYWORDS.put("cos", SyntaxType.COS_FUNCTION_TOKEN);
        FUNCTION_KEYWORDS.put("tan", SyntaxType.TAN_FUNCTION_TOKEN);
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
        String txt;

        if (position > text.length() - 1)
            return new SyntaxToken("\0", position, SyntaxType.END_OF_FILE_TOKEN, null);

         if (Character.isDigit(currentChar()) || currentChar() == '.') {
            int start = position;
            while (Character.isDigit(currentChar()) || currentChar() == '.') next();
            txt = text.substring(start, position);
             if (tryParse(txt)) {
                 float value = Float.parseFloat(txt);
                 return new SyntaxToken(txt, start, SyntaxType.NUMBER_TOKEN, value);
             } else {
                 diagnostics.add("LEXICAL ERROR: The number " + txt + " isn't a valid Float");
                 return new SyntaxToken(txt, position++, SyntaxType.BAD_TOKEN, null);
             }
        }

        if (Character.isLetter(currentChar())) {
            int start = position;
            while (Character.isLetter(currentChar())) next();
            txt = text.substring(start, position);

            SyntaxType functionTokenType = FUNCTION_KEYWORDS.get(txt.toLowerCase());
            if (functionTokenType != null) {
                return new SyntaxToken(txt, start, functionTokenType);
            }

            diagnostics.add("LEXICAL ERROR: Unrecognized identifier: '" + txt + "' at position " + start);
            return new SyntaxToken(txt, start, SyntaxType.BAD_TOKEN, null);
        }

        if (Character.isWhitespace(currentChar())) {
            int start = position;
            while (Character.isWhitespace(currentChar())) next();
            txt = text.substring(start, position);
            return new SyntaxToken(txt, start, SyntaxType.WHITESPACE_TOKEN);
        }
        if (currentChar() == '+')
            return new SyntaxToken("+", position++, SyntaxType.PLUS_TOKEN, null);
        else if (currentChar() == '-')
            return new SyntaxToken("-", position++, SyntaxType.MINUS_TOKEN, null);
        else if (currentChar() == '/')
            return new SyntaxToken("/", position++, SyntaxType.DIVIDE_TOKEN, null);
        else if (currentChar() == '*')
            return new SyntaxToken("*", position++, SyntaxType.MULTIPLY_TOKEN, null);
        else if (currentChar() == '(')
            return new SyntaxToken("(", position++, SyntaxType.OPEN_PARENTHESIS_TOKEN, null);
        else if (currentChar() == ')')
            return new SyntaxToken(")", position++, SyntaxType.CLOSE_PARENTHESIS_TOKEN, null);

        diagnostics.add("LEXICAL ERROR: bad character input: '" + currentChar() + "' at position " + position);
        return new SyntaxToken(String.valueOf(text.charAt(position)), position++, SyntaxType.BAD_TOKEN, null);
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
