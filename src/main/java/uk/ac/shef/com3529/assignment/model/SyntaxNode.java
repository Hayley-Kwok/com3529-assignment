package uk.ac.shef.com3529.assignment.model;

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