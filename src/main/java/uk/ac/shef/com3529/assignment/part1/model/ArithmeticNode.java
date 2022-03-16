package uk.ac.shef.com3529.assignment.part1.model;

import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;

public class ArithmeticNode<T extends Number> extends SyntaxNode {
    protected ArithmeticRelation relation;
    protected T result;

    public ArithmeticNode(SyntaxNode leftNode, ArithmeticRelation relation, SyntaxNode rightNode) {
        this.leftNode = leftNode;
        this.relation = relation;
        this.rightNode = rightNode;
    }

    //TODO implement this
    public T getResult() {
        return result;
    }


}
