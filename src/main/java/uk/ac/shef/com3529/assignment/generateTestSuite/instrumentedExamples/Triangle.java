package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.Set;

public class Triangle {

    public static String instrumentedClassify(int side1, int side2, int side3, Set<Integer> coveredBranches) {
        String type;
        if (side1 > side2) {
            coveredBranches.add(1);
            int temp = side1;
            side1 = side2;
            side2 = temp;
        } else {
            coveredBranches.add(2);
        }

        if (side1 > side3) {
            coveredBranches.add(3);
            int temp = side1;
            side1 = side3;
            side3 = temp;
        } else {
            coveredBranches.add(4);
        }

        if (side2 > side3) {
            coveredBranches.add(5);
            int temp = side2;
            side2 = side3;
            side3 = temp;
        } else {
            coveredBranches.add(6);
        }

        if (side1 + side2 <= side3) {
            coveredBranches.add(7);
            type = "INVALID";
        } else {
            coveredBranches.add(8);
            type = "SCALENE";
            if (side1 == side2) {
                coveredBranches.add(9);
                if (side2 == side3) {
                    coveredBranches.add(10);
                    type = "EQUILATERAL";
                } else {
                    coveredBranches.add(11);
                }
            } else {
                coveredBranches.add(12);
                if (side2 == side3) {
                    coveredBranches.add(13);
                    type = "ISOSCELES";
                } else {
                    coveredBranches.add(14);
                }
            }
        }
        return type;
    }
}
