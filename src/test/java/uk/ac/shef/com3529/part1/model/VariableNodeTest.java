package uk.ac.shef.com3529.part1.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VariableNodeTest {

    @ParameterizedTest
    @CsvSource({"a, a, 1, 1, true",
            "a, b, 1, 1, false",
            "a, a, 1, 1, true",
            "a, a, 1, 2, false"})
    public void TestEqualsIntNode(String aName, String bName, int aVal, int bVal, boolean equals) {
        VariableNode<Integer> a = new VariableNode<>(aName, aVal);
        VariableNode<Integer> b = new VariableNode<>(bName, bVal);

        assertEquals(equals, a.hashCode() == b.hashCode());
        assertEquals(a.equals(b), equals);
    }
}
