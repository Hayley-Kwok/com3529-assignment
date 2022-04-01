package uk.ac.shef.com3529.assignment.generateTestSuite;

import java.util.ArrayList;

/**
 * Data object for storing the information for each test case
 */
public class CsvSourceInput {
    private final ArrayList<Boolean> truthValues;
    private final int testIndex;
    private final ArrayList<Number> variableValues;

    public CsvSourceInput(ArrayList<Boolean> truthValuesForRow, int testIndex, ArrayList<Number> variableValues) {
        this.truthValues = truthValuesForRow;
        this.testIndex = testIndex;
        this.variableValues = variableValues;
    }

    public ArrayList<Boolean> getTruthValues() {
        return truthValues;
    }

    public int getTestIndex() {
        return testIndex;
    }

    public ArrayList<Number> getVariableValues() {
        return variableValues;
    }
}