package uk.ac.shef.com3529.assignment.part1;

import uk.ac.shef.com3529.assignment.part1.model.ConditionNode;
import uk.ac.shef.com3529.assignment.part1.model.SyntaxNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TestRequirements {
    private final SyntaxNode root;
    private final HashSet<VariableNode<?>> variables = new HashSet<>();
    private final HashSet<ConditionNode> allConditions = new HashSet<>();
    private HashSet<ConditionNode> majors;
    private HashMap<ConditionNode, ArrayList<ConditionNode>> removedEquivalentConditions = new HashMap<>();
    private HashMap<ConditionNode, ArrayList<ConditionNode>> removedContradictingConditions = new HashMap<>();

    public TestRequirements(SyntaxNode root) {
        this.root = root;
        traverseTree(root, this::addVariableNodeToVariables);
        traverseTree(root, this::addConditionNodeToConditions);
        findMajors();
    }

    public SyntaxNode getRoot() {
        return root;
    }

    public HashSet<VariableNode<?>> getVariables() {
        return variables;
    }

    public HashSet<ConditionNode> getAllConditions() {
        return allConditions;
    }

    public HashSet<ConditionNode> getMajors() {
        return majors;
    }

    private void findMajors() {
        majors = new HashSet<>(allConditions);

        HashSet<ConditionNode> equalsNodes =
                majors.stream().filter(n -> n.getRelation().equals(ComparisonRelation.EqualsEquals)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateEquivalentNodes(equalsNodes);
        eliminateContradictingNode(equalsNodes, ComparisonRelation.NotEquals, true);

        HashSet<ConditionNode> notEqualsNodes =
                majors.stream().filter(n -> n.getRelation().equals(ComparisonRelation.NotEquals)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateEquivalentNodes(notEqualsNodes);


        HashSet<ConditionNode> largerThanNodes =
                majors.stream().filter(n -> n.getRelation().equals(ComparisonRelation.LargerThan)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateContradictingNode(largerThanNodes, ComparisonRelation.SmallerOrEqualsTo, false);

        HashSet<ConditionNode> smallerThanNodes =
                majors.stream().filter(n -> n.getRelation().equals(ComparisonRelation.SmallerThan)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateContradictingNode(smallerThanNodes, ComparisonRelation.LargerOrEqualsTo, false);
    }

    private void eliminateContradictingNode(HashSet<ConditionNode> notEqualsNodes, ComparisonRelation flippedRelation,
                                            boolean considerFlippingNodes) {
        for (ConditionNode currentNode : notEqualsNodes) {
            ConditionNode[] oppositeNodes;
            if (considerFlippingNodes) {
                oppositeNodes = new ConditionNode[]{
                        new ConditionNode(currentNode.getLeftNode(), flippedRelation, currentNode.getRightNode()),
                        new ConditionNode(currentNode.getRightNode(), flippedRelation, currentNode.getLeftNode())};
            } else {
                oppositeNodes = new ConditionNode[]{
                        new ConditionNode(currentNode.getLeftNode(), flippedRelation, currentNode.getRightNode())};
            }

            for (ConditionNode oppositeNode : oppositeNodes) {
                if (majors.contains(oppositeNode)) {
                    majors.remove(oppositeNode);

                    if (removedContradictingConditions.containsKey(currentNode)) {
                        removedContradictingConditions.get(currentNode).add(oppositeNode);
                    } else {
                        removedContradictingConditions.put(currentNode, new ArrayList<>(Collections.singletonList(oppositeNode)));
                    }
                }
            }
        }
    }

    /**
     * This method is for Equals and NotEquals situation as flipping leftNode and rightNode are considered as equivalent.
     * Eg: c1 == c2 is equivalent as c2 == c1 and one of it should be removed when considering major
     * The equivalent node will be removed from the nonEquivalentConditions & added to removedEquivalentConditions so
     * that the value can still be updated when computing the branch predicate.
     *
     * @param nodeSubset subset of the node that has the same ComparisonRelation
     */
    private void eliminateEquivalentNodes(HashSet<ConditionNode> nodeSubset) {
        for (Iterator<ConditionNode> i = nodeSubset.iterator(); i.hasNext(); ) {
            ConditionNode currentNode = i.next();

            ConditionNode positionSwappedNode = new ConditionNode(currentNode.getRightNode(), currentNode.getRelation(),
                    currentNode.getLeftNode());

            if (nodeSubset.contains(positionSwappedNode)) {
                i.remove();
                majors.remove(currentNode);
                if (removedEquivalentConditions.containsKey(positionSwappedNode)) {
                    removedEquivalentConditions.get(positionSwappedNode).add(currentNode);
                } else {
                    removedEquivalentConditions.put(positionSwappedNode, new ArrayList<>(Collections.singletonList(currentNode)));
                }
            }
        }
    }

    private void addConditionNodeToConditions(SyntaxNode node) {
        if (node instanceof ConditionNode) {
            allConditions.add((ConditionNode) node);
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
}
