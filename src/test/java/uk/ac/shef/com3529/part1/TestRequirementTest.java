package uk.ac.shef.com3529.part1;

import org.junit.jupiter.api.Test;
import uk.ac.shef.com3529.assignment.part1.TestRequirements;
import uk.ac.shef.com3529.assignment.part1.model.*;
import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRequirementTest {

    @Test
    public void testGetVariables() {
        //The predicate is (v1 == v2) && (v3 == v4)
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");
        VariableNode<?> v3 = new VariableNode<Long>("v3");
        VariableNode<?> v4 = new VariableNode<Float>("v4");
        ConditionNode leftNode = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode rightNode = new ConditionNode(v3, ComparisonRelation.EqualsEquals, v4);

        HashSet<VariableNode<?>> expectedVariables = new HashSet<>(Arrays.asList(v1, v2, v3, v4));

        TestRequirements objUnderTest = new TestRequirements(new AndNode(leftNode, rightNode));

        HashSet<?> actualVariables = objUnderTest.getVariables();
        assertEquals(expectedVariables, actualVariables);
    }

    @Test
    public void testGetAllConditions() {
        //The predicate is (v1 == v2) && (v3 >= (v4 + v5))
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");
        VariableNode<?> v3 = new VariableNode<Long>("v3");
        VariableNode<?> v4 = new VariableNode<Float>("v4");
        VariableNode<?> v5 = new VariableNode<Float>("v5");
        ConditionNode leftNode = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ArithmeticNode arithmeticNode = new ArithmeticNode(v4, ArithmeticRelation.Add, v5);
        ConditionNode rightNode = new ConditionNode(v3, ComparisonRelation.LargerOrEqualsTo, arithmeticNode);

        HashSet<ConditionNode> expectedConditions = new HashSet<>(Arrays.asList(leftNode, rightNode));

        TestRequirements objUnderTest = new TestRequirements(new AndNode(leftNode, rightNode));

        HashSet<ConditionNode> actualConditions = objUnderTest.getAllConditions();
        assertEquals(expectedConditions, actualConditions);
    }

    @Test
    public void testGetMajorEquivalentEqualsConditions() {
        //The predicate is (v1 == v2) && (v2 == v1) && (v1 == v2)
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode c2 = new ConditionNode(v2, ComparisonRelation.EqualsEquals, v1);
        ConditionNode c3 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        AndNode root = new AndNode(c1, new AndNode(c2, c3));

        HashSet<ConditionNode> c1HashSet = new HashSet<>(Collections.singletonList(c1));
        HashSet<ConditionNode> c2HashSet = new HashSet<>(Collections.singletonList(c2));
        TestRequirements objUnderTest = new TestRequirements(root);

        HashSet<ConditionNode> actualMajors = objUnderTest.getMajors();

        assertTrue(c1HashSet.equals(actualMajors) || c2HashSet.equals(actualMajors));
    }

    @Test
    public void testGetMajorContradictingEqualsConditions() {
        //The predicate is (v1 == v2) && (v2 != v1) && (v1 != v2) || (v2 == v1)
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode c2 = new ConditionNode(v2, ComparisonRelation.NotEquals, v1);
        ConditionNode c3 = new ConditionNode(v1, ComparisonRelation.NotEquals, v2);
        ConditionNode c4 = new ConditionNode(v2, ComparisonRelation.EqualsEquals, v1);
        AndNode root = new AndNode(new AndNode(c1, c2), new OrNode(c3, c4));

        HashSet<ConditionNode> c1HashSet = new HashSet<>(Collections.singletonList(c1));
        HashSet<ConditionNode> c4HashSet = new HashSet<>(Collections.singletonList(c4));
        TestRequirements objUnderTest = new TestRequirements(root);

        HashSet<ConditionNode> actualMajors = objUnderTest.getMajors();

        assertTrue(c1HashSet.equals(actualMajors) || c4HashSet.equals(actualMajors));
    }

    @Test
    public void testLargerThanContradictingConditions() {
        //The predicate: (v1 > v2) && (v1 <= v2)
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.LargerThan, v2);
        ConditionNode c2 = new ConditionNode(v1, ComparisonRelation.SmallerOrEqualsTo, v2);

        TestRequirements objUnderTest = new TestRequirements(new AndNode(c1, c2));

        HashSet<ConditionNode> expectedMajors = new HashSet<>(Collections.singletonList(c1));
        assertEquals(expectedMajors, objUnderTest.getMajors());
    }

    @Test
    public void testSmallerThanContradictingConditions() {
        //The predicate: (v1 < v2) && (v1 >= v2)
        VariableNode<?> v1 = new VariableNode<Integer>("v1");
        VariableNode<?> v2 = new VariableNode<Double>("v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.SmallerThan, v2);
        ConditionNode c2 = new ConditionNode(v1, ComparisonRelation.LargerOrEqualsTo, v2);

        TestRequirements objUnderTest = new TestRequirements(new AndNode(c1, c2));

        HashSet<ConditionNode> expectedMajors = new HashSet<>(Collections.singletonList(c1));
        assertEquals(expectedMajors, objUnderTest.getMajors());
    }
}
