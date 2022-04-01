package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.api.extension.ExtensionContext;
import java.util.Arrays;
import java.util.stream.Stream;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.RandomExample;
import org.junit.jupiter.params.provider.Arguments;
import java.util.HashSet;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
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
                    Arguments.of(18.782112f, 0.81246376f, 14.497818f, false, new HashSet<Integer>(Arrays.asList(3,4,5))), //Test ID 0: [false, false, false]
                    Arguments.of(19.003794f, 18.150127f, 18.150127f, true, new HashSet<Integer>(Arrays.asList(2,4,5))), //Test ID 1: [false, true, true]
                    Arguments.of(15.312186f, 15.312186f, 15.312186f, true, new HashSet<Integer>(Arrays.asList(1,2,6))) //Test ID 3: [true, true, true]
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