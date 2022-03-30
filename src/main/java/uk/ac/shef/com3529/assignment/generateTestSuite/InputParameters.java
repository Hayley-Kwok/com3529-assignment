package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.BranchPredicate;
import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.*;

public class InputParameters {
    private final BinaryRelatedNode<?> root;
    private final VariableNode<?>[] variables;
    private final HashSet<ConditionNode> allConditions;
    private final ConditionNode[] majors;
    private final HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions;
    private final HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions;
    private final BranchPredicate branchPredicate;

    public InputParameters(BinaryRelatedNode<?> root,
                           VariableNode<?>[] variables,
                           HashSet<ConditionNode> allConditions,
                           ConditionNode[] majors,
                           HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions,
                           HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions) {
        this.root = root;
        this.variables = variables;
        this.allConditions = allConditions;
        this.majors = majors;
        this.removedEquivalentConditions = removedEquivalentConditions;
        this.removedContradictingConditions = removedContradictingConditions;

        branchPredicate = new BranchPredicate(majors, removedEquivalentConditions, removedContradictingConditions, root, allConditions);
    }

    public void updateValueInVariableNodeForRow(ArrayList<Boolean> row) {
        allConditions.forEach(ConditionNode::resetResultOverrode);

        do {
            setRandomValueForVariables();
        } while (checkIfValueSatisfyConditions(row));
    }

    private boolean checkIfValueSatisfyConditions(ArrayList<Boolean> row) {
        for (int i = 0; i < majors.length; i++) {
            if (row.get(i) != majors[i].getResult()) {
                return false;
            }
        }
        for (Map.Entry<String, ArrayList<ConditionNode>> contradictingEntry : removedContradictingConditions.entrySet()) {
            ConditionNode major = Arrays.stream(majors).filter(m -> m.toString().equals(contradictingEntry.getKey())).findFirst().get();
            int majorIndex = Arrays.asList(majors).indexOf(major);
            for (ConditionNode node : contradictingEntry.getValue()) {
                if (row.get(majorIndex) == node.getResult()) {
                    return false;
                }
            }
        }

        return branchPredicate.getResult() == row.get(row.size() - 1);
    }


    private void setRandomValueForVariables() {
        for (VariableNode<?> variable : variables) {
            Random random = new Random();
            if (variable.getType().equals(Integer.class)) {
                int randomInt = random.ints(variable.getMin().intValue(), variable.getMax().intValue()).findFirst().getAsInt();
                variable.setValue(randomInt);
            } else if (variable.getType().equals(Long.class)) {
                long randomLong = random.longs(variable.getMin().longValue(), variable.getMax().longValue()).findFirst().getAsLong();
                variable.setValue(randomLong);
            } else if (variable.getType().equals(Float.class)) {
                float randomFloat = variable.getMin().floatValue() + random.nextFloat() * (variable.getMax().floatValue() - variable.getMin().floatValue());
                variable.setValue(randomFloat);
            } else {
                double randomDouble = random.doubles(variable.getMin().doubleValue(), variable.getMax().doubleValue()).findFirst().getAsDouble();
                variable.setValue(randomDouble);
            }
        }
    }


}
