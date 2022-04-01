package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Calendar;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayBetweenTwoDatesTest {
    // Majors: [(year2 < year1), (year2 == year1), (day2 < day1), (month2 < month1), (month2 == month1)]
    // Restricted Test Indices: [3, 19, 9, 11, 12, 13]
    // Correlated Test Indices: [19, 12]

    @ParameterizedTest
    @CsvSource({
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 19: [true, false, false, true, true, true]
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 3: [false, false, false, true, true, false]
            " 10, 19, 8, 8, 501, 501, false",  //Test ID 9: [false, true, false, false, true, false]
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 11: [false, true, false, true, true, true]
            " 16, 14, 3, 6, 2594, 2594, false",  //Test ID 12: [false, true, true, false, false, false]
            " 24, 22, 5, 5, 2855, 2855, true",  //Test ID 13: [false, true, true, false, true, true]
    })
    public void MCDCTest(int day1, int day2, int month1, int month2, int year1, int year2, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Calendar.daysBetweenTwoDates(day1, day2, month1, month2, year1, year2, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}