package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Arrays;
import java.util.Iterator;

public final class BinaryExpressionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final SyntaxToken operatorToken;
    private final ExpressionNode right;
    public BinaryExpressionNode(ExpressionNode left, SyntaxToken operatorToken, ExpressionNode right) {
        this.left = left;
        this.operatorToken = operatorToken;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return this.left;
    }
    public ExpressionNode getRight() {
        return this.right;
    }
    public SyntaxToken getOperatorToken() {
        return this.operatorToken;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.BINARY_EXPRESSION;
    }
    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Arrays.asList(left, operatorToken, right).iterator();
    }
}
