package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.TestRequirements;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//1 TestRequirement -> 1 Branch Predicate -> 1 TestCase
public class TestSuite {
    private static final String Package = "package uk.ac.shef.com3529.assignment.generatedTestCases;\n\n";
    private static final String OutputDirectory = "src/test/java/uk/ac/shef/com3529/assignment/generatedTestCases";
    private static final String FilePath = "/%s.java";
    private static final String ClassDefinition = "public class %s { \n";

    private final String className;
    private final String testFunctionCall;

    private HashSet<String> importString = new HashSet<>();
    private List<TestRequirements> requirements;
    private ArrayList<String> testCases = new ArrayList<>();

    public TestSuite(String className, String importStatementForTestFunction, String testFunctionCall, List<TestRequirements> testRequirements) {
        this.className = className + "Test";
        this.testFunctionCall = testFunctionCall;

        importString.add(importStatementForTestFunction);
        requirements = testRequirements;

        generateTestCases();
        writeToFile();
    }

    private void generateTestCases() {
        for (TestRequirements requirement : requirements) {
            generateParameterizedTestCase(requirement);
        }
    }

    private void generateParameterizedTestCase(TestRequirements requirement) {
        ArrayList<CsvSourceInput> inputs = new ArrayList<>();
        InputParameters parameters = new InputParameters(requirement.getRoot(),
                requirement.getVariables(),
                requirement.getAllConditions(),
                requirement.getMajors(),
                requirement.getRemovedEquivalentConditions(),
                requirement.getRemovedContradictingConditions());

        for (int testIndex : requirement.getRestrictedTestIndices()) {
            boolean feasible = parameters.findValueForVariablesForRow(requirement.getFullConditionTable().get(testIndex));

            ArrayList<Number> values = new ArrayList<>();
            if (feasible) {
                for (VariableNode<?> variableNode : requirement.getVariables()) {
                    values.add(variableNode.getValue());
                }
            }
            inputs.add(new CsvSourceInput(requirement.getFullConditionTable().get(testIndex), testIndex, values));
        }
        ParameterizedTestCase testCase =
                new ParameterizedTestCase(importString, requirement.getVariables(), "restrictedTest", inputs);
        testCases.add(testCase.getTestCaseString());
    }

    private void writeToFile() {
        try {
            FileWriter writer = new FileWriter(getOutputFilePath());
            StringBuilder sb = new StringBuilder();
            sb.append(Package);
            sb.append(String.join("", importString));
            sb.append("\n\n");
            sb.append(String.format(ClassDefinition, className));
            sb.append(
                    "    private boolean test(" + TestGenerationHelper.generateParametersSignature(requirements.get(0).getVariables()) + ") {\n" +
                            "        return " + requirements.get(0).getRoot().toString() + ";\n" +
                            "    }\n\n"
            );
            sb.append(String.join("\n", testCases));
            sb.append("}");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
