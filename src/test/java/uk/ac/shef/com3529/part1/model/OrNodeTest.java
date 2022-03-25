package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.ConditionNode;
import uk.ac.shef.com3529.assignment.part1.model.OrNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrNodeTest {

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected) {
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
    @CsvSource({"1, 1, true", "1, 2, false"})
    public void testEquals(int n1c1Val, int n2c1Val, boolean expected) {
        OrNode n1 = new OrNode(new VariableNode<>("condition1", n1c1Val),
                new VariableNode<>("condition2", 1));
        OrNode n2 = new OrNode(new VariableNode<>("condition1", n2c1Val),
                new VariableNode<>("condition2", 1));

        assertEquals(expected, n1.hashCode() == n2.hashCode());
        assertEquals(expected, n1.equals(n2));
    }
}
