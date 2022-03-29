package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.SyntaxNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.HashSet;
import java.util.function.Consumer;

public class NodeHelper {
    private final HashSet<VariableNode<?>> variables = new HashSet<>();
    private final HashSet<ConditionNode> conditions = new HashSet<>();

    public NodeHelper(SyntaxNode root) {
        traverseTree(root, this::addVariableNodeToVariables);
        traverseTree(root, this::addConditionNodeToConditions);
    }

    public HashSet<VariableNode<?>> getVariables() {
        return variables;
    }

    public HashSet<ConditionNode> getConditions() {
        return conditions;
    }

    /**
     * Add the node into the allConditions set if the type is ConditionNode; Method being used in traverseTree
     *
     * @param node a syntaxNode
     */
    private void addConditionNodeToConditions(SyntaxNode node) {
        if (node instanceof ConditionNode) {
            conditions.add((ConditionNode) node);
        }
    }

    /**
     * Add the node into the variables set if the type is VariableNode; Method being used in traverseTree
     *
     * @param node a syntaxNode
     */
    private void addVariableNodeToVariables(SyntaxNode node) {
        if (node instanceof VariableNode) {
            variables.add((VariableNode<?>) node);
        }
    }

    /**
     * Looping through the entire syntax tree in order and call the addNodeToSet method on each node
     *
     * @param node         root node to the syntax tree
     * @param addNodeToSet method that take in the current node and do some operation on it
     */
    private void traverseTree(SyntaxNode node, Consumer<SyntaxNode> addNodeToSet) {
        if (node == null) {
            return;
        }

        traverseTree(node.getLeftNode(), addNodeToSet);
        addNodeToSet.accept(node);
        traverseTree(node.getRightNode(), addNodeToSet);
    }
}
