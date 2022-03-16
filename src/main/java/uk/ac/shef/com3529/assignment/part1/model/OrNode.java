package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.BinaryRelation;

public class OrNode extends BinaryRelatedNode<BinaryRelation>{

    public OrNode(SyntaxNode leftNode, SyntaxNode rightNode) {
        super(leftNode, BinaryRelation.Or, rightNode);
    }

    @Override
    public boolean getResult() {
        if (resultOverrode) {
            return result;
        }
        if (leftNode instanceof IdentifierNode && rightNode instanceof IdentifierNode) {
            return ((IdentifierNode<Boolean>) leftNode).value || ((IdentifierNode<Boolean>) rightNode).value;
        }

        if (leftNode instanceof IdentifierNode) {
            return ((IdentifierNode<Boolean>) leftNode).value || ((BinaryRelatedNode) rightNode).getResult();
        }

        if (rightNode instanceof IdentifierNode) {
            return ((BinaryRelatedNode) leftNode).getResult() || ((IdentifierNode<Boolean>) rightNode).value;
        }

        return ((BinaryRelatedNode)leftNode).getResult() || ((BinaryRelatedNode)rightNode).getResult();
    }
}
