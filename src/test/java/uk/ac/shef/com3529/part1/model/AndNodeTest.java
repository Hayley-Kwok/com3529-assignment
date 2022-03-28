package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.AndNode;
import uk.ac.shef.com3529.assignment.part1.model.ConditionNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is for testing And node.
 */
public class AndNodeTest {

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
    public void testGetResultWithNoNegation(boolean leftNodeResult, boolean rightNodeResult, boolean expected) {
        ConditionNode leftNode = new ConditionNode(new VariableNode<>("condition1", 1),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("condition2", 1));
        ConditionNode rightNode = new ConditionNode(new VariableNode<>("condition3", 1),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("condition4", 1));
        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        AndNode objUnderTest = new AndNode(leftNode, rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, false, true", "true, true, true, false",})
    public void testGetResultWithNegation(boolean leftNodeResult, boolean rightNodeResult,
                                          boolean negation, boolean expected) {
        ConditionNode leftNode = new ConditionNode(new VariableNode<>("condition1", 1),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("condition2", 1));
        ConditionNode rightNode = new ConditionNode(new VariableNode<>("condition3", 1),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("condition4", 1));
        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        AndNode objUnderTest = new AndNode(leftNode, rightNode, negation);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"2, 2, true, true, true",
            "1, 2, true, true, false",
            "2, 2, false, true, false"})
    public void testEquals(int n1c1Val, int n2c1Val, boolean n1Negated, boolean n2Negated, boolean expected) {
        AndNode n1 = new AndNode(new VariableNode<>("condition1", n1c1Val),
                new VariableNode<>("condition2", 1), n1Negated);
        AndNode n2 = new AndNode(new VariableNode<>("condition1", n2c1Val),
                new VariableNode<>("condition2", 1), n2Negated);

        assertEquals(expected, n1.hashCode() == n2.hashCode());
        assertEquals(expected, n1.equals(n2));
    }
}
