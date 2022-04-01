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

public class EquilateralTriangleWithCoveredBranchTest { 
    // Majors: [(side1 == side2), (side2 == side3), (side1 + side2 > side3)]
    // Restricted Test Indices: [3, 5, 6, 7]
    // Correlated Test Indices: [7, 0]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
                    Arguments.of(4, 1, 5, false, new HashSet<Integer>(Arrays.asList(6))), //Test ID 0: [false, false, false, false]
                    Arguments.of(5, 4, 4, false, new HashSet<Integer>(Arrays.asList(1,5))), //Test ID 3: [false, true, true, false]
                    Arguments.of(4, 4, 2, false, new HashSet<Integer>(Arrays.asList(1,2,4))), //Test ID 5: [true, false, true, false]
// The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 6: [true, true, false, false]
                    Arguments.of(3, 3, 3, true, new HashSet<Integer>(Arrays.asList(1,2,3))) //Test ID 7: [true, true, true, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void MCDCTest(int side1, int side2, int side3, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.equilateralClassify(side1, side2, side3, coveredBranches);

        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}