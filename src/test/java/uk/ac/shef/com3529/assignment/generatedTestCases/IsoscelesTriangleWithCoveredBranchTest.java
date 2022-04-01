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
    // Majors: [(side1 == side2), (side2 == side3), (side1 + side2 > side3)]
    // Restricted Test Indices: [1, 2, 3, 5]
    // Correlated Test Indices: [2, 5]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
                    Arguments.of(5, 9, 4, false, new HashSet<Integer>(Arrays.asList(1,7,9))), //Test ID 1: [false, false, true, false]
// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 2: [false, true, false, false]
                    Arguments.of(9, 3, 3, true, new HashSet<Integer>(Arrays.asList(1,3,6,7,8))), //Test ID 3: [false, true, true, true]
                    Arguments.of(8, 8, 1, true, new HashSet<Integer>(Arrays.asList(1,2,4,5,9))) //Test ID 5: [true, false, true, true]
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