package uk.ac.shef.com3529.assignment.part1.model;

import java.util.Objects;

//T would be the type of the variable (identifier)
public class VariableNode<T extends Number> extends SyntaxNode {
    protected String name;
    protected T value;

    public VariableNode(String name) {
        this.name = name;
    }

    public VariableNode(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableNode)) return false;
        VariableNode<?> that = (VariableNode<?>) o;
        return name.equals(that.name) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return name;
    }
}
