package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.OrNode;
import uk.ac.shef.com3529.assignment.part1.model.IdentifierNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrNodeGetResult {
    @ParameterizedTest
    @CsvSource({"true, true, true", "true, true, false"})
    public void testOrNodeWithOverrodeResult(boolean leftNodeResult, boolean rightNodeResult, boolean overrodeResult){
        OrNode objUnderTest = new OrNode(new IdentifierNode<>("condition1", leftNodeResult),
                new IdentifierNode<>("condition2", rightNodeResult));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithBothIdentifierNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        OrNode objUnderTest = new OrNode(new IdentifierNode<>("condition1", leftNodeResult),
                new IdentifierNode<>("condition2", rightNodeResult));

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithOnlyLeftIdentifierNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        OrNode rightNode = new OrNode(new IdentifierNode<>("condition2", true),
                new IdentifierNode<>("condition3", true));
        rightNode.setResult(rightNodeResult);

        OrNode objUnderTest = new OrNode(new IdentifierNode<>("condition1", leftNodeResult), rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithOnlyRightIdentifierNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        OrNode leftNode = new OrNode(new IdentifierNode<>("condition1", true),
                new IdentifierNode<>("condition2", true));
        leftNode.setResult(leftNodeResult);

        OrNode objUnderTest = new OrNode(leftNode, new IdentifierNode<>("condition3", rightNodeResult));

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
    public void testOrNodeWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        OrNode leftNode = new OrNode(new IdentifierNode<>("condition1", true),
                new IdentifierNode<>("condition2", true));
        OrNode rightNode = new OrNode(new IdentifierNode<>("condition3", true),
                new IdentifierNode<>("condition4", true));
        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        OrNode objUnderTest = new OrNode(leftNode, rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }
}
