package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Iterator;

public abstract class SyntaxNode {
    public abstract SyntaxType getType();

    public abstract Iterator<SyntaxNode> getChildren();
}
