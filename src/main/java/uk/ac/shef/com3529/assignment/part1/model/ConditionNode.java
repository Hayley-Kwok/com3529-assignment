package uk.ac.shef.com3529.assignment.part1.model;

public abstract class ConditionNode<T> extends BinaryRelatedNode<T>{
    public ConditionNode(SyntaxNode leftNode, T relation, SyntaxNode rightNode) {
        super(leftNode, relation, rightNode);
    }
}
