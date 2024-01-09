package edu.rmnstepaniuk.analysis;

import edu.rmnstepaniuk.analysis.nodes.*;

import java.util.ArrayList;

import java.util.List;

public class Parser {

    private final SyntaxToken[] tokens;
    private int position;
    private final List<String> diagnostics = new ArrayList<>();

    public Parser(String text) {
        List<SyntaxToken> tokenList = new ArrayList<>();
        Lexer lexer = new Lexer(text);
        SyntaxToken token;
        do {
            token = lexer.lex();

            if (token.getType() != SyntaxType.WHITESPACE_TOKEN && token.getType() != SyntaxType.BAD_TOKEN) {
                tokenList.add(token);
            }

        } while (token.getType() != SyntaxType.END_OF_FILE_TOKEN);
        this.tokens = tokenList.toArray(new SyntaxToken[0]);
        this.diagnostics.addAll(lexer.getDiagnostics());
    }

    private SyntaxToken peek(int offset) {
        int index = position + offset;
        if (index > tokens.length - 1) return tokens[tokens.length - 1];
        return tokens[index];
    }
    private SyntaxToken current() {
        return peek(0);
    }

    private SyntaxToken nextToken() {
        SyntaxToken current = current();
        position++;
        return current;
    }
    private SyntaxToken matchToken(SyntaxType type) {
        if (current().getType() == type) return nextToken();
        this.diagnostics.add("ERROR: Unexpected token <"+current().getType()+"> at position " + position + ", expected <"+type+">");
        return new SyntaxToken(null, current().getPosition(), current().getType(), null);
    }

    public SyntaxTree parse() {
        ExpressionNode expression = parseExpression(0);
        SyntaxToken endOfFileToken = matchToken(SyntaxType.END_OF_FILE_TOKEN);
        return new SyntaxTree(diagnostics, expression, endOfFileToken);
    }

    private ExpressionNode parseExpression(int parentPrecedence) {
        ExpressionNode left;
        int unaryOperatorPrecedence = SyntaxFacts.getUnaryOperatorPrecedence(current().getType());
        if (unaryOperatorPrecedence != 0 && parentPrecedence <= unaryOperatorPrecedence) {
            SyntaxToken operatorToken = nextToken();
            ExpressionNode operand = parseExpression(unaryOperatorPrecedence);
            left = new UnaryExpressionNode(operatorToken, operand);
        } else {
            left = parsePrimaryExpression();
        }

        while (true) {
            int precedence = SyntaxFacts.getBinaryOperatorPrecedence(current().getType());
            if (precedence == 0 || precedence <= parentPrecedence) break;
            SyntaxToken operatorToken = nextToken();
            ExpressionNode right = parseExpression(precedence);
            left = new BinaryExpressionNode(left, operatorToken, right);

        }
        return left;
    }

    private ExpressionNode parsePrimaryExpression() {

        if (current().getType() == SyntaxType.OPEN_PARENTHESIS_TOKEN) {
            SyntaxToken left = nextToken();
            ExpressionNode expression = parseExpression(0);
            SyntaxToken right = matchToken(SyntaxType.CLOSE_PARENTHESIS_TOKEN);
            return new ParenthesizedExpressionNode(left, expression, right);
        }


        SyntaxToken numberToken = matchToken(SyntaxType.NUMBER_TOKEN);
        return new LiteralExpressionNode(numberToken);
    }

    public SyntaxToken[] getTokens() {
        return tokens;
    }
    public int getPosition() {
        return position;
    }
    public List<String> getDiagnostics() {
        return diagnostics;
    }
}
