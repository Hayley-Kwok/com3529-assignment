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
    @CsvSource({"true, true, true", "true, true, false"})
    public void testGetResultWithOverrodeResult(boolean leftNodeResult, boolean rightNodeResult, boolean overrodeResult){
        AndNode objUnderTest = new AndNode(new VariableNode<>("condition1", leftNodeResult),
                new VariableNode<>("condition2", rightNodeResult));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    //TODO come back to this when finished implementing conditionNodes
//    @ParameterizedTest
//    @CsvSource({"true, true, true", "true, false, false", "false, true, false", "false, false, false"})
//    public void testGetResultWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
//        AndNode leftNode = new AndNode(new VariableNode<>("condition1", true),
//                new VariableNode<>("condition2", true));
//        AndNode rightNode = new AndNode(new VariableNode<>("condition3", true),
//                new VariableNode<>("condition4", true));
//        leftNode.setResult(leftNodeResult);
//        rightNode.setResult(rightNodeResult);
//
//        AndNode objUnderTest = new AndNode(leftNode, rightNode);
//
//        assertEquals(expected, objUnderTest.getResult());
//    }

}
