package edu.rmnstepaniuk;

// import edu.rmnstepaniuk.analysis.Evaluator;
import edu.rmnstepaniuk.analysis.SyntaxTree;

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
            if (line.equals("")) {
                continue;
            }
            syntaxTree = SyntaxTree.parse(line);

            Printer.printSyntaxTree(syntaxTree.getRoot(), "");

            if (!syntaxTree.getDiagnostics().isEmpty()) {
                for (String diagnosis : syntaxTree.getDiagnostics()) {
                    System.out.println("\u001B[31m" + diagnosis);
                    System.out.print("\u001B[0m");

                }
                continue;
            }
            System.out.println("No errors found");

            Printer.printBinaryTree(syntaxTree.getRoot(), "");
/*
                Evaluator evaluator = new Evaluator(syntaxTree.getRoot());
                try {
                    float result = evaluator.evaluate();
                    System.out.println("= " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
 */
        }
    }
}
