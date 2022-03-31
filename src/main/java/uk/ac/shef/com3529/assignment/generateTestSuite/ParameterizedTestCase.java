package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.ArrayList;
import java.util.HashSet;

public class ParameterizedTestCase {

    private final HashSet<String> importString;
    private String testCaseString;
    private final String methodName;
    private final VariableNode<?>[] variables;
    private final ArrayList<CsvSourceInput> inputValues;

    public ParameterizedTestCase(HashSet<String> importString, VariableNode<?>[] variables, String methodName,
                                 ArrayList<CsvSourceInput> inputValues) {
        this.importString = importString;
        this.variables = variables;
        this.methodName = methodName;
        this.inputValues = inputValues;

        addImportString();
    }

    public String getTestCaseString() {
        if (testCaseString != null) {
            return testCaseString;
        }

        testCaseString =
                "    @ParameterizedTest\n" +
                        "    @CsvSource({" +
                        generateCsvSourceValues() +
                        "              })\n" +
                        "    public void " + methodName + "(" + TestGenerationHelper.generateParametersSignature(variables) + ", boolean expected) {\n" +
                        "        HashSet<Integer> coveredBranches = new HashSet<>();\n" +
                        "        String type = Triangle.instrumentedClassify(side1, side2, side3, coveredBranches);\n" +
                        "\n" +
                        "        System.out.println(type);\n" +
                        "        System.out.println(coveredBranches);\n" +
//                        "        HashSet<Integer> expectedCoveredBranches = new HashSet<>(Arrays.asList(2,3,5,7));\n" +
//                        "        assertTrue(coveredBranches.containsAll(expectedCoveredBranches));\n" +
                        "        assertEquals(expected, test(side1, side2, side3));\n" +
                        "    }\n";
        return testCaseString;
    }

    private String generateCsvSourceValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (CsvSourceInput input : inputValues) {
            ArrayList<Boolean> truthValues = input.getTruthValues();
            sb.append("                ");

            //generate the input parameters
            if (input.getVariableValues().size() == 0) {
                sb.append("// the program cannot find the input that satisfy this test requirement. Try rerunning the program. ");
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

    private void addImportString() {
        importString.add("import org.junit.jupiter.params.ParameterizedTest;\n");
        importString.add("import org.junit.jupiter.params.provider.CsvSource;\n");
        importString.add("import static org.junit.jupiter.api.Assertions.assertEquals;\n");
        importString.add("import java.util.Arrays;\n");
        importString.add("import java.util.HashSet;\n");
    }

}
