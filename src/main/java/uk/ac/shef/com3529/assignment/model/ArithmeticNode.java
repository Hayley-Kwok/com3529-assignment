package uk.ac.shef.com3529.assignment.model;

import uk.ac.shef.com3529.assignment.model.enums.ArithmeticRelation;

import java.util.Objects;

public class ArithmeticNode extends SyntaxNode {
    private final ArithmeticRelation relation;

    public ArithmeticNode(SyntaxNode leftNode, ArithmeticRelation relation, SyntaxNode rightNode) {
        this.leftNode = leftNode;
        this.relation = relation;
        this.rightNode = rightNode;
    }

    public double getResult() {
        // It is known that the program will face different numerical values. In order to perform the arithmetic operations,
        // the value have to be casted to one of the concrete type of Number. So, in here, the value is being casted to
        // be double which can represent most of the value represents by different Number type of java.
        double leftNodeValue = ((VariableNode<?>) leftNode).getValue().doubleValue();
        double rightNodeValue = ((VariableNode<?>) rightNode).getValue().doubleValue();

        switch (relation) {
            case Add:
                return leftNodeValue + rightNodeValue;
            case Minus:
                return leftNodeValue - rightNodeValue;
            case Multiply:
                return leftNodeValue * rightNodeValue;
            case Divide:
                return leftNodeValue / rightNodeValue;
        }
        throw new ArithmeticException("Tried to do arithmetic operation other than +,-,*,/ in the Arithmetic Node");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArithmeticNode)) return false;
        ArithmeticNode that = (ArithmeticNode) o;
        return relation == that.relation &&
                leftNode.equals(that.leftNode) &&
                rightNode.equals(that.rightNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, leftNode, rightNode);
    }

    @Override
    public String toString() {
        return leftNode.toString() + " " + relation.toString() + " " + rightNode.toString();
    }
}
