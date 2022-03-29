package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BranchPredicate {

    private final ConditionNode[] majors;
    private final HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions;
    private final HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions;
    private final BinaryRelatedNode<?> root;
    private final HashSet<ConditionNode> allConditions;

    public BranchPredicate(ConditionNode[] majors,
                           HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions,
                           HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions,
                           BinaryRelatedNode<?> root,
                           HashSet<ConditionNode> allConditions) {
        this.majors = majors;
        this.removedEquivalentConditions = removedEquivalentConditions;
        this.removedContradictingConditions = removedContradictingConditions;
        this.root = root;
        this.allConditions = allConditions;
    }

    public boolean getResult(ArrayList<? extends Number> variableValues, VariableNode<?>[] variables) {
        allConditions.forEach(ConditionNode::resetResultOverrode);
        for (int i = 0; i < variableValues.size(); i++) {
            variables[i].setValue(variableValues.get(i));
        }
        updateNonMajors();
        return root.getResult();
    }

    public boolean getResult(ArrayList<Boolean> majorValues) {
        allConditions.forEach(ConditionNode::resetResultOverrode);
        setMajors(majors, majorValues);
        updateNonMajors();
        try {
            makeSureUpdateAllConditions(allConditions);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return root.getResult();
    }

    private void setMajors(ConditionNode[] majors, ArrayList<Boolean> row) {
        for (int i = 0; i < majors.length; i++) {
            majors[i].setResult(row.get(i));
        }
    }

    //called after updating all majors
    private void updateNonMajors() {
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
    }

    private void makeSureUpdateAllConditions(HashSet<ConditionNode> allConditions) throws Exception {
        for (ConditionNode node : allConditions) {
            if (!node.getResultOverrode()) {
                throw new Exception("not all node is updated when evaluating branch predicate");
            }
        }
    }
}
