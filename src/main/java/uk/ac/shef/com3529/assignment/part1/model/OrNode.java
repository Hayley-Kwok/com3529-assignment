package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.BinaryRelation;

public class OrNode extends BinaryRelatedNode<BinaryRelation>{

    public OrNode(SyntaxNode leftNode, SyntaxNode rightNode) {
        super(leftNode, BinaryRelation.Or, rightNode);
    }

    // Based on the scope of this assignment, it is assumed that the leftNode and rightNode for the OrNode will be BinaryRelatedNode.
    // Since the node are generated by the imaginary parser, it is assumed that the above assumption will always be
    // true and thereby there are no addition check on the casting.
    @Override
    public boolean getResult() {
        return ((BinaryRelatedNode<?>)leftNode).getResult() || ((BinaryRelatedNode<?>)rightNode).getResult();
    }
}
