package uk.ac.shef.com3529.assignment.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrNodeTest {

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithoutNegation(boolean leftNodeResult, boolean rightNodeResult, boolean expected) {
        ConditionNode leftNode = new ConditionNode(new VariableNode<>("v1", 1),
                ComparisonRelation.LargerThan,
                new VariableNode<>("v2", 1));
        ConditionNode rightNode = new ConditionNode(new VariableNode<>("v3", 2),
                ComparisonRelation.LargerThan,
                new VariableNode<>("v4", 2));

        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        OrNode objUnderTest = new OrNode(leftNode, rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, false, false, true", "true, false, true, false"})
    public void testOrNodeWithNegation(boolean leftNodeResult, boolean rightNodeResult, boolean negation, boolean expected) {
        ConditionNode leftNode = new ConditionNode(new VariableNode<>("v1", 1),
                ComparisonRelation.LargerThan,
                new VariableNode<>("v2", 1));
        ConditionNode rightNode = new ConditionNode(new VariableNode<>("v3", 2),
                ComparisonRelation.LargerThan,
                new VariableNode<>("v4", 2));

        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        OrNode objUnderTest = new OrNode(leftNode, rightNode, negation);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"1, 1, true, true, true",
            "1, 2, true, true, false",
            "1, 1, true, false, false"})
    public void testEquals(int n1c1Val, int n2c1Val, boolean n1Negation, boolean n2Negation, boolean expected) {
        OrNode n1 = new OrNode(new VariableNode<>("condition1", n1c1Val),
                new VariableNode<>("condition2", 1), n1Negation);
        OrNode n2 = new OrNode(new VariableNode<>("condition1", n2c1Val),
                new VariableNode<>("condition2", 1), n2Negation);

        assertEquals(expected, n1.hashCode() == n2.hashCode());
        assertEquals(expected, n1.equals(n2));
    }
}
