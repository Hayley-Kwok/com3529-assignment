package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.NodeHelper;
import uk.ac.shef.com3529.assignment.model.ConditionNode;
import uk.ac.shef.com3529.assignment.model.VariableNode;
import uk.ac.shef.com3529.assignment.model.enums.ComparisonRelation;

public class ConditionWithExpectedValue {
    private final boolean expectedValue;
    private final VariableNode<?>[] involvedVariables;
    private final ComparisonRelation relation;

    public ConditionWithExpectedValue(ConditionNode condition, boolean expectedValue) {
        this.expectedValue = expectedValue;

        NodeHelper nh = new NodeHelper(condition);
        involvedVariables = nh.getVariables();
        relation = condition.getRelation();
    }

    public boolean getExpectedValue() {
        return expectedValue;
    }

    public VariableNode<?>[] getInvolvedVariables() {
        return involvedVariables;
    }

    public ComparisonRelation getRelation() {
        return relation;
    }
}