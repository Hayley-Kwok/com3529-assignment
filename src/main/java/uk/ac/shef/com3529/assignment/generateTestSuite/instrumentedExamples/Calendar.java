package uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples;

import java.util.HashSet;

public class Calendar {
    public static boolean daysBetweenTwoDates(int day1, int day2, int month1,
                                              int month2, int year1, int year2, HashSet<Integer> coveredBranch) {
        if ((year2 < year1) ||
                (year2 == year1 && month2 < month1) ||
                (year2 == year1 && month2 == month1 && day2 < day1)) {
            return true;
        }
        return false;
    }
}
