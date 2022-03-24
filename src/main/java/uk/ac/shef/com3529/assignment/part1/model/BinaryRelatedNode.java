package uk.ac.shef.com3529.assignment.part1.model;

import java.util.Objects;

/**
 * This node represents the binary relationship of two syntax nodes.
 */

public abstract class BinaryRelatedNode<T> extends SyntaxNode {
    protected T relation;
    protected boolean result;

    public BinaryRelatedNode(SyntaxNode leftNode, T relation, SyntaxNode rightNode) {
        this.leftNode = leftNode;
        this.relation = relation;
        this.rightNode = rightNode;
    }

    public abstract boolean getResult();

    public T getRelation() {
        return relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        BinaryRelatedNode<?> that = (BinaryRelatedNode<?>) o;
        return result == that.result &&
                relation.equals(that.relation) &&
                leftNode.equals(that.leftNode) &&
                rightNode.equals(that.rightNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, result, leftNode, rightNode);
    }

    @Override
    public String toString() {
        return leftNode.toString() + " " + relation.toString() + " " + rightNode.toString();
    }
}
