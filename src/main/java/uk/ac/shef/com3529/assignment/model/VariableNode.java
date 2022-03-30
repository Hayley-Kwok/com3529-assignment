package uk.ac.shef.com3529.assignment.model;

import java.util.Objects;

//T would be the type of the variable (identifier)
public class VariableNode<T extends Number> extends SyntaxNode implements Comparable<VariableNode<T>> {
    private T max; //exclusive
    private T min; //inclusive
    private final Class<T> type;
    protected String name;
    protected T value;

    public VariableNode(Class<T> t, String name) {
        this.name = name;
        type = t;
        if (t.equals(Integer.class)) {
            max = (T) Integer.valueOf(Integer.MAX_VALUE);
            min = (T) Integer.valueOf(Integer.MIN_VALUE);
        } else if (t.equals(Long.class)) {
            max = (T) Long.valueOf(Long.MAX_VALUE);
            min = (T) Long.valueOf(Long.MIN_VALUE);
        } else if (t.equals(Float.class)) {
            max = (T) Float.valueOf(Float.MAX_VALUE);
            min = (T) Float.valueOf(Float.MIN_VALUE);
        } else {
            max = (T) Double.valueOf(Double.MAX_VALUE);
            min = (T) Double.valueOf(Double.MIN_VALUE);
        }
    }

    public VariableNode(Class<T> t, String name, T value) {
        this(t, name);
        this.value = value;
    }

    public VariableNode(Class<T> t, String name, T min, T max) {
        this(t, name);
        this.max = max;
        this.min = min;
    }

    public VariableNode(Class<T> t, String name, T value, T min, T max) {
        this(t, name, value);
        this.max = max;
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public T getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = (T) value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableNode)) return false;
        VariableNode<?> that = (VariableNode<?>) o;
        return max.equals(that.max) &&
                min.equals(that.min) &&
                type.equals(that.type) &&
                name.equals(that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, min, max, type);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(VariableNode that) {
        return this.name.compareTo(that.getName());
    }
}
