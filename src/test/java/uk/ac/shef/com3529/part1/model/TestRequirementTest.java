package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.api.Test;
import uk.ac.shef.com3529.assignment.part1.TestRequirements;
import uk.ac.shef.com3529.assignment.part1.model.*;
import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRequirementTest {

    @Test
    public void testGetVariables() {
        //The predicate is (v1 == v2) && (v3 == v4)
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");
        VariableNode<?> v3 = new VariableNode<Long>("v3");
        VariableNode<?> v4 = new VariableNode<Float>("v4");
        ConditionNode leftNode = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode rightNode = new ConditionNode(v3,ComparisonRelation.EqualsEquals, v4);

        HashSet<VariableNode<?>>expectedVariables = new HashSet<>(Arrays.asList(v1,v2,v3,v4));

        TestRequirements objUnderTest = new TestRequirements(new AndNode(leftNode, rightNode));

        HashSet<?>actualVariables = objUnderTest.getVariables();
        assertEquals(expectedVariables, actualVariables);
    }

    @Test
    public void testGetConditions() {
        //The predicate is (v1 == v2) && (v3 >= (v4 + v5))
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");
        VariableNode<?> v3 = new VariableNode<Long>("v3");
        VariableNode<?> v4 = new VariableNode<Float>("v4");
        VariableNode<?> v5 = new VariableNode<Float>("v5");
        ConditionNode leftNode = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ArithmeticNode<Integer> arithmeticNode = new ArithmeticNode<>(v4, ArithmeticRelation.Add, v5);
        ConditionNode rightNode = new ConditionNode(v3, ComparisonRelation.LargerOrEqualsTo, arithmeticNode);

        HashSet<ConditionNode> expectedConditions = new HashSet<>(Arrays.asList(leftNode, rightNode));

        TestRequirements objUnderTest = new TestRequirements(new AndNode(leftNode, rightNode));

        HashSet<ConditionNode> actualConditions = objUnderTest.getConditions();
        assertEquals(expectedConditions, actualConditions);
    }
}
