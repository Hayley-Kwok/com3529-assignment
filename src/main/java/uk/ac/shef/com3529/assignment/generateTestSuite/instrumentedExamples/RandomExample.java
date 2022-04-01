package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.HashSet;

public class RandomExample {
    public static boolean RestrictedNotCoveringAllMajors(float a, float b, float c, HashSet<Integer> coveredBranch) {
        /*
            (a == b && b == c ||
             a != b && b == c)
         */
        boolean result = false;
        if (a == b) {
            coveredBranch.add(1);
            if (b == c) {
                coveredBranch.add(2);
                result = true;
            } else {
                coveredBranch.add(3);
            }
        } else {
            coveredBranch.add(4);
        }

        if (a != b) {
            coveredBranch.add(5);
            if (b == c) {
                coveredBranch.add(2);
                result = true;
            } else {
                coveredBranch.add(3);
            }
        } else {
            coveredBranch.add(6);
        }
        return result;
    }
}
