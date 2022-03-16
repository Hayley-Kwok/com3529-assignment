package uk.ac.shef.com3529.assignment.part1.model;

/**
 * Base of all nodes
 */
public abstract class SyntaxNode {
    protected SyntaxNode leftNode = null;
    protected SyntaxNode rightNode = null;

    public SyntaxNode getLeftNode() {
        return leftNode;
    }

    public SyntaxNode getRightNode() {
        return rightNode;
    }
}