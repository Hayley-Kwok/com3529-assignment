package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.OrNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrNodeTest {
    @ParameterizedTest
    @CsvSource({"true, true, true", "true, true, false"})
    public void testGetResultWithOverrodeResult(boolean leftNodeResult, boolean rightNodeResult, boolean overrodeResult){
        OrNode objUnderTest = new OrNode(new VariableNode<>("condition1", leftNodeResult),
                new VariableNode<>("condition2", rightNodeResult));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    //TODO come back to this when finished implementing conditionNodes
//    @ParameterizedTest
//    @CsvSource({"true, true, true", "true, false, true", "false, true, true", "false, false, false"})
//    public void testOrNodeWithBothBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult, boolean expected){
//        OrNode leftNode = new OrNode(new VariableNode<>("condition1", true),
//                new VariableNode<>("condition2", true));
//        OrNode rightNode = new OrNode(new VariableNode<>("condition3", true),
//                new VariableNode<>("condition4", true));
//        leftNode.setResult(leftNodeResult);
//        rightNode.setResult(rightNodeResult);
//
//        OrNode objUnderTest = new OrNode(leftNode, rightNode);
//
//        assertEquals(expected, objUnderTest.getResult());
//    }
}
