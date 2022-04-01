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

public class BMIObeseWithCoveredBranchTest {
    // Majors: [(bmi >= 30.0)]
    // Restricted Test Indices: [0, 1]
    // Correlated Test Indices: [0, 1]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
                    Arguments.of(22.192497933689964, false, new HashSet<Integer>(Arrays.asList(2))), //Test ID 0: [false, false]
                    Arguments.of(40.444360299108546, true, new HashSet<Integer>(Arrays.asList(1))) //Test ID 1: [true, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void MCDCTest(double bmi, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = BMI.obese(bmi, coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}