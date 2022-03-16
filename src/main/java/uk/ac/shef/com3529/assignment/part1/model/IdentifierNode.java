package uk.ac.shef.com3529.assignment.part1.model;

//T would be the type of the variable (identifier)
public class IdentifierNode<T> implements SyntaxNode {
    protected String name;
    protected T value;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public IdentifierNode(String name, T value) {
        this.name = name;
        this.value = value;
    }
}
