package edu.rmnstepaniuk.analysis;

import edu.rmnstepaniuk.analysis.nodes.SyntaxNode;

import java.util.Collections;
import java.util.Iterator;

public class SyntaxToken extends SyntaxNode {
    private final String text;
    private final int position;
    private final SyntaxType type;
    private Object value;

    public SyntaxToken(String text, int position, SyntaxType type) {

        this.text = text;
        this.position = position;
        this.type = type;
    }

    public SyntaxToken(String text, int position, SyntaxType type, Object value) {
        this.text = text;
        this.position = position;
        this.type = type;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    @Override
    public SyntaxType getType() {
        return type;
    }

    @Override
    public Iterator<SyntaxNode> getChildren() {
        return Collections.emptyIterator();
    }

    public Object getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }
}
