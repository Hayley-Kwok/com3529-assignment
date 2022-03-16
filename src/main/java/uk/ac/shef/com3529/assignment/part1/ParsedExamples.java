package uk.ac.shef.com3529.assignment.part1;

import uk.ac.shef.com3529.assignment.part1.model.*;
import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

public class ParsedExamples {

    /*
        Return the root node for the following branch predicate from week 3 practical sheet
            if (side1 + side2 >= side3 &&
                (side1 == side2 || side2 == side3) &&
                (side1 != side2 || side2 != side3))
     */
    public static SyntaxNode getTrianglePractical() {
        SyntaxNode side1 = new IdentifierNode<Integer>("side1");
        SyntaxNode side2 = new IdentifierNode<Integer>("side2");
        SyntaxNode side3 = new IdentifierNode<Integer>("side3");

        SyntaxNode c1 = new ComparisonNode
                (new ArithmeticNode(side1, ArithmeticRelation.Add, side2),
                    ComparisonRelation.LargerThan,
                    side3);

        SyntaxNode c2 = new ComparisonNode(side1, ComparisonRelation.EqualsEquals, side2);
        SyntaxNode c3 = new ComparisonNode(side2, ComparisonRelation.EqualsEquals, side3);
        SyntaxNode c4 = new ComparisonNode(side1, ComparisonRelation.NotEquals, side2);
        SyntaxNode c5 = new ComparisonNode(side2, ComparisonRelation.NotEquals, side3);

        SyntaxNode conjunct2 = new OrNode(c2, c3);
        SyntaxNode conjunct3 = new OrNode(c4, c5);
        SyntaxNode root = new AndNode(new AndNode(c1, conjunct2), conjunct3);
        return root;
    }

}
