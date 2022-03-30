package uk.ac.shef.com3529.assignment.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VariableNodeTest {
    @ParameterizedTest
    @CsvSource({
            "a, a, 1, 1, 1, 10, 1, 10, true",
            "a, b, 1, 1, 1, 10, 1, 10, false",
            "a, a, 1, 2, 1, 10, 1, 10, false",
            "a, a, 1, 1, 1, 10, 3, 10, false",
            "a, a, 1, 1, 1, 13, 1, 10, false",
    })
    public void TestEqualsIntNode(String aName, String bName, int aVal, int bVal, int aMin, int aMax, int bMin, int bMax, boolean equals) {
        VariableNode<Integer> a = new VariableNode<>(Integer.class, aName, aVal, aMin, aMax);
        VariableNode<Integer> b = new VariableNode<>(Integer.class, bName, bVal, bMin, bMax);

        assertEquals(equals, a.hashCode() == b.hashCode());
        assertEquals(equals, a.equals(b));
    }

    @Test
    public void TestEqualsDifferentType() {
        VariableNode<Integer> a = new VariableNode<>(Integer.class, "a");
        VariableNode<Double> b = new VariableNode<>(Double.class, "a");

        assertNotEquals(b.hashCode(), a.hashCode());
        assertNotEquals(b, a);
    }

    @Test
    public void TestMinMaxSetByDefaultDouble() {
        VariableNode<Double> objUnderTest = new VariableNode<>(Double.class, "a");

        assertEquals(Double.MAX_VALUE, objUnderTest.getMax());
        assertEquals(Double.MIN_VALUE, objUnderTest.getMin());
    }

    @Test
    public void TestMinMaxSetByDefaultInt() {
        VariableNode<Integer> objUnderTest = new VariableNode<>(Integer.class, "a");

        assertEquals(Integer.MAX_VALUE, objUnderTest.getMax());
        assertEquals(Integer.MIN_VALUE, objUnderTest.getMin());
    }

    @Test
    public void TestMinMaxSetByDefaultLong() {
        VariableNode<Long> objUnderTest = new VariableNode<>(Long.class, "a");

        assertEquals(Long.MAX_VALUE, objUnderTest.getMax());
        assertEquals(Long.MIN_VALUE, objUnderTest.getMin());
    }

    @Test
    public void TestMinMaxSetByDefaultFloat() {
        VariableNode<Float> objUnderTest = new VariableNode<>(Float.class, "a");

        assertEquals(Float.MAX_VALUE, objUnderTest.getMax());
        assertEquals(Float.MIN_VALUE, objUnderTest.getMin());
    }

}
