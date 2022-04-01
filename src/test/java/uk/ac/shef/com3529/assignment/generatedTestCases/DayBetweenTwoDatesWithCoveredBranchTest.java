package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Calendar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayBetweenTwoDatesWithCoveredBranchTest { 
    // Majors: [(year2 < year1), (year2 == year1), (day2 < day1), (month2 < month1), (month2 == month1)]
    // Restricted Test Indices: [3, 19, 9, 11, 12, 13]
    // Correlated Test Indices: [19, 12]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 19: [true, false, false, true, true, true]
// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 3: [false, false, false, true, true, false]
                    Arguments.of(11, 20, 4, 4, 1248, 1248, false, new HashSet<Integer>(Arrays.asList(2,3,5,7,9))), //Test ID 9: [false, true, false, false, true, false]
// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 11: [false, true, false, true, true, true]
                    Arguments.of(25, 9, 10, 12, 2052, 2052, false, new HashSet<Integer>(Arrays.asList(2,3,5,10))), //Test ID 12: [false, true, true, false, false, false]
                    Arguments.of(27, 11, 11, 11, 903, 903, true, new HashSet<Integer>(Arrays.asList(2,3,5,7,8))) //Test ID 13: [false, true, true, false, true, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void MCDCTest(int day1, int day2, int month1, int month2, int year1, int year2, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Calendar.daysBetweenTwoDates(day1, day2, month1, month2, year1, year2, coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}