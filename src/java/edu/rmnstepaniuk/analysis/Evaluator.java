package edu.rmnstepaniuk.analysis;

import edu.rmnstepaniuk.analysis.nodes.*;

public record Evaluator(ExpressionNode root) {

    public float evaluate() throws Exception {
        return evaluateExpression(this.root);
    }

    private float evaluateExpression(ExpressionNode node) throws Exception {
        switch (node.getType()) {
            case LITERAL_EXPRESSION -> {
                LiteralExpressionNode n = (LiteralExpressionNode) node;
                return (float) n.getLiteralToken().getValue();
            }
            case UNARY_EXPRESSION -> {
                UnaryExpressionNode u = (UnaryExpressionNode) node;
                float operand = evaluateExpression(u.getOperand());
                SyntaxType operatorToken = u.getOperatorToken().getType();
                if (operatorToken == SyntaxType.PLUS_TOKEN) return operand;
                else if (operatorToken == SyntaxType.MINUS_TOKEN) return -operand;
                else throw new Exception("Unexpected unary operator " + operatorToken);
            }
            case BINARY_EXPRESSION -> {
                BinaryExpressionNode b = (BinaryExpressionNode) node;
                float left = evaluateExpression(b.getLeft());
                float right = evaluateExpression(b.getRight());
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
            case FUNCTION_CALL_EXPRESSION -> {
                FunctionCallExpressionNode f = (FunctionCallExpressionNode) node;
                SyntaxType functionType = f.getFunctionToken().getType();
                ExpressionNode argument = f.getArgument();

                float argumentValue = evaluateExpression(argument);
                return switch (functionType) {
                    case SIN_FUNCTION_TOKEN -> (float) Math.sin(argumentValue);
                    case COS_FUNCTION_TOKEN -> (float) Math.cos(argumentValue);
                    case TAN_FUNCTION_TOKEN -> (float) Math.tan(argumentValue);
                    default -> throw new Exception("Unexpected function call " + functionType);
                };
            }
            default -> throw new Exception("Unexpected node " + node.getType());
        }
    }
}
