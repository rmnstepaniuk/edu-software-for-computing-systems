package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Arrays;
import java.util.Iterator;

public class FunctionCallExpressionNode extends ExpressionNode {
    private final SyntaxToken functionToken;
    private final SyntaxToken leftParenthesisToken;
    private final ExpressionNode argument;
    private final SyntaxToken rightParenthesisToken;

    public FunctionCallExpressionNode(
            SyntaxToken functionToken,
            SyntaxToken leftParenthesisToken,
            ExpressionNode argument,
            SyntaxToken rightParenthesisToken
    ) {
        this.functionToken = functionToken;
        this.leftParenthesisToken = leftParenthesisToken;
        this.argument = argument;
        this.rightParenthesisToken = rightParenthesisToken;
    }

    public SyntaxToken getFunctionToken() {
        return functionToken;
    }

    public SyntaxToken getLeftParenthesisToken() {
        return leftParenthesisToken;
    }

    public ExpressionNode getArgument() {
        return argument;
    }

    public SyntaxToken getRightParenthesisToken() {
        return rightParenthesisToken;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.FUNCTION_CALL_EXPRESSION;
    }

    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Arrays.asList(functionToken, leftParenthesisToken, argument, rightParenthesisToken).iterator();
    }
}