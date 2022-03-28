package uk.ac.shef.com3529.assignment;

import java.util.Arrays;

public class EntryPoint {
    public static void main(String[] args) {
        TestRequirements trianglePractical = new TestRequirements(ParsedExamples.getTrianglePractical());
        System.out.println("Predicate under consideration: " + trianglePractical.getRoot());
        System.out.println("All Conditions: " + trianglePractical.getAllConditions());
        System.out.println("All Inputs: " + trianglePractical.getVariables());
        System.out.println("All Majors: " + Arrays.toString(trianglePractical.getMajors()));

        System.out.println("Full Condition Table: " + trianglePractical.getFullConditionTable());
        System.out.println("Restricted MCDC Table:" + trianglePractical.getRestrictedMCDCConditionTable());
        System.out.println("Correlated MCDC Table:" + trianglePractical.getCorrelatedMCDCConditionTable());
    }
}
