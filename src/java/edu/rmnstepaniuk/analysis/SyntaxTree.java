package edu.rmnstepaniuk.analysis;

import edu.rmnstepaniuk.analysis.nodes.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

public final class SyntaxTree {
    private final List<String> diagnostics;
    private final ExpressionNode root;
    private final SyntaxToken endOfFileToken;

    public SyntaxTree(List<String> diagnostics, ExpressionNode root, SyntaxToken endOfFileToken) {
        this.diagnostics = diagnostics;
        this.root = root;
        this.endOfFileToken = endOfFileToken;
    }

    public static SyntaxTree parse(String text) {
        Parser parser = new Parser(text);
        return parser.parse();
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    public ExpressionNode getRoot() {
        return root;
    }

    public SyntaxToken getEndOfFileToken() {
        return endOfFileToken;
    }
}
