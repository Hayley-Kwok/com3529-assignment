package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.generateTestSuite.TestSuite;
import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class EntryPoint {
    public static void main(String[] args) {
        TestRequirements requirements = generateTestRequirements(ParsedExamples.getTrianglePractical());
        TestSuite testSuite = new TestSuite("Triangle",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.instrumentedClassify(%s,%s,%s, coveredBranches);",
                Collections.singletonList(requirements));

    }

    private static TestRequirements generateTestRequirements(BinaryRelatedNode<?> root) {
        TestRequirements requirements = new TestRequirements(root);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Branch Predicate under consideration: " + requirements.getRoot());
        System.out.println("All Conditions: " + requirements.getAllConditions());
        System.out.println("Inputs: " + Arrays.toString(requirements.getVariables()));
        System.out.println("Majors: " + Arrays.toString(requirements.getMajors()));

        System.out.println("Full Condition Table: \n\t" + prettyPrintConditionTable(requirements.getFullConditionTable()));
        System.out.println("Restricted MCDC Test Indices: " + requirements.getRestrictedTestIndices());
        System.out.println("Restricted MCDC Table: \n\t" + prettyPrintConditionTable(requirements.getRestrictedMCDCConditionTable()));
        System.out.println("Correlated MCDC Test Indices: " + requirements.getCorrelatedTestIndices());
        System.out.println("Correlated MCDC Table: \n\t" + prettyPrintConditionTable(requirements.getCorrelatedMCDCConditionTable()));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        return requirements;
    }

    private static String prettyPrintConditionTable(ArrayList<ArrayList<Boolean>> table) {
        return table.stream().map(Object::toString).collect(Collectors.joining("\n\t"));
    }
}
