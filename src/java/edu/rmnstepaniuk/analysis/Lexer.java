package edu.rmnstepaniuk.analysis;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String text;
    private int position;

    private final List<String> diagnostics = new ArrayList<>();

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
        // <number>
        // + - * / ( )
        // <whitespace>
        String txt;

        if (position > text.length() - 1)
            return new SyntaxToken("\0", position, SyntaxType.END_OF_FILE_TOKEN, null);

         if (Character.isDigit(currentChar())) {
            int start = position;
            while (Character.isDigit(currentChar())) next();
            txt = text.substring(start, position);
             if (tryParse(txt)) {
                 int value = Integer.parseInt(txt);
                 return new SyntaxToken(txt, start, SyntaxType.NUMBER_TOKEN, value);
             } else {
                 diagnostics.add("The number " + txt + " isn't a valid Integer");
                 return new SyntaxToken(txt, position++, SyntaxType.BAD_TOKEN, null);
             }
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

        diagnostics.add("ERROR: bad character input: '" + currentChar() + "'");
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
            int result = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
