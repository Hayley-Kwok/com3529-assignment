package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.HashSet;

public class RandomExample {
    public static boolean RestrictedNotCoveringAllMajors(double a, double b, double c, HashSet<Integer> coveredBranch) {
        if (a == b && b == c ||
                a != b && b == c) {
            return true;
        }
        return false;
    }
}
