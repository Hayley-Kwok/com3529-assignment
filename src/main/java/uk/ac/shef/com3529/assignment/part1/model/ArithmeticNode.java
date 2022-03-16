package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;

public class ArithmeticNode extends ConditionNode<ArithmeticRelation>{
    public ArithmeticNode(SyntaxNode leftNode, ArithmeticRelation relation, SyntaxNode rightNode) {
        super(leftNode, relation, rightNode);
    }

    //TODO implement this
    @Override
    public boolean getResult() {
        return false;
    }


}
