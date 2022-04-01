package uk.ac.shef.com3529.assignment.model;

import java.util.Objects;

public class NumericalNode<T extends Number> extends SyntaxNode {
    private final T value;

    public NumericalNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumericalNode)) return false;
        NumericalNode<?> that = (NumericalNode<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
