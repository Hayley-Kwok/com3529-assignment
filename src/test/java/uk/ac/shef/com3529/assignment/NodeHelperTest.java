package uk.ac.shef.com3529.assignment;

import org.junit.jupiter.api.Test;
import uk.ac.shef.com3529.assignment.model.AndNode;
import uk.ac.shef.com3529.assignment.model.ArithmeticNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;
import uk.ac.shef.com3529.assignment.model.enums.ArithmeticRelation;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeHelperTest {

    @Test
    public void testGetVariables() {
        //The predicate is (v1 == v2) && (v3 == v4)
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Double.class, "v2");
        VariableNode<?> v3 = new VariableNode<>(Long.class, "v3");
        VariableNode<?> v4 = new VariableNode<>(Float.class, "v4");
        ConditionNode leftNode = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ConditionNode rightNode = new ConditionNode(v3, ComparisonRelation.EqualsEquals, v4);

        VariableNode<?>[] expectedVariables = new VariableNode<?>[]{v1, v2, v3, v4};

        NodeHelper objUnderTest = new NodeHelper(new AndNode(leftNode, rightNode));

        VariableNode<?>[] actualVariables = objUnderTest.getVariables();
        assertTrue(Arrays.equals(expectedVariables, actualVariables));
    }

    @Test
    public void testGetAllConditions() {
        //The predicate is (v1 == v2) && (v3 >= (v4 + v5))
        VariableNode<?> v1 = new VariableNode<>(Integer.class, "v1");
        VariableNode<?> v2 = new VariableNode<>(Double.class, "v2");
        VariableNode<?> v3 = new VariableNode<>(Long.class, "v3");
        VariableNode<?> v4 = new VariableNode<>(Float.class, "v4");
        VariableNode<?> v5 = new VariableNode<>(Float.class, "v5");
        ConditionNode leftNode = new ConditionNode(v1, ComparisonRelation.EqualsEquals, v2);
        ArithmeticNode arithmeticNode = new ArithmeticNode(v4, ArithmeticRelation.Add, v5);
        ConditionNode rightNode = new ConditionNode(v3, ComparisonRelation.LargerOrEqualsTo, arithmeticNode);

        HashSet<ConditionNode> expectedConditions = new HashSet<>(Arrays.asList(leftNode, rightNode));

        NodeHelper objUnderTest = new NodeHelper(new AndNode(leftNode, rightNode));

        HashSet<ConditionNode> actualConditions = objUnderTest.getConditions();
        assertEquals(expectedConditions, actualConditions);
    }
}
