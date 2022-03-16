package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.BinaryRelation;

import java.util.AbstractMap;

public class AndNode extends BinaryRelatedNode<BinaryRelation>{

    public AndNode(SyntaxNode leftNode, SyntaxNode rightNode) {
        super(leftNode, BinaryRelation.And, rightNode);
    }

    public boolean getResult() {
        if (resultOverrode) {
            return result;
        }
        if (leftNode instanceof IdentifierNode && rightNode instanceof IdentifierNode) {
            return ((IdentifierNode<Boolean>) leftNode).value && ((IdentifierNode<Boolean>) rightNode).value;
        }

        if (leftNode instanceof IdentifierNode) {
            return ((IdentifierNode<Boolean>) leftNode).value && ((BinaryRelatedNode) rightNode).getResult();
        }

        if (rightNode instanceof IdentifierNode) {
            return ((BinaryRelatedNode) leftNode).getResult() && ((IdentifierNode<Boolean>) rightNode).value;
        }

        return ((BinaryRelatedNode)leftNode).getResult() && ((BinaryRelatedNode)rightNode).getResult();
    }
}
