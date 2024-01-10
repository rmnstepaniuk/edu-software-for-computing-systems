package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Arrays;
import java.util.Iterator;

public final class ParenthesizedExpressionNode extends ExpressionNode {
    private final SyntaxToken openParenthesisToken;
    private final ExpressionNode expression;
    private final SyntaxToken closeParenthesisToken;

    public ParenthesizedExpressionNode(SyntaxToken openParenthesisToken, ExpressionNode expression, SyntaxToken closeParenthesisToken) {
        this.openParenthesisToken = openParenthesisToken;
        this.expression = expression;
        this.closeParenthesisToken = closeParenthesisToken;
    }

    public SyntaxToken getOpenParenthesisToken() {
        return openParenthesisToken;
    }

    public SyntaxToken getCloseParenthesisToken() {
        return closeParenthesisToken;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.PARENTHESIZED_EXPRESSION;
    }

    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Arrays.asList(openParenthesisToken, expression, closeParenthesisToken).iterator();
    }
}
