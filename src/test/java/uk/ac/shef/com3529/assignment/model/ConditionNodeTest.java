package uk.ac.shef.com3529.assignment.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

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
    @CsvSource({"false, true", "true, false"})
    public void testGetResultNegation(boolean negation, boolean expected) {
        VariableNode<Integer> leftNode = new VariableNode<>("v1", 7);
        VariableNode<Integer> rightNode = new VariableNode<>("v2", 6);

        ConditionNode objUnderTest = new ConditionNode(leftNode, ComparisonRelation.LargerThan, rightNode, negation);

        assertEquals(expected, objUnderTest.getResult());
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

    @ParameterizedTest
    @CsvSource({"true, true, EqualsEquals, EqualsEquals, true, true, true",
            "true, false, EqualsEquals, EqualsEquals, true, true, false,",
            "true, true, EqualsEquals, NotEquals, true, true, false,",
            "true, true, EqualsEquals, EqualsEquals, true, false, false"})
    public void testEquals(boolean n1Result, boolean n2Result, String n1relation, String n2relation, boolean n1Negation,
                           boolean n2Negation, boolean expected) {
        ConditionNode n1 = new ConditionNode(
                new VariableNode<>("condition1", 1),
                ComparisonRelation.valueOf(n1relation),
                new VariableNode<>("condition2", 1),
                n1Negation);

        ConditionNode n2 = new ConditionNode(
                new VariableNode<>("condition1", 1),
                ComparisonRelation.valueOf(n2relation),
                new VariableNode<>("condition2", 1),
                n2Negation);

        n1.setResult(n1Result);
        n2.setResult(n2Result);

        assertEquals(expected, n1.hashCode() == n2.hashCode());
        assertEquals(expected, n1.equals(n2));
    }
}
