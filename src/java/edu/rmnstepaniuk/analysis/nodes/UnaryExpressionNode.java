package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Arrays;
import java.util.Iterator;

public class UnaryExpressionNode extends ExpressionNode {

    private final SyntaxToken operatorToken;
    private final ExpressionNode operand;
    public UnaryExpressionNode(SyntaxToken operatorToken, ExpressionNode operand) {
        this.operatorToken = operatorToken;
        this.operand = operand;
    }

    public ExpressionNode getOperand() {
        return operand;
    }
    public SyntaxToken getOperatorToken() {
        return operatorToken;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.UNARY_EXPRESSION;
    }
    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Arrays.asList(operatorToken, operand).iterator();
    }
}