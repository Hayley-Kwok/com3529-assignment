package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.BranchPredicate;
import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

import java.util.*;
import java.util.stream.Collectors;

public class InputParameters {
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
        this.variables = variables;
        this.allConditions = allConditions;
        this.majors = majors;
        this.removedEquivalentConditions = removedEquivalentConditions;
        this.removedContradictingConditions = removedContradictingConditions;

        branchPredicate = new BranchPredicate(majors, removedEquivalentConditions, removedContradictingConditions, root, allConditions);
    }

    public boolean findValueForVariablesForRow(ArrayList<Boolean> row) {
        allConditions.forEach(ConditionNode::resetResultOverrode);
        ArrayList<ConditionWithExpectedValue> conditionWithExpectedValues = generateConditionWithExpectedValues(row);
        int counter = 0;
        do {
            Arrays.stream(variables).forEach(v -> v.setValueSet(false));

            List<ConditionWithExpectedValue> equalsConditions = conditionWithExpectedValues.stream()
                    .filter(c -> (c.getRelation().equals(ComparisonRelation.EqualsEquals) && c.getExpectedValue()) ||
                            (c.getRelation().equals(ComparisonRelation.NotEquals) && !c.getExpectedValue()))
                    .collect(Collectors.toList());

            for (ConditionWithExpectedValue equalsCondition : equalsConditions) {
                Number number = getRandomValueForVariable(
                        equalsCondition.getInvolvedVariables()[0].getType(),
                        equalsCondition.getInvolvedVariables()[0].getMin(),
                        equalsCondition.getInvolvedVariables()[0].getMax());
                Arrays.stream(equalsCondition.getInvolvedVariables()).filter(v -> !v.isValueSet()).forEach(c -> c.setValue(number));
            }

            List<VariableNode<?>> notSetVariables = Arrays.stream(variables).filter(v -> !v.isValueSet()).collect(Collectors.toList());
            for (VariableNode<?> variable : notSetVariables) {
                Number number = getRandomValueForVariable(variable.getType(), variable.getMin(), variable.getMax());
                variable.setValue(number);
            }
            counter++;
        } while (!checkIfValuesSatisfyCondition(row) && counter < 1000);
        System.out.println(counter);
        return counter < 1000;
    }

    private ArrayList<ConditionWithExpectedValue> generateConditionWithExpectedValues(ArrayList<Boolean> row) {
        ArrayList<ConditionWithExpectedValue> conditionWithExpectedValues = new ArrayList<>();
        for (int i = 0; i < majors.length; i++) {
            conditionWithExpectedValues.add(new ConditionWithExpectedValue(majors[i], row.get(i)));
        }
        for (Map.Entry<String, ArrayList<ConditionNode>> contradictingEntry : removedContradictingConditions.entrySet()) {
            ConditionNode major = Arrays.stream(majors).filter(m -> m.toString().equals(contradictingEntry.getKey())).findFirst().get();
            int majorIndex = Arrays.asList(majors).indexOf(major);
            for (ConditionNode node : contradictingEntry.getValue()) {
                conditionWithExpectedValues.add(new ConditionWithExpectedValue(node, !row.get(majorIndex)));
            }
        }
        //todo add equivalent as well
        return conditionWithExpectedValues;
    }

    private boolean checkIfValuesSatisfyCondition(ArrayList<Boolean> row) {
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

        //todo add equivalent as well

        return branchPredicate.getResult() == row.get(row.size() - 1);
    }

    private Number getRandomValueForVariable(Class<?> type, Number min, Number max) {
        Random random = new Random();
        if (type.equals(Integer.class)) {
            return random.ints(min.intValue(), max.intValue()).findFirst().getAsInt();
        } else if (type.equals(Long.class)) {
            return random.longs(min.longValue(), max.longValue()).findFirst().getAsLong();
        } else if (type.equals(Float.class)) {
            return min.floatValue() + random.nextFloat() * (max.floatValue() - min.floatValue());
        } else {
            return random.doubles(min.doubleValue(), max.doubleValue()).findFirst().getAsDouble();
        }
    }
}
