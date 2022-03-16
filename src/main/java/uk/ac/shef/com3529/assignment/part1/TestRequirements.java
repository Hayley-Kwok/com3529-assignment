package uk.ac.shef.com3529.assignment.part1;

import uk.ac.shef.com3529.assignment.part1.model.ConditionNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.SyntaxNode;

import java.util.HashSet;
import java.util.function.Consumer;

public class TestRequirements {
    private final SyntaxNode root;
    private final HashSet<VariableNode<?>> variables = new HashSet<>();
    private final HashSet<ConditionNode> conditions = new HashSet<>();

    public TestRequirements(SyntaxNode root) {
        this.root = root;
        traverseTree(root, this::addVariableNodeToVariables);
        traverseTree(root, this::addConditionNodeToConditions);
    }

    private void addConditionNodeToConditions(SyntaxNode node) {
        if (node instanceof ConditionNode) {
            conditions.add((ConditionNode) node);
        }
    }

    private void addVariableNodeToVariables(SyntaxNode node) {
        if (node instanceof VariableNode) {
            variables.add((VariableNode<?>) node);
        }
    }

    private void traverseTree(SyntaxNode node, Consumer<SyntaxNode> addNodeToSet) {
        if (node == null) {
            return;
        }

        traverseTree(node.getLeftNode(), addNodeToSet);
        addNodeToSet.accept(node);
        traverseTree(node.getRightNode(), addNodeToSet);
    }

    public HashSet<VariableNode<?>> getVariables() {
        return variables;
    }

    public HashSet<ConditionNode> getConditions() {
        return conditions;
    }
}
