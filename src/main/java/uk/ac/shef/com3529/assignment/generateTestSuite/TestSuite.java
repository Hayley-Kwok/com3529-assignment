package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.TestRequirements;
import uk.ac.shef.com3529.assignment.generateTestSuite.TestCases.ParameterizedTestCase;
import uk.ac.shef.com3529.assignment.generateTestSuite.TestCases.TestCaseWithCoveredBranch;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

//1 TestRequirement -> 1 Branch Predicate -> 1 TestCase
public class TestSuite {
    private static final String Package = "package uk.ac.shef.com3529.assignment.generatedTestCases;\n\n";
    private static final String OutputDirectory = "src/test/java/uk/ac/shef/com3529/assignment/generatedTestCases";
    private static final String FilePath = "/%s.java";
    private static final String ClassDefinition = "public class %s { \n";
    private static final String generatedTestMethodName = "MCDCTest";

    private final String className;
    private final String testFunctionCall;
    private final TestRequirements requirements;
    private final HashSet<String> importString = new HashSet<>();
    private final ArrayList<String> testCases = new ArrayList<>();

    public TestSuite(String className, String importStatementForTestFunction, String testFunctionCall, TestRequirements testRequirements, boolean generateCoverBranchField) {
        this.className = className + "Test";
        this.testFunctionCall = testFunctionCall;

        importString.add(importStatementForTestFunction);
        requirements = testRequirements;

        if (generateCoverBranchField) {
            generateCoveredBranchTestCase();
        } else {
            generateParameterizedTestCase();
        }
    }

    public void writeToFile() {
        try {
            FileWriter writer = new FileWriter(getOutputFilePath());
            StringBuilder sb = new StringBuilder();
            sb.append(Package);
            sb.append(String.join("\n", importString));
            sb.append("\n\n");
            sb.append(String.format(ClassDefinition, className));
            sb.append("    // Majors: " + Arrays.toString(requirements.getMajors()) + "\n");
            sb.append("    // Restricted Test Indices: " + requirements.getRestrictedTestIndices() + "\n");
            sb.append("    // Correlated Test Indices: " + requirements.getCorrelatedTestIndices() + "\n\n");
            sb.append(String.join("\n", testCases));
            sb.append("}");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateCoveredBranchTestCase() {
        ArrayList<CsvSourceInput> inputs = generateInputs();
        TestCaseWithCoveredBranch testCase = new TestCaseWithCoveredBranch(importString, requirements.getVariables(), generatedTestMethodName, inputs, testFunctionCall);
        testCases.add(testCase.getTestCaseString());
    }

    private void generateParameterizedTestCase() {
        ArrayList<CsvSourceInput> inputs = generateInputs();
        ParameterizedTestCase testCase =
                new ParameterizedTestCase(importString, requirements.getVariables(), generatedTestMethodName, inputs, testFunctionCall);
        testCases.add(testCase.getTestCaseString());
    }

    private ArrayList<CsvSourceInput> generateInputs() {
        ArrayList<CsvSourceInput> inputs = new ArrayList<>();
        NumberValuesForInputs parameters = new NumberValuesForInputs(
                requirements.getRoot(),
                requirements.getVariables(),
                requirements.getAllConditions(),
                requirements.getMajors(),
                requirements.getRemovedEquivalentConditions(),
                requirements.getRemovedContradictingConditions());

        HashSet<Integer> requiredTestIndices = new HashSet<>();
        requiredTestIndices.addAll(requirements.getCorrelatedTestIndices());
        requiredTestIndices.addAll(requirements.getRestrictedTestIndices());
        for (int testIndex : requiredTestIndices) {
            boolean feasible = parameters.findValueForVariablesForRow(requirements.getFullConditionTable().get(testIndex));

            ArrayList<Number> values = new ArrayList<>();
            if (feasible) {
                for (VariableNode<?> variableNode : requirements.getVariables()) {
                    values.add(variableNode.getValue());
                }
            }
            inputs.add(new CsvSourceInput(requirements.getFullConditionTable().get(testIndex), testIndex, values));
        }
        return inputs;
    }

    private String getOutputFilePath() {
        File file = new File(OutputDirectory);
        if (!file.exists()) {
            file.mkdir();
        }
        String filepath = String.format(FilePath, className);
        return file.getAbsolutePath() + filepath;
    }
}
