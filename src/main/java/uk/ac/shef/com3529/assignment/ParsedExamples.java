package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.model.*;
import uk.ac.shef.com3529.assignment.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

public class ParsedExamples {

    private boolean test(int side1, int side2, int side3) {
        return (side1 + side2 >= side3 &&
                (side1 == side2 || side2 == side3) &&
                (side1 != side2 || side2 != side3));
    }

    /**
     * Return the root node for the following branch predicate from week 3 practical sheet
     * if (side1 + side2 >= side3 &&
     * (side1 == side2 || side2 == side3) &&
     * (side1 != side2 || side2 != side3))
     */
    public static BinaryRelatedNode<?> getTrianglePractical() {
        SyntaxNode side1 = new VariableNode<>(Integer.class, "side1", 1, 20);
        SyntaxNode side2 = new VariableNode<>(Integer.class, "side2", 1, 20);
        SyntaxNode side3 = new VariableNode<>(Integer.class, "side3", 1, 20);

        SyntaxNode c1 = new ConditionNode
                (new ArithmeticNode(side1, ArithmeticRelation.Add, side2),
                        ComparisonRelation.LargerThan,
                        side3);

        SyntaxNode c2 = new ConditionNode(side1, ComparisonRelation.EqualsEquals, side2);
        SyntaxNode c3 = new ConditionNode(side2, ComparisonRelation.EqualsEquals, side3);
        SyntaxNode c4 = new ConditionNode(side1, ComparisonRelation.NotEquals, side2);
        SyntaxNode c5 = new ConditionNode(side2, ComparisonRelation.NotEquals, side3);

        SyntaxNode conjunct2 = new OrNode(c2, c3);
        SyntaxNode conjunct3 = new OrNode(c4, c5);
        return new AndNode(new AndNode(c1, conjunct2), conjunct3);
    }

}
