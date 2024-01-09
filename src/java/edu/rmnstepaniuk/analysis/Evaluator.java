package edu.rmnstepaniuk.analysis;

import edu.rmnstepaniuk.analysis.nodes.BinaryExpressionNode;
import edu.rmnstepaniuk.analysis.nodes.ExpressionNode;
import edu.rmnstepaniuk.analysis.nodes.LiteralExpressionNode;
import edu.rmnstepaniuk.analysis.nodes.ParenthesizedExpressionNode;

public record Evaluator(ExpressionNode root) {

    public int evaluate() throws Exception {
        return evaluateExpression(this.root);
    }

    private int evaluateExpression(ExpressionNode node) throws Exception {
        switch (node.getType()) {
            case LITERAL_EXPRESSION -> {
                LiteralExpressionNode n = (LiteralExpressionNode) node;
                return (int) n.getLiteralToken().getValue();
            }
            case BINARY_EXPRESSION -> {
                BinaryExpressionNode b = (BinaryExpressionNode) node;
                int left = evaluateExpression(b.getLeft());
                int right = evaluateExpression(b.getRight());
                SyntaxType operatorToken = b.getOperatorToken().getType();
                return switch (operatorToken) {
                    case PLUS_TOKEN -> left + right;
                    case MINUS_TOKEN -> left - right;
                    case MULTIPLY_TOKEN -> left * right;
                    case DIVIDE_TOKEN -> left / right;
                    default -> throw new Exception("Unexpected binary operator " + operatorToken);
                };
            }
            case PARENTHESIZED_EXPRESSION -> {
                ParenthesizedExpressionNode p = (ParenthesizedExpressionNode) node;
                return evaluateExpression(p.getExpression());
            }
            default -> throw new Exception("Unexpected node " + node.getType());
        }
    }
}
