package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.BinaryRelation;

public class AndNode extends BinaryRelatedNode<BinaryRelation>{

    public AndNode(SyntaxNode leftNode, SyntaxNode rightNode) {
        super(leftNode, BinaryRelation.And, rightNode);
    }

    // Based on the scope of this assignment, it is assumed that the leftNode and rightNode for the AndNode will be BinaryRelatedNode.
    // Since the node are generated by the imaginary parser, it is assumed that the above assumption will always be
    // true and thereby there are no addition check on the casting.
    public boolean getResult() {
        return ((BinaryRelatedNode<?>)leftNode).getResult() && ((BinaryRelatedNode<?>)rightNode).getResult();
    }
}
