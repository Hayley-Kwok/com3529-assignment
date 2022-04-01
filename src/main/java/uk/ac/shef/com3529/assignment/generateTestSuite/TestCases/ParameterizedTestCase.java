package uk.ac.shef.com3529.assignment.generateTestSuite.TestCases;

import uk.ac.shef.com3529.assignment.generateTestSuite.CsvSourceInput;
import uk.ac.shef.com3529.assignment.generateTestSuite.TestGenerationHelper;
import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.ArrayList;
import java.util.HashSet;

public class ParameterizedTestCase extends TestCaseBase {

    public ParameterizedTestCase(HashSet<String> importString, VariableNode<?>[] variables, String methodName,
                                 ArrayList<CsvSourceInput> inputValues, String testFunctionCall) {
        super(importString, variables, methodName, inputValues, testFunctionCall);
    }

    @Override
    public String getTestCaseString() {
        if (testCaseString != null) {
            return testCaseString;
        }

        testCaseString =
                "    @ParameterizedTest\n" +
                        "    @CsvSource({" +
                        generateCsvSourceValues() +
                        "              })\n" +
                        "    public void " + methodName + "(" + TestGenerationHelper.generateParametersSignature(variables) + ", boolean expectedBranchResult) {\n" +
                        "        HashSet<Integer> coveredBranches = new HashSet<>();\n" +
                        "        boolean actualBranchResult = " + String.format(testFunctionCall, TestGenerationHelper.generateOnlyNameSignature(variables)) +
                        "\n" +
                        "        System.out.println(coveredBranches);\n" +
                        "        assertEquals(expectedBranchResult, actualBranchResult);\n" +
                        "    }\n";
        return testCaseString;
    }

    @Override
    protected void addImportString() {
        importString.add("import org.junit.jupiter.params.ParameterizedTest;");
        importString.add("import org.junit.jupiter.params.provider.CsvSource;");
        importString.add("import static org.junit.jupiter.api.Assertions.assertEquals;");
        importString.add("import java.util.HashSet;");
    }

    private String generateCsvSourceValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (CsvSourceInput input : inputValues) {
            ArrayList<Boolean> truthValues = input.getTruthValues();
            sb.append("                ");

            //generate the input parameters
            if (input.getVariableValues().size() == 0) {
                sb.append("// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.");
            } else {
                sb.append("\"");
                for (Number value : input.getVariableValues()) {
                    sb.append(" ");
                    sb.append(value);
                    sb.append(",");
                }
                sb.append(" ");
                sb.append(truthValues.get(truthValues.size() - 1));

                sb.append("\", ");
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
