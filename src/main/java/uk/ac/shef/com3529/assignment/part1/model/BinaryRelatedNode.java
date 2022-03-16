package uk.ac.shef.com3529.assignment.part1.model;

import java.util.AbstractMap;

/**
    This node represents the binary relationship of two syntax nodes.
 */

public abstract class BinaryRelatedNode<T> extends SyntaxNode {
    protected T relation;
    protected boolean result;
    protected boolean resultOverrode;

    public BinaryRelatedNode(SyntaxNode leftNode,T relation, SyntaxNode rightNode){
        this.leftNode = leftNode;
        this.relation = relation;
        this.rightNode = rightNode;
    }

    public abstract boolean getResult();

    public void setResult(boolean result) {
        this.resultOverrode = true;
        this.result = result;
    }
}
