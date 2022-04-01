package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.HashSet;

public class Calendar {
    public static boolean daysBetweenTwoDates(int day1, int day2, int month1,
                                              int month2, int year1, int year2, HashSet<Integer> coveredBranch) {
        /*
           ((year2 < year1) ||
            (year2 == year1 && month2 < month1) ||
            (year2 == year1 && month2 == month1 && day2 < day1))
         */
        boolean result = false;
        if ((year2 < year1)) {
            coveredBranch.add(1);
            result = true;
        } else {
            coveredBranch.add(2);
        }

        if (year2 == year1) {
            coveredBranch.add(3);
            if (month2 < month1) {
                coveredBranch.add(4);
                result = true;
            } else {
                coveredBranch.add(5);
            }
        } else {
            coveredBranch.add(6);
        }

        if (year2 == year1) {
            coveredBranch.add(3);
            if (month2 == month1) {
                coveredBranch.add(7);
                if (day2 < day1) {
                    coveredBranch.add(8);
                    result = true;
                } else {
                    coveredBranch.add(9);
                }
            } else {
                coveredBranch.add(10);
            }
        } else {
            coveredBranch.add(6);
        }

        return result;
    }
}
