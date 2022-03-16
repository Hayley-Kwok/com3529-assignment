package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VariableNodeTest {

    @ParameterizedTest
    @CsvSource({"1, 1, true", "1, 2, false"})
    public void TestEqualsIntNode(int aVal, int bVal, boolean equals) {
        VariableNode<Integer> a = new VariableNode<>("a", aVal);
        VariableNode<Integer> b = new VariableNode<>("a", bVal);

        assertEquals(a.equals(b), equals);
    }
}
