package edu.rmnstepaniuk;

import edu.rmnstepaniuk.analysis.nodes.*;

public record BinaryTree(ExpressionNode root) {

    public void printBinaryTree() {
        printBinaryTree(root, "");
    }

    private void printBinaryTree(ExpressionNode node, String indent) {
        if (node == null) {
            return;
        }

        if (node instanceof ParenthesizedExpressionNode parenNode) {
            printBinaryTree(parenNode.getExpression(), indent);
        } else {
            System.out.print(indent);

            if (node instanceof BinaryExpressionNode binaryNode) {
                System.out.println("└── " + binaryNode.getOperatorToken().getText());
                printBinaryTree(binaryNode.getLeft(), indent + "    ");
                printBinaryTree(binaryNode.getRight(), indent + "    ");
            } else if (node instanceof UnaryExpressionNode unaryNode) {
                System.out.println("└── " + unaryNode.getOperatorToken().getText());
                printBinaryTree(unaryNode.getOperand(), indent + "    ");
            } else if (node instanceof FunctionCallExpressionNode funcNode) {
                System.out.println("└── " + funcNode.getFunctionToken().getText());
                printBinaryTree(funcNode.getArgument(), indent + "    ");
            } else if (node instanceof LiteralExpressionNode literalNode) {
                System.out.println("└── " + literalNode.getLiteralToken().getValue());
            } else if (node instanceof IdentifierExpressionNode identifierNode) {
                System.out.println("└── " + identifierNode.getIdentifierToken().getText());
            }
        }
    }
}
