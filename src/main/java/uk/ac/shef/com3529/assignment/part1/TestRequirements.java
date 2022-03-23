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

    /**
     * Find all the Majors from allCondition and save it to Majors field
     */
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

    /**
     * Eliminate the contradicting conditions of conditions from the notEqualsNodes from major and add the eliminated
     * conditions to removedContradictingConditions so that we can still compute the branch predicate.
     * An example of contradicting conditions is (v1 >= v2) and (v1 < v2)
     *
     * @param notEqualsNodes    a set of nodes with the same ComparisonRelation
     * @param flippedRelation   the contradicting ComparisonRelation of notEqualsNodes
     * @param flipLeftRightNode does the method consider flipping leftNode and rightNode. Eg: (v1 == v2) should consider
     *                          both (v1 != v2) & (v2 != v1) as a contradicting condition
     */
    private void eliminateContradictingNode(HashSet<ConditionNode> notEqualsNodes, ComparisonRelation flippedRelation,
                                            boolean flipLeftRightNode) {
        for (ConditionNode currentNode : notEqualsNodes) {
            ConditionNode[] oppositeNodes;
            if (flipLeftRightNode) {
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

    /**
     * Add the node into the allConditions set if the type is ConditionNode; Method being used in traverseTree
     *
     * @param node a syntaxNode
     */
    private void addConditionNodeToConditions(SyntaxNode node) {
        if (node instanceof ConditionNode) {
            allConditions.add((ConditionNode) node);
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
