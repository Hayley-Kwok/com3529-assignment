package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.ArithmeticNode;
import uk.ac.shef.com3529.assignment.part1.model.ConditionNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConditionNodeTest {

    @ParameterizedTest
    @CsvSource({"1, 2, true", "1, 1, false"})
    public void testGetResultWithOverrodeResult(float v1Val, float v2Val, boolean overrodeResult) {
        ConditionNode objUnderTest = new ConditionNode(
                new VariableNode<>("v1", v1Val),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("v2", v2Val));

        objUnderTest.setResult(overrodeResult);

        assertEquals(overrodeResult, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"true, true, EqualsEquals, true", "true, false, EqualsEquals, false",
            "true, false, NotEquals, true", "false, false, NotEquals, false"})
    public void testGetResultWithBinaryRelatedNode(boolean leftNodeResult, boolean rightNodeResult,
                                                   String relation, boolean expected) {
        ConditionNode leftNode = new ConditionNode(
                new VariableNode<>("v1", -3),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("v2", 2));
        leftNode.setResult(leftNodeResult);

        ConditionNode rightNode = new ConditionNode(
                new VariableNode<>("v1", -3),
                ComparisonRelation.EqualsEquals,
                new VariableNode<>("v2", 2));
        rightNode.setResult(rightNodeResult);

        ConditionNode objUnderTest = new ConditionNode(leftNode, ComparisonRelation.valueOf(relation), rightNode);

        assertEquals(expected, objUnderTest.getResult());
    }

    @Test
    public void testGetResultInternalCastingBothArithmeticNode() {
        ArithmeticNode leftNode = new ArithmeticNode(
                new VariableNode<>("v1", 3.9),
                ArithmeticRelation.Add,
                new VariableNode<>("v2", 4));

        ArithmeticNode rightNode = new ArithmeticNode(
                new VariableNode<>("v3", 3.9),
                ArithmeticRelation.Add,
                new VariableNode<>("v4", 4.0));

        ConditionNode objUnderTest = new ConditionNode(leftNode, ComparisonRelation.EqualsEquals, rightNode);

        assertTrue(objUnderTest.getResult());
    }

    @Test
    public void testGetResultInternalCastingOnlyLeftArithmeticNode() {
        ArithmeticNode leftNode = new ArithmeticNode(
                new VariableNode<>("v1", 3.9),
                ArithmeticRelation.Add,
                new VariableNode<>("v2", 4));

        VariableNode<Double> rightNode = new VariableNode<>("v3", 7.9);

        ConditionNode objUnderTest = new ConditionNode(leftNode, ComparisonRelation.EqualsEquals, rightNode);

        assertTrue(objUnderTest.getResult());
    }

    @Test
    public void testGetResultInternalCastingOnlyRightArithmeticNode() {
        VariableNode<Double> leftNode = new VariableNode<>("v3", 7.9);

        ArithmeticNode rightNode = new ArithmeticNode(
                new VariableNode<>("v1", 3.9),
                ArithmeticRelation.Add,
                new VariableNode<>("v2", 4));

        ConditionNode objUnderTest = new ConditionNode(leftNode, ComparisonRelation.EqualsEquals, rightNode);

        assertTrue(objUnderTest.getResult());
    }

    @Test
    public void testGetResultInternalCastingBothVariableNode() {
        VariableNode<Double> leftNode = new VariableNode<>("v1", 7.0);
        VariableNode<Integer> rightNode = new VariableNode<>("v2", 7);

        ConditionNode objUnderTest = new ConditionNode(leftNode, ComparisonRelation.EqualsEquals, rightNode);

        assertTrue(objUnderTest.getResult());
    }


    @ParameterizedTest
    @CsvSource({"1, EqualsEquals, 1, true", "1, EqualsEquals, 2, false",
            "1, NotEquals, 2, true", "1, NotEquals, 1, false",
            "3, LargerThan, 1, true", "1, LargerThan, 2, false",
            "1, LargerOrEqualsTo, 1, true", "1, LargerOrEqualsTo, 2, false",
            "-1, SmallerThan, 1, true", "1, SmallerThan, 1, false",
            "-3, SmallerOrEqualsTo, -3, true", "3, SmallerOrEqualsTo, 2, false"})
    public void testGetResultSwitchRelations(int v1Val, String relation, int v2Val, boolean expected) {
        ConditionNode objUnderTest = new ConditionNode(
                new VariableNode<>("v1", v1Val),
                ComparisonRelation.valueOf(relation),
                new VariableNode<>("v2", v2Val));

        assertEquals(expected, objUnderTest.getResult());

    }
}
