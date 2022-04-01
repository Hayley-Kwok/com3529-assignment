package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.HashSet;

public class BMI {
    public static boolean underWeight(double bmi, HashSet<Integer> coveredBranch) {
        boolean result = false;
        if (bmi < 18.5) {
            coveredBranch.add(1);
            result = true;
        } else {
            coveredBranch.add(2);
        }
        return result;
    }

    public static boolean normal(double bmi, HashSet<Integer> coveredBranch) {
        boolean result = false;
        if (bmi >= 18.5) {
            coveredBranch.add(1);
            if (bmi < 25) {
                coveredBranch.add(2);
                result = true;
            } else {
                coveredBranch.add(3);
            }
        } else {
            coveredBranch.add(4);
        }

        return result;
    }

    public static boolean overweight(double bmi, HashSet<Integer> coveredBranch) {
        boolean result = false;
        if (bmi >= 25) {
            coveredBranch.add(1);
            if (bmi < 30) {
                coveredBranch.add(2);
                result = true;
            } else {
                coveredBranch.add(3);
            }
        } else {
            coveredBranch.add(4);
        }

        return result;
    }

    public static boolean obese(double bmi, HashSet<Integer> coveredBranch) {
        boolean result = false;
        if (bmi >= 30) {
            coveredBranch.add(1);
            result = true;
        } else {
            coveredBranch.add(2);
        }

        return result;
    }

}
