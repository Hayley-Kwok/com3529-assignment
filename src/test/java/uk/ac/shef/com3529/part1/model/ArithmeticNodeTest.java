package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.ArithmeticNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.enums.ArithmeticRelation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArithmeticNodeTest {

    @ParameterizedTest
    @CsvSource({"1, 1, Add, 2", "-1, -1, Minus, 0", "-1, -2, Multiply, 2", "2.4, 1.2, Divide, 2"})
    public void getResultTest(double leftNodeVal, double rightNodeVal, String relation, double expectedVal) {
        ArithmeticNode objUnderTest = new ArithmeticNode(
                new VariableNode<>("v1", leftNodeVal),
                ArithmeticRelation.valueOf(relation),
                new VariableNode<>("v2", rightNodeVal));

        assertEquals(expectedVal, objUnderTest.getResult());
    }

    @ParameterizedTest
    @CsvSource({"v1, 1, v2, 1, v1, 1, v2, 1, Minus, Minus, true",
                "v1, 2, v2, 1, v1, 1, v2, 1, Minus, Minus, false",
                "v1, 1, v3, 1, v1, 1, v2, 1, Minus, Minus, false",
                "v1, 1, v2, 3, v1, 1, v2, 1, Minus, Minus, false",
                "v1, 1, v2, 1, v3, 1, v2, 1, Minus, Minus, false",
                "v1, 1, v2, 1, v1, 2, v2, 1, Minus, Minus, false",
                "v1, 1, v2, 1, v1, 1, v3, 1, Minus, Minus, false",
                "v1, 1, v2, 1, v1, 1, v2, 4, Minus, Minus, false",
                "v1, 1, v2, 1, v1, 1, v2, 1, Add, Minus, false",
                "v1, 1, v2, 1, v1, 1, v2, 1, Minus, Add, false"})
    public void equalsTest(String node1LeftName, int node1LeftVal, String node1RightName, int node1RightVal,
                           String node2LeftName, int node2LeftVal, String node2RightName, int node2RightVal,
                           String node1Relation, String node2Relation, boolean expectedVal) {
        ArithmeticNode node1 =  new ArithmeticNode(
                new VariableNode<>(node1LeftName, node1LeftVal),
                ArithmeticRelation.valueOf(node1Relation),
                new VariableNode<>(node1RightName, node1RightVal));

        ArithmeticNode node2 =  new ArithmeticNode(
                new VariableNode<>(node2LeftName, node2LeftVal),
                ArithmeticRelation.valueOf(node2Relation),
                new VariableNode<>(node2RightName, node2RightVal));

        assertEquals(node1.equals(node2), expectedVal);
    }
}
