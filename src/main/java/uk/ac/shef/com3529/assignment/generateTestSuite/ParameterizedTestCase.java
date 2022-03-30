package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class ParameterizedTestCase {

    private HashSet<String> importString;
    private String testCaseString;
    private String methodName;
    private VariableNode<?>[] variables;
    private Map<Integer, ArrayList<Number>> csvSources;

    public ParameterizedTestCase(HashSet<String> importString, VariableNode<?>[] variables, String methodName,
                                 Map<Integer, ArrayList<Number>> csvSources) {
        this.importString = importString;
        this.variables = variables;
        this.methodName = methodName;
        this.csvSources = csvSources;

        addImportString();
    }

    public String getTestCaseString() {
        if (testCaseString != null) {
            return testCaseString;
        }

        testCaseString =
                "    @ParameterizedTest\n" +
                        "    @CsvSource({" +
                        generateCsvSource() +
                        "                })\n" +
                        "    public void " + methodName + "(int v1, int v2, int v3) {\n" +
                        "        HashSet<Integer> coveredBranches = new HashSet<>();\n" +
                        "        Triangle.instrumentedClassify(v1,v2,v3, coveredBranches);\n" +
                        "\n" +
                        "        HashSet<Integer> expectedCoveredBranches = new HashSet<>(Arrays.asList(2,3,5,7));\n" +
                        "        assertTrue(coveredBranches.containsAll(expectedCoveredBranches));\n" +
                        "    }\n";
        return testCaseString;
    }

    private String generateCsvSource() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Map.Entry<Integer, ArrayList<Number>> csvSource : csvSources.entrySet()) {
            sb.append("                \"");
            for (Number value : csvSource.getValue()) {
                sb.append(" ");
                sb.append(value);
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("\", ");
            sb.append(" //");
            sb.append(csvSource.getKey());
            sb.append("\n");
        }
//        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    private void addImportString() {
        importString.add("import org.junit.jupiter.params.ParameterizedTest;\n");
        importString.add("import org.junit.jupiter.params.provider.CsvSource;\n");
        importString.add("import static org.junit.jupiter.api.Assertions.assertTrue;\n");
        importString.add("import java.util.Arrays;\n");
        importString.add("import java.util.HashSet;\n");
    }

}
