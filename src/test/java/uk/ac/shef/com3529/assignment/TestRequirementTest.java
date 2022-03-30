package uk.ac.shef.com3529.assignment;

import org.junit.jupiter.api.Test;
import uk.ac.shef.com3529.assignment.model.AndNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.OrNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRequirementTest {
    @Test
    public void testGetMajorsEquivalentEqualsConditions() {
        //The predicate is (v1 == v2) && (v2 == v1) && (v1 == v2)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode c2 = new ConditionNode(v2, ComparisonRelation.EqualsEquals, v1);
        ConditionNode c3 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        AndNode root = new AndNode(c1, new AndNode(c2, c3));

        TestRequirements objUnderTest = new TestRequirements(root);

        ConditionNode[] actualMajors = objUnderTest.getMajors();

        assertTrue(Arrays.equals(actualMajors, new ConditionNode[]{c1}) ||
                Arrays.equals(actualMajors, new ConditionNode[]{c2}));
    }

    @Test
    public void testGetMajorsEquivalentLargerEqualsConditions() {
        //The predicate is (v1 >= v2) && (v2 <= v1)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.LargerOrEqualsTo, v2);
        ConditionNode c2 = new ConditionNode(v2, ComparisonRelation.SmallerOrEqualsTo, v1);

        TestRequirements objUnderTest = new TestRequirements(new AndNode(c1, c2));

        ConditionNode[] actualMajors = objUnderTest.getMajors();
        assertTrue(Arrays.equals(actualMajors, new ConditionNode[]{c1}) ||
                Arrays.equals(actualMajors, new ConditionNode[]{c2}));
    }

    @Test
    public void testGetMajorsEquivalentLargerThanConditions() {
        //The predicate is (v1 > v2) && (v2 < v1)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.LargerThan, v2);
        ConditionNode c2 = new ConditionNode(v2, ComparisonRelation.SmallerThan, v1);

        TestRequirements objUnderTest = new TestRequirements(new AndNode(c1, c2));

        ConditionNode[] actualMajors = objUnderTest.getMajors();
        assertTrue(Arrays.equals(actualMajors, new ConditionNode[]{c1}) ||
                Arrays.equals(actualMajors, new ConditionNode[]{c2}));
    }

    @Test
    public void testGetMajorsContradictingEqualsConditions() {
        //The predicate is (v1 == v2) && (v2 != v1) && (v1 != v2) || (v2 == v1)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode c2 = new ConditionNode(v2, ComparisonRelation.NotEquals, v1);
        ConditionNode c3 = new ConditionNode(v1, ComparisonRelation.NotEquals, v2);
        ConditionNode c4 = new ConditionNode(v2, ComparisonRelation.EqualsEquals, v1);
        AndNode root = new AndNode(new AndNode(c1, c2), new OrNode(c3, c4));

        TestRequirements objUnderTest = new TestRequirements(root);

        ConditionNode[] actualMajors = objUnderTest.getMajors();

        assertTrue(Arrays.equals(actualMajors, new ConditionNode[]{c1}) ||
                Arrays.equals(actualMajors, new ConditionNode[]{c4}));
    }

    @Test
    public void testGetMajorsLargerThanContradictingConditions() {
        //The predicate: (v1 > v2) && (v1 <= v2)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.LargerThan, v2);
        ConditionNode c2 = new ConditionNode(v1, ComparisonRelation.SmallerOrEqualsTo, v2);

        TestRequirements objUnderTest = new TestRequirements(new AndNode(c1, c2));

        assertTrue(Arrays.equals(objUnderTest.getMajors(), new ConditionNode[]{c1}));
    }

    @Test
    public void testGetMajorsSmallerThanContradictingConditions() {
        //The predicate: (v1 < v2) && (v1 >= v2)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.SmallerThan, v2);
        ConditionNode c2 = new ConditionNode(v1, ComparisonRelation.LargerOrEqualsTo, v2);

        TestRequirements objUnderTest = new TestRequirements(new AndNode(c1, c2));

        assertTrue(Arrays.equals(objUnderTest.getMajors(), new ConditionNode[]{c1}));
    }

    @Test
    public void testGetFullMultiConditionTable() {
        //The predicate: (v1 < v2) || (v1 == v2)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Integer.class, "v2");

        ConditionNode c1 = new ConditionNode(v1, ComparisonRelation.SmallerThan, v2);
        ConditionNode c2 = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);

        TestRequirements objUnderTest = new TestRequirements(new OrNode(c1, c2));

        ArrayList<ArrayList<Boolean>> expected = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(false, false, false)),
                new ArrayList<>(Arrays.asList(false, true, true)),
                new ArrayList<>(Arrays.asList(true, false, true)),
                new ArrayList<>(Arrays.asList(true, true, true))
        ));

        assertEquals(expected, objUnderTest.getFullConditionTable());
    }
}
