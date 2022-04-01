package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsoscelesTriangleWithCoveredBranchTest {
    // Majors: [(side1 + side2 > side3), (side2 == side3), (side1 == side2)]
    // Restricted Test Indices: [1, 4, 5, 6]
    // Correlated Test Indices: [1, 6]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
                    Arguments.of(1, 1, 3, false, new HashSet<Integer>(Arrays.asList(6))), //Test ID 1: [false, false, true, false]
                    Arguments.of(5, 2, 1, false, new HashSet<Integer>(Arrays.asList(1, 5))), //Test ID 4: [true, false, false, false]
                    Arguments.of(7, 7, 5, true, new HashSet<Integer>(Arrays.asList(1, 2, 3))), //Test ID 5: [true, false, true, true]
                    Arguments.of(8, 2, 2, true, new HashSet<Integer>(Arrays.asList(1, 2, 3))) //Test ID 6: [true, true, false, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void MCDCTest(int side1, int side2, int side3, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.isoscelesClassify(side1, side2, side3, coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}