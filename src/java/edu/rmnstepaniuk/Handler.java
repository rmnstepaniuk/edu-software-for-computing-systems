package edu.rmnstepaniuk;

import edu.rmnstepaniuk.analysis.Evaluator;
import edu.rmnstepaniuk.analysis.nodes.SyntaxNode;
import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxTree;

import java.util.Iterator;
import java.util.Scanner;

public class Handler {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String line;
        SyntaxTree syntaxTree;

        while (true) {
            System.out.print("> ");
            line = scanner.nextLine();
            if (line.equals("#exit")) {
                System.out.println("Exiting program");
                break;
            }
            syntaxTree = SyntaxTree.parse(line);

            prettyPrint(syntaxTree.getRoot(), "");

            if (syntaxTree.getDiagnostics().isEmpty()) {
                Evaluator evaluator = new Evaluator(syntaxTree.getRoot());
                try {
                    float result = evaluator.evaluate();
                    System.out.println("= " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                for (String diagnosis : syntaxTree.getDiagnostics()) {
                    System.out.println("\u001B[31m" + diagnosis);
                    System.out.print("\u001B[0m");
                }
            }
        }
    }

    static void prettyPrint(SyntaxNode node, String indent) {
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
            prettyPrint(child, indent);
        }
        System.out.print("\u001B[0m");
    }
}
