package uk.ac.shef.com3529.assignment.part1.model;

//T would be the type of the variable (identifier)
public class VariableNode<T> implements SyntaxNode {
    protected String name;
    protected T value;

    public VariableNode(String name) {
        this.name = name;
    }

    public VariableNode(String name, T value) {
        this.name = name;
        this.value = value;
    }
}
