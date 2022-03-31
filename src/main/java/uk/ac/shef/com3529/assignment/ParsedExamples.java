package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.model.*;
import uk.ac.shef.com3529.assignment.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

public class ParsedExamples {

    /**
     * Return the root node for the following branch predicate from week 3 practical sheet (Isosceles Example)
     * (((side1 + side2 > side3) && ((side1 == side2) || (side2 == side3))) && ((side1 != side2) || (side2 != side3)))
     */
    public static BinaryRelatedNode<?> getIsoscelesPractical() {
        SyntaxNode side1 = new VariableNode<>(Integer.class, "side1", 1, 10);
        SyntaxNode side2 = new VariableNode<>(Integer.class, "side2", 1, 10);
        SyntaxNode side3 = new VariableNode<>(Integer.class, "side3", 1, 10);

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

    /**
     * (side1 + side2 <= side3)
     *
     * @return
     */
    public static BinaryRelatedNode<?> getInvalidBranch() {
        SyntaxNode side1 = new VariableNode<>(Integer.class, "side1", 1, 10);
        SyntaxNode side2 = new VariableNode<>(Integer.class, "side2", 1, 10);
        SyntaxNode side3 = new VariableNode<>(Integer.class, "side3", 1, 10);

        ConditionNode c1 = new ConditionNode
                (new ArithmeticNode(side1, ArithmeticRelation.Add, side2),
                        ComparisonRelation.SmallerOrEqualsTo,
                        side3);
        return c1;
    }

    /**
     * Scalene Case for Triangle
     * (((side1 + side2 > side3) && !(side1 == side2)) && (!(side2 == side3) && !(side1 == side3)))
     *
     * @return node for this branch predicate
     */
    public static BinaryRelatedNode<?> getScaleneBranch() {
        SyntaxNode side1 = new VariableNode<>(Integer.class, "side1", 1, 10);
        SyntaxNode side2 = new VariableNode<>(Integer.class, "side2", 1, 10);
        SyntaxNode side3 = new VariableNode<>(Integer.class, "side3", 1, 10);

        SyntaxNode c1 = new ConditionNode
                (new ArithmeticNode(side1, ArithmeticRelation.Add, side2),
                        ComparisonRelation.LargerThan,
                        side3);
        SyntaxNode c2 = new ConditionNode(side1, ComparisonRelation.EqualsEquals, side2, true);
        SyntaxNode c3 = new ConditionNode(side2, ComparisonRelation.EqualsEquals, side3, true);
        SyntaxNode c4 = new ConditionNode(side1, ComparisonRelation.EqualsEquals, side3, true);
        return new AndNode(new AndNode(c1, c2), new AndNode(c3, c4));
    }

    public static boolean test(int side1, int side2, int side3) {
        return side1 + side2 > side3 && !(side1 == side2) && !(side2 == side3) && !(side1 == side3);
    }

    /**
     * Equilateral Case for Triangle
     * (((side1 + side2 > side3) && (side1 == side2)) && (side2 == side3))
     *
     * @return
     */
    public static BinaryRelatedNode<?> getEquilateralBranch() {

        SyntaxNode side1 = new VariableNode<>(Integer.class, "side1", 1, 10);
        SyntaxNode side2 = new VariableNode<>(Integer.class, "side2", 1, 10);
        SyntaxNode side3 = new VariableNode<>(Integer.class, "side3", 1, 10);

        SyntaxNode c1 = new ConditionNode
                (new ArithmeticNode(side1, ArithmeticRelation.Add, side2),
                        ComparisonRelation.LargerThan,
                        side3);
        SyntaxNode c2 = new ConditionNode(side1, ComparisonRelation.EqualsEquals, side2);
        SyntaxNode c3 = new ConditionNode(side2, ComparisonRelation.EqualsEquals, side3);
        return new AndNode(new AndNode(c1, c2), c3);
    }

}
