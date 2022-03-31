package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.Set;

public class Triangle {

    public static String instrumentedClassify(int side1, int side2, int side3, Set<Integer> coveredBranches) {
        String type = "NOT ISOSCELES";
        if (side1 + side2 > side3) {
            coveredBranches.add(1);
            if ((side1 == side2) || (side2 == side3)) {
                coveredBranches.add(2);
                if ((side1 != side2) || (side2 != side3)) {
                    coveredBranches.add(3);
                    type = "ISOSCELES";
                } else {
                    coveredBranches.add(4);
                }
            } else {
                coveredBranches.add(5);
            }
        } else {
            coveredBranches.add(6);
        }
        return type;
    }
}
