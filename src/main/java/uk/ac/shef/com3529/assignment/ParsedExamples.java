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
     * Invalid Case for Triangle
     * (side1 + side2 <= side3)
     *
     * @return node for this branch predicate
     */
    public static BinaryRelatedNode<?> getInvalidBranch() {
        SyntaxNode side1 = new VariableNode<>(Integer.class, "side1", 1, 10);
        SyntaxNode side2 = new VariableNode<>(Integer.class, "side2", 1, 10);
        SyntaxNode side3 = new VariableNode<>(Integer.class, "side3", 1, 10);

        return new ConditionNode
                (new ArithmeticNode(side1, ArithmeticRelation.Add, side2),
                        ComparisonRelation.SmallerOrEqualsTo,
                        side3);
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
     * @return node for this branch predicate
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

    /**
     * year2 < year1 || (year2 == year1 && month2 < month1) || (year2 == year1 && month2 == month1 && day2 < day1)
     */
    public static BinaryRelatedNode<?> getDaysBetweenTwoDates() {
        SyntaxNode year1 = new VariableNode<>(Integer.class, "year1", 1, 3000);
        SyntaxNode year2 = new VariableNode<>(Integer.class, "year2", 1, 3000);
        SyntaxNode month1 = new VariableNode<>(Integer.class, "month1", 1, 13);
        SyntaxNode month2 = new VariableNode<>(Integer.class, "month2", 1, 13);
        SyntaxNode day1 = new VariableNode<>(Integer.class, "day1", 1, 32);
        SyntaxNode day2 = new VariableNode<>(Integer.class, "day2", 1, 32);

        SyntaxNode c1 = new ConditionNode(year2, ComparisonRelation.SmallerThan, year1);
        SyntaxNode c2 = new ConditionNode(year2, ComparisonRelation.EqualsEquals, year1);
        SyntaxNode c3 = new ConditionNode(month2, ComparisonRelation.SmallerThan, month1);
        SyntaxNode c4 = new ConditionNode(month2, ComparisonRelation.EqualsEquals, month1);
        SyntaxNode c5 = new ConditionNode(day2, ComparisonRelation.SmallerThan, day1);

        AndNode d2 = new AndNode(c2, c3);
        AndNode d3 = new AndNode(c2, new AndNode(c4, c5));

        return new OrNode(c1, new OrNode(d2, d3));
    }

    /**
     * (((a == b) && (b == c)) || ((a != b) && (b == c)))
     */
    public static BinaryRelatedNode<?> getRestrictedNotCoveringAllMajors() {
        SyntaxNode a = new VariableNode<>(Double.class, "a", 0.0, 20.0);
        SyntaxNode b = new VariableNode<>(Double.class, "b", 0.0, 20.0);
        SyntaxNode c = new VariableNode<>(Double.class, "c", 0.0, 20.0);

        SyntaxNode c1 = new ConditionNode(a, ComparisonRelation.EqualsEquals, b);
        SyntaxNode c2 = new ConditionNode(b, ComparisonRelation.EqualsEquals, c);
        SyntaxNode c3 = new ConditionNode(a, ComparisonRelation.NotEquals, b);

        return new OrNode(new AndNode(c1, c2), new AndNode(c3, c2));
    }


}
