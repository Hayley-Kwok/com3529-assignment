package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Calendar;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayBetweenTwoDatesTest {
    // Majors: [(day2 < day1), (year2 < year1), (year2 == year1), (month2 == month1), (month2 < month1)]
    // Restricted Test Indices: [3, 20, 6, 22, 7, 11]
    // Correlated Test Indices: [20, 11]

    @ParameterizedTest
    @CsvSource({
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 3: [false, false, false, true, true, false]
            " 29, 12, 1, 2, 82, 82, false",  //Test ID 20: [true, false, true, false, false, false]
            " 5, 29, 12, 12, 1205, 1205, false",  //Test ID 6: [false, false, true, true, false, false]
            " 17, 11, 5, 5, 911, 911, true",  //Test ID 22: [true, false, true, true, false, true]
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 7: [false, false, true, true, true, true]
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 11: [false, true, false, true, true, true]
    })
    public void MCDCTest(int day1, int day2, int month1, int month2, int year1, int year2, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Calendar.daysBetweenTwoDates(day1, day2, month1, month2, year1, year2, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}