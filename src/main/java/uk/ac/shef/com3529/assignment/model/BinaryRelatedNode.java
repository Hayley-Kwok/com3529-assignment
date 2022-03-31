package uk.ac.shef.com3529.assignment.model;

import java.util.Objects;

/**
 * This node represents the binary relationship of two syntax nodes.
 */

public abstract class BinaryRelatedNode<T> extends SyntaxNode {
    protected T relation;
    protected boolean negated;

    public BinaryRelatedNode(SyntaxNode leftNode, T relation, SyntaxNode rightNode) {
        this.leftNode = leftNode;
        this.relation = relation;
        this.rightNode = rightNode;
    }

    public BinaryRelatedNode(SyntaxNode leftNode, T relation, SyntaxNode rightNode, boolean negated) {
        this(leftNode, relation, rightNode);
        this.negated = negated;
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
        return negated == that.negated &&
                relation.equals(that.relation) &&
                leftNode.equals(that.leftNode) &&
                rightNode.equals(that.rightNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, negated, leftNode, rightNode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (negated) {
            sb.append("!");
        }

        sb.append("(")
                .append(leftNode.toString()).append(" ")
                .append(relation.toString()).append(" ")
                .append(rightNode.toString()).append(")");

        return sb.toString();
    }

    protected boolean flipResult(boolean result) {
        if (negated) {
            return !result;
        }
        return result;
    }

    public boolean isNegated() {
        return negated;
    }
}
