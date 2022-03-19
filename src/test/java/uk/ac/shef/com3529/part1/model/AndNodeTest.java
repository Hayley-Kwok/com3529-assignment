package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.AndNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is for testing And node.
 */
public class AndNodeTest {

    @ParameterizedTest
    @CsvSource({"1, 2, true", "1, 2, false"})
    public void testGetResultWithOverrodeResult(double leftNodeVal, double rightNodeVal, boolean overrodeResult) {
        AndNode objUnderTest = new AndNode(new VariableNode<>("condition1", leftNodeVal),
                new VariableNode<>("condition2", rightNodeVal));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
    public void testGetResultWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected) {
        AndNode leftNode = new AndNode(new VariableNode<>("condition1", 1),
                new VariableNode<>("condition2", 1));
        AndNode rightNode = new AndNode(new VariableNode<>("condition3", 1),
                new VariableNode<>("condition4", 1));
        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        AndNode objUnderTest = new AndNode(leftNode, rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false"})
    public void testEquals(boolean n1Result, boolean n2Result, boolean expected) {
        AndNode n1 = new AndNode(new VariableNode<>("condition1", 1),
                new VariableNode<>("condition2", 1));
        AndNode n2 = new AndNode(new VariableNode<>("condition1", 1),
                new VariableNode<>("condition2", 1));

        n1.setResult(n1Result);
        n2.setResult(n2Result);

        assertEquals(expected, n1.equals(n2));
    }
}
