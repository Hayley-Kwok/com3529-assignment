package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.RandomExample;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestrictedNotCoveringAllMajorsWithCoveredBranchTest { 
    // Majors: [(a == b), (b == c)]
    // Restricted Test Indices: [0, 1]
    // Correlated Test Indices: [0, 3]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
                    Arguments.of(6.319523f, 15.905655f, 3.3873367f, false, new HashSet<Integer>(Arrays.asList(3,4,5))), //Test ID 0: [false, false, false]
                    Arguments.of(5.7938313f, 7.510496f, 7.510496f, true, new HashSet<Integer>(Arrays.asList(2,4,5))), //Test ID 1: [false, true, true]
                    Arguments.of(13.827506f, 13.827506f, 13.827506f, true, new HashSet<Integer>(Arrays.asList(1,2,6))) //Test ID 3: [true, true, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void MCDCTest(float a, float b, float c, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = RandomExample.RestrictedNotCoveringAllMajors(a, b, c, coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}