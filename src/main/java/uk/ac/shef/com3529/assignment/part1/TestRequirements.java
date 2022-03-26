package uk.ac.shef.com3529.assignment.part1;

import uk.ac.shef.com3529.assignment.part1.model.BinaryRelatedNode;
import uk.ac.shef.com3529.assignment.part1.model.ConditionNode;
import uk.ac.shef.com3529.assignment.part1.model.SyntaxNode;
import uk.ac.shef.com3529.assignment.part1.model.VariableNode;
import uk.ac.shef.com3529.assignment.part1.model.enums.ComparisonRelation;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TestRequirements {
    private final BinaryRelatedNode<?> root;
    private final HashSet<VariableNode<?>> variables = new HashSet<>();
    private final HashSet<ConditionNode> allConditions = new HashSet<>();
    private ConditionNode[] majors;
    private ArrayList<ArrayList<Boolean>> fullMultiConditionTable;
    private ArrayList<ArrayList<Boolean>> restrictedConditionTable;

    //using string as the key for the ConditionNode here cause HashMap doesn't like mutating object as key
    private HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions = new HashMap<>();
    private HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions = new HashMap<>();

    public TestRequirements(BinaryRelatedNode<?> root) {
        this.root = root;
        traverseTree(root, this::addVariableNodeToVariables);
        traverseTree(root, this::addConditionNodeToConditions);
        findMajors();
    }

    public BinaryRelatedNode<?> getRoot() {
        return root;
    }

    public HashSet<VariableNode<?>> getVariables() {
        return variables;
    }

    public HashSet<ConditionNode> getAllConditions() {
        return allConditions;
    }

    public ConditionNode[] getMajors() {
        return majors;
    }

    public ArrayList<ArrayList<Boolean>> getFullMultiConditionTable() {
        if (fullMultiConditionTable != null) {
            return fullMultiConditionTable;
        }

        ArrayList<ArrayList<Boolean>> fullTruthTable = generateBooleanValuesForConditions(majors.length);
        for (ArrayList<Boolean> row : fullTruthTable) {
            for (int i = 0; i < majors.length; i++) {
                majors[i].setResult(row.get(i));
            }
            row.add(getBranchPredicate());
        }

        fullMultiConditionTable = fullTruthTable;
        return fullMultiConditionTable;
    }

    public ArrayList<ArrayList<Boolean>> getRestrictedMCDCConditionTable() {
        if (restrictedConditionTable != null) {
            return restrictedConditionTable;
        }

        ArrayList<int[]>[] allRestrictedMCDCPairs = getPossibleRestrictedMCDCPairs();

        List<ArrayList<int[]>> restrictedMCDCCombinations = generatePermutations(allRestrictedMCDCPairs);

        //find the index of the test needed for each restrictedMCDCCombination
        ArrayList<HashSet<Integer>> possibleRestrictedMCDC = new ArrayList<>();
        for (ArrayList<int[]> restrictedMCDCCombination : restrictedMCDCCombinations) {
            HashSet<Integer> row = new HashSet<>();
            for (int[] ids : restrictedMCDCCombination) {
                Arrays.stream(ids).forEach(row::add);
            }
            possibleRestrictedMCDC.add(row);
        }

        //find the set that require the minimum number of tests
        int minTests = possibleRestrictedMCDC.stream().mapToInt(HashSet::size).min().getAsInt();
        HashSet<Integer> MCDCIndices = possibleRestrictedMCDC.stream().filter(set -> set.size() == minTests).findFirst().get();

        //grab the condition from the table
        restrictedConditionTable = new ArrayList<>();
        for (Integer index : MCDCIndices) {
            restrictedConditionTable.add(getFullMultiConditionTable().get(index));
        }
        return restrictedConditionTable;
    }

    /**
     * Generate all permutations of lists
     *
     * @param lists the list that the method will generate permutations for
     * @return all permutations of the lists
     */
    private ArrayList<ArrayList<int[]>> generatePermutations(ArrayList<int[]>[] lists) {
        ArrayList<ArrayList<int[]>> restrictedMCDCCombinations = new ArrayList<>();
        generatePermutationsHelper(lists, restrictedMCDCCombinations, 0, new ArrayList<>());
        return restrictedMCDCCombinations;
    }

    /**
     * Helper for generatePermutations; It is a recursive function;
     *
     * @param lists   the list that the method will generate permutations for
     * @param result  the list that store the result permutations
     * @param depth   the depth that current list has reached
     * @param current the list that store the permutations so far
     */
    private void generatePermutationsHelper(ArrayList<int[]>[] lists, ArrayList<ArrayList<int[]>> result, int depth, ArrayList<int[]> current) {
        if (depth == lists.length) {
            result.add(current);
            return;
        }

        for (int i = 0; i < lists[depth].size(); i++) {
            ArrayList<int[]> copy = new ArrayList<>(current);
            copy.add(lists[depth].get(i));
            generatePermutationsHelper(lists, result, depth + 1, new ArrayList<>(copy));
        }
    }

    /**
     * Generate all the possible pairs for restricted MCDC
     *
     * @return an array of arraylist of int array.
     * The int array store the 2 indices of the test from the fullMultiConditionTable that satisfy restricted MCDC for that condition.
     */
    private ArrayList<int[]>[] getPossibleRestrictedMCDCPairs() {
        ArrayList<int[]>[] allCandidates = new ArrayList[majors.length];

        for (int i = 0; i < majors.length; i++) {
            for (int j = 0; j < getFullMultiConditionTable().size(); j++) {
                ArrayList<Boolean> flippedRow = getRestrictedFlippedRow(fullMultiConditionTable.get(j), i);
                for (int k = j; k < fullMultiConditionTable.size(); k++) {
                    if (fullMultiConditionTable.get(k).equals(flippedRow)) {
                        if (allCandidates[i] == null) {
                            allCandidates[i] = new ArrayList<>();
                        }
                        allCandidates[i].add(new int[]{j, k});
                    }
                }
            }
        }
        return allCandidates;
    }

    /**
     * Essentially a toString method for the object returned from getPossibleRestrictedMCDCPairs
     *
     * @return String version of getPossibleRestrictedMCDCPairs
     */
    private String getAllPossibleRestrictedMCDCStr() {
        StringBuilder sb = new StringBuilder().append("[");
        for (ArrayList<int[]> row : getPossibleRestrictedMCDCPairs()) {
            sb.append("[");
            if (row == null) {
                sb.append("null");
            } else {
                for (int[] pair : row) {
                    sb.append(Arrays.toString(pair)).append(", ");
                }
                sb.setLength(sb.length() - 2);
            }
            sb.append("], ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return an arrayList with elements at the last index (branch predicate) & majorIndex of orgRow flipped
     *
     * @param orgRow     the arrayList of the original row
     * @param majorIndex the index of the major that need to be flipped
     * @return flipped version of orgRow
     */
    private ArrayList<Boolean> getRestrictedFlippedRow(ArrayList<Boolean> orgRow, int majorIndex) {
        ArrayList<Boolean> flippedRow = new ArrayList<>(orgRow);
        int[] flippingIndices = new int[]{majorIndex, orgRow.size() - 1};
        for (int i : flippingIndices) {
            flippedRow.set(i, !flippedRow.get(i));
        }
        return flippedRow;
    }

    private ArrayList<Boolean> getFlippedRow(ArrayList<Boolean> orgRow) {
        ArrayList<Boolean> flippedRow = new ArrayList<>();
        for (boolean val : orgRow) {
            flippedRow.add(!val);
        }
        return flippedRow;
    }

    private ArrayList<ArrayList<Boolean>> generateBooleanValuesForConditions(int n) {
        int rows = (int) Math.pow(2, n);

        ArrayList<ArrayList<Boolean>> truthTable = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<Boolean> row = new ArrayList<>();
            for (int j = n - 1; j >= 0; j--) {
                if (((i / (int) Math.pow(2, j)) % 2) == 1) {
                    row.add(true);
                } else {
                    row.add(false);
                }
            }
            truthTable.add(row);
        }
        return truthTable;
    }

    //called after updating all majors
    private boolean getBranchPredicate() {
        for (ConditionNode major : majors) {
            String key = major.toString();
            if (removedEquivalentConditions.containsKey(key)) {
                ArrayList<ConditionNode> equivalentNodes = removedEquivalentConditions.get(key);
                for (ConditionNode node : equivalentNodes) {
                    node.setResult(major.getResult());
                }
            }

            if (removedContradictingConditions.containsKey(key)) {
                ArrayList<ConditionNode> contradictingNodes = removedContradictingConditions.get(key);
                for (ConditionNode node : contradictingNodes) {
                    node.setResult(!major.getResult());
                }
            }
        }
        try {
            makeSureUpdateAllConditions();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return root.getResult();
    }

    private void makeSureUpdateAllConditions() throws Exception {
        for (ConditionNode node : allConditions) {
            if (!node.getResultOverrode()) {
                throw new Exception("not all node is updated when evaluating branch predicate");
            }
        }
    }

    /**
     * Find all the Majors from allCondition and save it to majors field
     */
    private void findMajors() {
        //TODO flip v1 >= v2 & v2 <= v1 as equivalent
        HashSet<ConditionNode> majorSet = new HashSet<>(allConditions);

        HashSet<ConditionNode> equalsNodes =
                majorSet.stream().filter(n -> n.getRelation().equals(ComparisonRelation.EqualsEquals)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateEquivalentNodes(equalsNodes, majorSet);
        eliminateContradictingNode(equalsNodes, majorSet, ComparisonRelation.NotEquals, true);

        HashSet<ConditionNode> notEqualsNodes =
                majorSet.stream().filter(n -> n.getRelation().equals(ComparisonRelation.NotEquals)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateEquivalentNodes(notEqualsNodes, majorSet);


        HashSet<ConditionNode> largerThanNodes =
                majorSet.stream().filter(n -> n.getRelation().equals(ComparisonRelation.LargerThan)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateContradictingNode(largerThanNodes, majorSet, ComparisonRelation.SmallerOrEqualsTo, false);

        HashSet<ConditionNode> smallerThanNodes =
                majorSet.stream().filter(n -> n.getRelation().equals(ComparisonRelation.SmallerThan)).
                        collect(Collectors.toCollection(HashSet::new));
        eliminateContradictingNode(smallerThanNodes, majorSet, ComparisonRelation.LargerOrEqualsTo, false);

        majors = new ConditionNode[majorSet.size()];
        majorSet.toArray(majors);
    }

    /**
     * Eliminate the contradicting conditions of conditions from the notEqualsNodes from major and add the eliminated
     * conditions to removedContradictingConditions so that we can still compute the branch predicate.
     * An example of contradicting conditions is (v1 >= v2) and (v1 < v2)
     *
     * @param notEqualsNodes    a set of nodes with the same ComparisonRelation
     * @param majorSet          the set of majors we are updating
     * @param flippedRelation   the contradicting ComparisonRelation of notEqualsNodes
     * @param flipLeftRightNode does the method consider flipping leftNode and rightNode. Eg: (v1 == v2) should consider
     *                          both (v1 != v2) & (v2 != v1) as a contradicting condition
     */
    private void eliminateContradictingNode(HashSet<ConditionNode> notEqualsNodes,
                                            HashSet<ConditionNode> majorSet,
                                            ComparisonRelation flippedRelation,
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
                ConditionNode contradictingNode = getNodeFromSet(oppositeNode, majorSet);
                if (contradictingNode != null) {
                    majorSet.remove(contradictingNode);
                    String key = currentNode.toString();
                    if (removedContradictingConditions.containsKey(key)) {
                        removedContradictingConditions.get(key).add(contradictingNode);
                    } else {
                        removedContradictingConditions.put(key, new ArrayList<>(Collections.singletonList(contradictingNode)));
                    }
                }
            }
        }
    }

    private ConditionNode getNodeFromSet(ConditionNode node, HashSet<ConditionNode> set) {
        for (ConditionNode element : set) {
            if (element.equals(node)) {
                return element;
            }
        }
        return null;
    }

    /**
     * This method is for Equals and NotEquals situation as flipping leftNode and rightNode are considered as equivalent.
     * Eg: c1 == c2 is equivalent as c2 == c1 and one of it should be removed when considering major
     * The equivalent node will be removed from the nonEquivalentConditions & added to removedEquivalentConditions so
     * that the value can still be updated when computing the branch predicate.
     *
     * @param nodeSubset subset of the node that has the same ComparisonRelation
     * @param majorSet   set of major we are considering
     */
    private void eliminateEquivalentNodes(HashSet<ConditionNode> nodeSubset, HashSet<ConditionNode> majorSet) {
        for (Iterator<ConditionNode> i = nodeSubset.iterator(); i.hasNext(); ) {
            ConditionNode currentNode = i.next();

            ConditionNode positionSwappedNode = new ConditionNode(currentNode.getRightNode(), currentNode.getRelation(),
                    currentNode.getLeftNode());

            if (nodeSubset.contains(positionSwappedNode)) {
                i.remove();
                majorSet.remove(currentNode);
                String key = positionSwappedNode.toString();
                if (removedEquivalentConditions.containsKey(key)) {
                    removedEquivalentConditions.get(key).add(currentNode);
                } else {
                    removedEquivalentConditions.put(key, new ArrayList<>(Collections.singletonList(currentNode)));
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
