package uk.ac.shef.com3529.assignment.generateTestSuite.TestCases;

import uk.ac.shef.com3529.assignment.generateTestSuite.CsvSourceInput;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class TestCaseBase {
    protected final HashSet<String> importString;
    protected String testCaseString;
    protected final String methodName;
    protected final VariableNode<?>[] variables;
    protected final ArrayList<CsvSourceInput> inputValues;
    protected final String testFunctionCall;

    public TestCaseBase(HashSet<String> importString, VariableNode<?>[] variables, String methodName,
                        ArrayList<CsvSourceInput> inputValues, String testFunctionCall) {
        this.importString = importString;
        this.variables = variables;
        this.methodName = methodName;
        this.inputValues = inputValues;
        this.testFunctionCall = testFunctionCall;

        addImportString();
    }

    abstract protected void addImportString();

    public abstract String getTestCaseString();
}
