package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.BranchPredicate;
import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class that generate the random values for the input
 */
public class NumberValuesForInputs {
    private static final int numberOfTry = 1000;
    private final VariableNode<?>[] variables;
    private final HashSet<ConditionNode> allConditions;
    private final ConditionNode[] majors;
    private final HashMap<String, ArrayList<ConditionNode>> removedEquivalentConditions;
    private final HashMap<String, ArrayList<ConditionNode>> removedContradictingConditions;
    private final BranchPredicate branchPredicate;

    public NumberValuesForInputs(BinaryRelatedNode<?> root,
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

    /**
     * Update all values in the variables that will satisfy the truth values in the given row
     *
     * @param row arraylist of truth values
     * @return if values that satisfy row is being found after numberOfTry times
     */
    public boolean findValueForVariablesForRow(ArrayList<Boolean> row) {
        allConditions.forEach(ConditionNode::resetResultOverrode);
        ArrayList<ConditionWithExpectedValue> conditionWithExpectedValues = generateConditionWithExpectedValues(row);
        int counter = 0;
        do {
            Arrays.stream(variables).forEach(v -> v.setValueSet(false));

            List<ConditionWithExpectedValue> equalsConditions = conditionWithExpectedValues.stream()
                    .filter(c -> (c.getRelation().equals(ComparisonRelation.EqualsEquals) && c.getExpectedValue() && !c.getCondition().isNegated()) ||
                            (c.getRelation().equals(ComparisonRelation.NotEquals) && !c.getExpectedValue() && !c.getCondition().isNegated()) ||
                            (c.getRelation().equals(ComparisonRelation.EqualsEquals) && !c.getExpectedValue() && c.getCondition().isNegated()) ||
                            (c.getRelation().equals(ComparisonRelation.NotEquals) && c.getExpectedValue() && c.getCondition().isNegated())
                    ).collect(Collectors.toList());

            // set all node that is expected to be equals to the same value. It provides some help to the input value generation
            for (ConditionWithExpectedValue equalsCondition : equalsConditions) {
                Number number = getRandomValueForVariable(
                        equalsCondition.getInvolvedVariables()[0].getType(),
                        equalsCondition.getInvolvedVariables()[0].getMin(),
                        equalsCondition.getInvolvedVariables()[0].getMax());
                Optional<VariableNode<?>> anySetValue = Arrays.stream(equalsCondition.getInvolvedVariables()).filter(VariableNode::isValueSet).findFirst();

                if (anySetValue.isPresent()) {
                    number = anySetValue.get().getValue();
                }

                Number finalNumber = number;
                Arrays.stream(equalsCondition.getInvolvedVariables()).filter(v -> !v.isValueSet()).forEach(c -> c.setValue(finalNumber));
            }

            List<VariableNode<?>> notSetVariables = Arrays.stream(variables).filter(v -> !v.isValueSet()).collect(Collectors.toList());
            for (VariableNode<?> variable : notSetVariables) {
                Number number = getRandomValueForVariable(variable.getType(), variable.getMin(), variable.getMax());
                variable.setValue(number);
            }
            counter++;
        } while (!checkIfValuesSatisfyCondition(conditionWithExpectedValues, row.get(row.size() - 1)) && counter < numberOfTry);
        return counter < numberOfTry;
    }

    /**
     * Transform all of the conditions to a list of ConditionWithExpectedValue to facilitate the random value generation
     *
     * @param row arraylist of truth values
     * @return the corresponding list of ConditionWithExpectedValue
     */
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

        for (Map.Entry<String, ArrayList<ConditionNode>> equivalentEntry : removedEquivalentConditions.entrySet()) {
            ConditionNode major = Arrays.stream(majors).filter(m -> m.toString().equals(equivalentEntry.getKey())).findFirst().get();
            int majorIndex = Arrays.asList(majors).indexOf(major);
            for (ConditionNode node : equivalentEntry.getValue()) {
                conditionWithExpectedValues.add(new ConditionWithExpectedValue(node, row.get(majorIndex)));
            }
        }
        return conditionWithExpectedValues;
    }

    /**
     * check if the current values in variables satisfy the expected result
     *
     * @param conditionWithExpectedValues ArrayList of ConditionWithExpectedValue
     * @param branchResult                expected result for the entire branch
     * @return true if all values satisfy all conditions
     */
    private boolean checkIfValuesSatisfyCondition(ArrayList<ConditionWithExpectedValue> conditionWithExpectedValues, boolean branchResult) {
        for (ConditionWithExpectedValue conditionWithExpectedValue : conditionWithExpectedValues) {
            if (conditionWithExpectedValue.getCondition().getResult() != conditionWithExpectedValue.getExpectedValue()) {
                return false;
            }
        }

        return branchPredicate.getResult() == branchResult;
    }

    /**
     * Generate a random number with the min, max and type constraints
     *
     * @param type type of number
     * @param min  min of number
     * @param max  max of number
     * @return the random number
     */
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
