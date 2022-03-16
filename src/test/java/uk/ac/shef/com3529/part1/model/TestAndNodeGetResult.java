package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.AndNode;
import uk.ac.shef.com3529.assignment.part1.model.IdentifierNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is for testing the getResult function for add node.
 */
public class TestAndNodeGetResult {

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, true, false"})
    public void testAndNodeWithOverrodeResult(boolean leftNodeResult, boolean rightNodeResult, boolean overrodeResult){
        AndNode objUnderTest = new AndNode(new IdentifierNode<>("condition1", leftNodeResult),
                new IdentifierNode<>("condition2", rightNodeResult));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
    public void testAndNodeWithBothIdentifierNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        AndNode objUnderTest = new AndNode(new IdentifierNode<>("condition1", leftNodeResult),
                new IdentifierNode<>("condition2", rightNodeResult));

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
    public void testAndNodeWithOnlyLeftIdentifierNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        AndNode rightNode = new AndNode(new IdentifierNode<>("condition2", true),
                new IdentifierNode<>("condition3", true));
        rightNode.setResult(rightNodeResult);

        AndNode objUnderTest = new AndNode(new IdentifierNode<>("condition1", leftNodeResult), rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
    public void testAndNodeWithOnlyRightIdentifierNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        AndNode leftNode = new AndNode(new IdentifierNode<>("condition1", true),
                new IdentifierNode<>("condition2", true));
        leftNode.setResult(leftNodeResult);

        AndNode objUnderTest = new AndNode(leftNode, new IdentifierNode<>("condition3", rightNodeResult));

        assertEquals(expected, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
    public void testAndNodeWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
        AndNode leftNode = new AndNode(new IdentifierNode<>("condition1", true),
                new IdentifierNode<>("condition2", true));
        AndNode rightNode = new AndNode(new IdentifierNode<>("condition3", true),
                new IdentifierNode<>("condition4", true));
        leftNode.setResult(leftNodeResult);
        rightNode.setResult(rightNodeResult);

        AndNode objUnderTest = new AndNode(leftNode, rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

}
