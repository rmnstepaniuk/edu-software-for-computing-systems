package edu.rmnstepaniuk.analysis.nodes;

import edu.rmnstepaniuk.analysis.SyntaxToken;
import edu.rmnstepaniuk.analysis.SyntaxType;

import java.util.Collections;
import java.util.Iterator;

public final class LiteralExpressionNode extends ExpressionNode {
    private final SyntaxToken literalToken;

    public LiteralExpressionNode(SyntaxToken literalToken) {
        this.literalToken = literalToken;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.LITERAL_EXPRESSION;
    }

    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Collections.singletonList((SyntaxNode) literalToken).iterator();
    }

    public SyntaxToken getLiteralToken() {
        return literalToken;
    }
}
