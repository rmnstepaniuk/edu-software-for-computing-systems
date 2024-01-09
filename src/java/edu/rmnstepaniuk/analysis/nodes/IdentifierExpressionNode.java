package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Collections;
import java.util.Iterator;

public class IdentifierExpressionNode extends ExpressionNode {
    private final SyntaxToken identifierToken;

    public IdentifierExpressionNode(SyntaxToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    public SyntaxToken getIdentifierToken() {
        return identifierToken;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.IDENTIFIER_EXPRESSION;
    }

    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Collections.singletonList((SyntaxNode) identifierToken).iterator();
    }
}
