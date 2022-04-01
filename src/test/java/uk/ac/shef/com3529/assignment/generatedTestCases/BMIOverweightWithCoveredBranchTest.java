package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BMIOverweightWithCoveredBranchTest {
    // Majors: [(bmi >= 25.0), (bmi < 30.0)]
    // Restricted Test Indices: [1, 2, 3]
    // Correlated Test Indices: [3, 0]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 0: [false, false, false]
                    Arguments.of(19.325084380026365, false, new HashSet<Integer>(Arrays.asList(4))), //Test ID 1: [false, true, false]
                    Arguments.of(32.193617951533, false, new HashSet<Integer>(Arrays.asList(1, 3))), //Test ID 2: [true, false, false]
                    Arguments.of(26.439462923243873, true, new HashSet<Integer>(Arrays.asList(1, 2))) //Test ID 3: [true, true, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void MCDCTest(double bmi, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = BMI.overweight(bmi, coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}