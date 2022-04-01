package uk.ac.shef.com3529.assignment.generateTestSuite.TestCases;

import uk.ac.shef.com3529.assignment.generateTestSuite.CsvSourceInput;
import uk.ac.shef.com3529.assignment.generateTestSuite.TestGenerationHelper;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.ArrayList;
import java.util.HashSet;

public class TestCaseWithCoveredBranch extends TestCaseBase {
    private static final String argumentProviderName = "TestArgumentsProvider";

    public TestCaseWithCoveredBranch(HashSet<String> importString, VariableNode<?>[] variables, String methodName, ArrayList<CsvSourceInput> inputValues, String testFunctionCall) {
        super(importString, variables, methodName, inputValues, testFunctionCall);
    }

    @Override
    protected void addImportString() {
        importString.add("import static org.junit.jupiter.api.Assertions.assertEquals;");
        importString.add("import java.util.Arrays;");
        importString.add("import java.util.HashSet;");
        importString.add("import java.util.stream.Stream;");
        importString.add("import org.junit.jupiter.api.extension.ExtensionContext;");
        importString.add("import org.junit.jupiter.params.ParameterizedTest;");
        importString.add("import org.junit.jupiter.params.provider.Arguments;");
        importString.add("import org.junit.jupiter.params.provider.ArgumentsProvider;");
        importString.add("import org.junit.jupiter.params.provider.ArgumentsSource;");
    }

    @Override
    public String getTestCaseString() {
        if (testCaseString != null) {
            return testCaseString;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("    static class " + argumentProviderName + " implements ArgumentsProvider {\n")
                .append("        @Override\n")
                .append("        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {\n")
                .append("            return Stream.of(\n")
                .append("                    //TODO add values to the set for expected covered branch\n")
                .append(generateArgumentsValues())
                .append("            );\n")
                .append("        }\n")
                .append("    }\n\n");


        sb.append("    @ParameterizedTest\n")
                .append("    @ArgumentsSource(" + argumentProviderName + ".class)\n")
                .append("    public void ").append(methodName).append("(").append(TestGenerationHelper.generateParametersSignature(variables)).append(", boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {\n")
                .append("        HashSet<Integer> coveredBranches = new HashSet<>();\n")
                .append("        boolean actualBranchResult = ").append(String.format(testFunctionCall, TestGenerationHelper.generateOnlyNameSignature(variables))).append("\n")
                .append("        assertEquals(expectedBranchResult, actualBranchResult);\n")
                .append("        assertEquals(expectedCoveredBranches, coveredBranches);\n")
                .append("    }\n");
        testCaseString = sb.toString();
        return testCaseString;
    }

    private String generateArgumentsValues() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputValues.size(); i++) {
            CsvSourceInput input = inputValues.get(i);
            ArrayList<Boolean> truthValues = input.getTruthValues();

            //generate the input parameters
            if (input.getVariableValues().size() == 0) {
                sb.append("// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.");
            } else {
                sb.append("                    Arguments.of(");
                for (Number value : input.getVariableValues()) {
                    sb.append(value);
                    if (value instanceof Float) {
                        sb.append("f");
                    }
                    sb.append(", ");
                }

                sb.append(truthValues.get(truthValues.size() - 1));
                sb.append(", new HashSet<Integer>(Arrays.asList()))");
                if (i < inputValues.size() - 1) {
                    sb.append(",");
                }
                sb.append(" //");
            }
            //add the test information for this input
            sb.append("Test ID ");
            sb.append(input.getTestIndex());
            sb.append(": ");
            sb.append(truthValues);
            sb.append("\n");
        }
        return sb.toString();
    }
}
