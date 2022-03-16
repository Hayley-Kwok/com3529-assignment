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
        if (leftNode instanceof VariableNode && rightNode instanceof VariableNode) {
            return ((VariableNode<Boolean>) leftNode).value || ((VariableNode<Boolean>) rightNode).value;
        }

        if (leftNode instanceof VariableNode) {
            return ((VariableNode<Boolean>) leftNode).value || ((BinaryRelatedNode) rightNode).getResult();
        }

        if (rightNode instanceof VariableNode) {
            return ((BinaryRelatedNode) leftNode).getResult() || ((VariableNode<Boolean>) rightNode).value;
        }

        return ((BinaryRelatedNode)leftNode).getResult() || ((BinaryRelatedNode)rightNode).getResult();
    }
}
