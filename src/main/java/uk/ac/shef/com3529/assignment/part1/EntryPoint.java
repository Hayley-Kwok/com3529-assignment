package uk.ac.shef.com3529.assignment.part1;

import java.util.Arrays;

public class EntryPoint {
    public static void main(String[] args) {
        TestRequirements trianglePractical = new TestRequirements(ParsedExamples.getTrianglePractical());
        System.out.println("Predicate under consideration: " + trianglePractical.getRoot());
        System.out.println("All Conditions: " + trianglePractical.getAllConditions());
        System.out.println("All Inputs: " + trianglePractical.getVariables());
        System.out.println("All Majors: " + Arrays.toString(trianglePractical.getMajors()));

        System.out.println(trianglePractical.getFullMultiConditionTable());
    }
}
