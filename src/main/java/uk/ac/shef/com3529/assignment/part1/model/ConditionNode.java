package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

public class ConditionNode extends BinaryRelatedNode<ComparisonRelation> {
    public ConditionNode(SyntaxNode leftNode, ComparisonRelation relation, SyntaxNode rightNode) {
        super(leftNode, relation, rightNode);
    }

    //TODO implement this function
    @Override
    public boolean getResult() {
        return false;
    }
}
