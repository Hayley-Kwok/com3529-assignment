package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestRequirements {
    private final BinaryRelatedNode<?> root;
    private final HashSet<VariableNode<?>> variables;
    private final HashSet<ConditionNode> allConditions;
    private ConditionNode[] majors;
    private ArrayList<ArrayList<Boolean>> fullConditionTable;
    private ArrayList<ArrayList<Boolean>> restrictedConditionTable;
    private ArrayList<Integer> restrictedTestIndices;
    private ArrayList<ArrayList<Boolean>> correlatedConditionTable;
    private ArrayList<Integer> correlatedTestIndices;

    //using string as the key for the ConditionNode here cause HashMap doesn't like mutating object as key
    private HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions = new HashMap<>();
    private HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions = new HashMap<>();

    public TestRequirements(BinaryRelatedNode<?> root) {
        this.root = root;

        NodeHelper nodeHelper = new NodeHelper(root);
        variables = nodeHelper.getVariables();
        allConditions = nodeHelper.getConditions();
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

    public HashMap<String, ArrayList<ConditionNode>> getRemovedEquivalentConditions() {
        return removedEquivalentConditions;
    }

    public HashMap<String, ArrayList<ConditionNode>> getRemovedContradictingConditions() {
        return removedContradictingConditions;
    }

    public ArrayList<ArrayList<Boolean>> getFullConditionTable() {
        if (fullConditionTable != null) {
            return fullConditionTable;
        }

        ArrayList<ArrayList<Boolean>> fullTruthTable = generateBooleanValuesForConditions(majors.length);
        BranchPredicate branchPredicate = new BranchPredicate(majors, removedEquivalentConditions,
                removedContradictingConditions, root, allConditions);

        for (ArrayList<Boolean> row : fullTruthTable) {
            row.add(branchPredicate.getResult(row));
        }

        fullConditionTable = fullTruthTable;
        return fullConditionTable;
    }

    public ArrayList<ArrayList<Boolean>> getRestrictedMCDCConditionTable() {
        if (restrictedConditionTable != null) {
            return restrictedConditionTable;
        }

        restrictedConditionTable = new ArrayList<>();
        for (Integer index : getRestrictedTestIndices()) {
            restrictedConditionTable.add(getFullConditionTable().get(index));
        }
        return restrictedConditionTable;
    }

    //TODO think about infeasible tests
    public ArrayList<Integer> getRestrictedTestIndices() {
        if (restrictedTestIndices != null) {
            return restrictedTestIndices;
        }

        ArrayList<int[]>[] allRestrictedMCDCPairs = getPossibleRestrictedMCDCPairs();

        List<ArrayList<int[]>> restrictedMCDCCombinations = generatePermutations(allRestrictedMCDCPairs);

        //find the index of the test needed for each restrictedMCDC Combination
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
        restrictedTestIndices = new ArrayList<>(possibleRestrictedMCDC.stream()
                .filter(set -> set.size() == minTests).findFirst().get());
        return restrictedTestIndices;
    }

    public ArrayList<ArrayList<Boolean>> getCorrelatedMCDCConditionTable() {
        if (correlatedConditionTable != null) {
            return correlatedConditionTable;
        }

        correlatedConditionTable = new ArrayList<>();
        for (Integer index : getCorrelatedTestIndices()) {
            correlatedConditionTable.add(getFullConditionTable().get(index));
        }
        return correlatedConditionTable;
    }

    public ArrayList<Integer> getCorrelatedTestIndices() {
        if (correlatedTestIndices != null) {
            return correlatedTestIndices;
        }

        ArrayList<ArrayList<Integer>> newPairs = new ArrayList<>();

        Set<Integer> notUsedTestIndices = IntStream.range(0, fullConditionTable.size()).boxed().collect(Collectors.toSet());
        notUsedTestIndices.removeAll(new HashSet<>(restrictedTestIndices));

        //check if existing restricted pairs also satisfy correlated or any existing restricted MCDC test has a flipped version in the fullConditionTable
        for (Integer i : restrictedTestIndices) {
            ArrayList<Boolean> flippedRow = getCorrelatedFlippedRow(fullConditionTable.get(i));
            for (Integer j : restrictedTestIndices) {
                if (fullConditionTable.get(j).equals(flippedRow)) {
                    correlatedTestIndices = new ArrayList<>(Arrays.asList(i, j));
                    return correlatedTestIndices;
                }
            }

            for (Integer j : notUsedTestIndices) {
                if (fullConditionTable.get(j).equals(flippedRow)) {
                    newPairs.add(new ArrayList<>(Arrays.asList(i, j)));
                }
            }
        }

        if (newPairs.size() > 0) {
            correlatedTestIndices = newPairs.get(0);
            return correlatedTestIndices;
        }

        //not used tests compare with not used tests
        for (Integer i : notUsedTestIndices) {
            ArrayList<Boolean> flippedRow = getCorrelatedFlippedRow(fullConditionTable.get(i));

            for (Integer j : notUsedTestIndices) {
                if (fullConditionTable.get(j).equals(flippedRow)) {
                    correlatedTestIndices = new ArrayList<>(Arrays.asList(i, j));
                    return correlatedTestIndices;
                }
            }
        }

        //TODO find the ones that require more than two test cases
        // 1. check if the existing restricted satisfy correlated
        // 2. find the complementing one if restricted doesn't satisfy
        correlatedTestIndices = restrictedTestIndices;
        return correlatedTestIndices;
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
            for (int j = 0; j < getFullConditionTable().size(); j++) {
                ArrayList<Boolean> flippedRow = getRestrictedFlippedRow(fullConditionTable.get(j), i);
                for (int k = j; k < fullConditionTable.size(); k++) {
                    if (fullConditionTable.get(k).equals(flippedRow)) {
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

    private ArrayList<Boolean> getCorrelatedFlippedRow(ArrayList<Boolean> orgRow) {
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

    /**
     * Find all the Majors from allCondition and save it to majors field
     */
    private void findMajors() {
        HashSet<ConditionNode> majorSet = new HashSet<>(allConditions);

        HashSet<ConditionNode> equalsNodes = getSubsetConditionNodes(majorSet, ComparisonRelation.EqualsEquals);
        eliminateEquivalentNodes(equalsNodes, majorSet, ComparisonRelation.EqualsEquals);
        eliminateContradictingNode(equalsNodes, majorSet, ComparisonRelation.NotEquals, true);

        HashSet<ConditionNode> notEqualsNodes = getSubsetConditionNodes(majorSet, ComparisonRelation.NotEquals);
        eliminateEquivalentNodes(notEqualsNodes, majorSet, ComparisonRelation.NotEquals);

        HashSet<ConditionNode> largerEqualsNodes = getSubsetConditionNodes(majorSet, ComparisonRelation.LargerOrEqualsTo);
        eliminateEquivalentNodes(largerEqualsNodes, majorSet, ComparisonRelation.SmallerOrEqualsTo);

        HashSet<ConditionNode> largerThanNodes = getSubsetConditionNodes(majorSet, ComparisonRelation.LargerThan);
        eliminateEquivalentNodes(largerThanNodes, majorSet, ComparisonRelation.SmallerThan);
        eliminateContradictingNode(largerThanNodes, majorSet, ComparisonRelation.SmallerOrEqualsTo, false);

        HashSet<ConditionNode> smallerThanNodes = getSubsetConditionNodes(majorSet, ComparisonRelation.SmallerThan);
        eliminateContradictingNode(smallerThanNodes, majorSet, ComparisonRelation.LargerOrEqualsTo, false);

        majors = new ConditionNode[majorSet.size()];
        majorSet.toArray(majors);
    }

    private HashSet<ConditionNode> getSubsetConditionNodes(HashSet<ConditionNode> set, ComparisonRelation relation) {
        return set.stream().filter(n -> n.getRelation().equals(relation)).
                collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Eliminate the contradicting conditions of conditions from the notEqualsNodes from major and add the eliminated
     * conditions to removedContradictingConditions so that we can still compute the branch predicate.
     * An example of contradicting conditions is (v1 >= v2) and (v1 < v2)
     *
     * @param notEqualsNodes              a set of nodes with the same ComparisonRelation
     * @param majorSet                    the set of majors we are updating
     * @param flippedRelation             the contradicting ComparisonRelation of notEqualsNodes
     * @param includeFlippedLeftRightNode does the method consider flipping leftNode and rightNode. Eg: (v1 == v2) should consider
     *                                    both (v1 != v2) & (v2 != v1) as a contradicting condition
     */
    private void eliminateContradictingNode(HashSet<ConditionNode> notEqualsNodes,
                                            HashSet<ConditionNode> majorSet,
                                            ComparisonRelation flippedRelation,
                                            boolean includeFlippedLeftRightNode) {
        for (ConditionNode currentNode : notEqualsNodes) {
            ConditionNode[] oppositeNodes;
            if (includeFlippedLeftRightNode) {
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
     * This method is for situation that flipping leftNode and rightNode are considered as equivalent.
     * Eg: v1 == v2 is equivalent as v2 == v1 and one of it should be removed when considering major
     * Another eg: v1 >= v2 is equivalent as v2 <= v1
     * The equivalent node will be removed from the nonEquivalentConditions & added to removedEquivalentConditions so
     * that the value can still be updated when computing the branch predicate.
     *
     * @param nodeSubset subset of the node that has the same ComparisonRelation
     * @param majorSet   set of major we are considering
     * @param relation   the relation for the positionSwappedNode
     */
    private void eliminateEquivalentNodes(HashSet<ConditionNode> nodeSubset, HashSet<ConditionNode> majorSet, ComparisonRelation relation) {
        for (Iterator<ConditionNode> i = nodeSubset.iterator(); i.hasNext(); ) {
            ConditionNode currentNode = i.next();

            ConditionNode positionSwappedNode = new ConditionNode(currentNode.getRightNode(), relation,
                    currentNode.getLeftNode());

            if (majorSet.contains(positionSwappedNode)) {
                if (nodeSubset.contains(positionSwappedNode)) {
                    i.remove();
                }
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


}
