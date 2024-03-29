package edu.rmnstepaniuk;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.nodes.*;

import java.util.Iterator;

public class Printer {
    public static void printSyntaxTree(SyntaxNode node, String indent) {
        System.out.print("\u001B[37m");
        String marker = "└──";

        System.out.print(indent);
        System.out.print(marker);
        System.out.print(node.getType());

        if (node instanceof SyntaxToken t && t.getValue() != null) {
            System.out.print(" ");
            System.out.print(t.getValue());
        }
        System.out.println();
        indent += "   ";

        for (Iterator<SyntaxNode> it = node.getChildren(); it.hasNext(); ) {
            SyntaxNode child = it.next();
            printSyntaxTree(child, indent);
        }
        System.out.print("\u001B[0m");
    }
    public static void printBinaryTree(ExpressionNode node, String indent) {
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
