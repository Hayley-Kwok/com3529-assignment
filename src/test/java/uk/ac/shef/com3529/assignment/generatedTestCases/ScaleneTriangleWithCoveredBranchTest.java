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

public class ScaleneTriangleWithCoveredBranchTest {
    // Majors: [!(side1 == side2), !(side2 == side3), (side1 + side2 > side3), !(side1 == side3)]

    static class TestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    //TODO add values to the set for expected covered branch
                    Arguments.of(9, 9, 8, false, new HashSet<Integer>(Arrays.asList(1, 7))), //Test ID 7: [false, true, true, true, false]
                    Arguments.of(6, 9, 9, false, new HashSet<Integer>(Arrays.asList(1, 2, 6))), //Test ID 11: [true, false, true, true, false]
                    Arguments.of(1, 4, 6, false, new HashSet<Integer>(Arrays.asList(8))), //Test ID 13: [true, true, false, true, false]
                    Arguments.of(6, 8, 6, false, new HashSet<Integer>(Arrays.asList(1, 2, 3, 5))), //Test ID 14: [true, true, true, false, false]
                    Arguments.of(1, 9, 4, true, new HashSet<Integer>(Arrays.asList(1, 2, 3, 4))) //Test ID 15: [true, true, true, true, true]
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void restrictedTest(int side1, int side2, int side3, boolean expectedBranchResult, HashSet<Integer> expectedCoveredBranches) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.scaleneClassify(side1, side2, side3, coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
        assertEquals(expectedCoveredBranches, coveredBranches);
    }
}