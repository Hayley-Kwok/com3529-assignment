package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.Set;

public class Triangle {

    public static boolean isoscelesClassify(int side1, int side2, int side3, Set<Integer> coveredBranches) {
        boolean isIsosceles = false;
        if (side1 + side2 > side3) {
            coveredBranches.add(1);
            if ((side1 == side2) || (side2 == side3)) {
                coveredBranches.add(2);
                if ((side1 != side2) || (side2 != side3)) {
                    coveredBranches.add(3);
                    isIsosceles = true;
                } else {
                    coveredBranches.add(4);
                }
            } else {
                coveredBranches.add(5);
            }
        } else {
            coveredBranches.add(6);
        }
        return isIsosceles;
    }

    public static boolean equilateralClassify(int side1, int side2, int side3, Set<Integer> coveredBranch) {
        boolean isEquilateral = false;
        if (side1 + side2 > side3) {
            coveredBranch.add(1);
            if (side1 == side2) {
                coveredBranch.add(2);
                if (side2 == side3) {
                    coveredBranch.add(3);
                    isEquilateral = true;
                } else {
                    coveredBranch.add(4);
                }
            } else {
                coveredBranch.add(5);
            }
        } else {
            coveredBranch.add(6);
        }
        return isEquilateral;
    }

    public static boolean scaleneClassify(int side1, int side2, int side3, Set<Integer> coveredBranch) {
        boolean isScalene = false;
        if (side1 + side2 > side3) {
            coveredBranch.add(1);
            if (!(side1 == side2)) {
                coveredBranch.add(2);
                if (!(side2 == side3)) {
                    coveredBranch.add(3);
                    if ((!(side1 == side3))) {
                        isScalene = true;
                        coveredBranch.add(4);
                    } else {
                        coveredBranch.add(5);
                    }
                } else {
                    coveredBranch.add(6);
                }
            } else {
                coveredBranch.add(7);
            }
        } else {
            coveredBranch.add(8);
        }
        return isScalene;
    }

    public static boolean invalidClassify(int side1, int side2, int side3, Set<Integer> coveredBranch) {
        if (side1 + side2 <= side3) {
            coveredBranch.add(1);
            return true;
        } else {
            coveredBranch.add(2);
            return false;
        }
    }
}
