package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.generateTestSuite.TestSuite;

import java.util.Arrays;
import java.util.Collections;

public class EntryPoint {
    public static void main(String[] args) {
        TestRequirements trianglePractical = new TestRequirements(ParsedExamples.getTrianglePractical());
        System.out.println("Predicate under consideration: " + trianglePractical.getRoot());
        System.out.println("All Conditions: " + trianglePractical.getAllConditions());
        System.out.println("All Inputs: " + Arrays.toString(trianglePractical.getVariables()));
        System.out.println("All Majors: " + Arrays.toString(trianglePractical.getMajors()));

        System.out.println("Full Condition Table: " + trianglePractical.getFullConditionTable());
        System.out.println("Restricted MCDC Table:" + trianglePractical.getRestrictedMCDCConditionTable());
        System.out.println("Correlated MCDC Table:" + trianglePractical.getCorrelatedMCDCConditionTable());
        TestSuite testSuite = new TestSuite("Triangle",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.instrumentedClassify(%s,%s,%s, coveredBranches);",
                Collections.singletonList(trianglePractical));

    }
}
