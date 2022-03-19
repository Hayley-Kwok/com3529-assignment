package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.OrNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrNodeTest {
    @ParameterizedTest
    @CsvSource({"1, 1, true", "1, 1, false"})
    public void testGetResultWithOverrodeResult(int leftNodeValue, int rightNodeValue, boolean overrodeResult) {
        OrNode objUnderTest = new OrNode(new VariableNode<>("condition1", leftNodeValue),
                new VariableNode<>("condition2", rightNodeValue));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected) {
        OrNode leftNode = new OrNode(new VariableNode<>("v1", 1),
                new VariableNode<>("v2", 1));
        OrNode rightNode = new OrNode(new VariableNode<>("v3", 2),
                new VariableNode<>("v4", 2));

        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        OrNode objUnderTest = new OrNode(leftNode, rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false"})
    public void testEquals(boolean n1Result, boolean n2Result, boolean expected) {
        OrNode n1 = new OrNode(new VariableNode<>("condition1", 1),
                new VariableNode<>("condition2", 1));
        OrNode n2 = new OrNode(new VariableNode<>("condition1", 1),
                new VariableNode<>("condition2", 1));

        n1.setResult(n1Result);
        n2.setResult(n2Result);

        assertEquals(expected, n1.equals(n2));
    }
}
