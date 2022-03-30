package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriangleTest {
    @ParameterizedTest
    @CsvSource({
            " 8, 5, 6",  //1
            " 6, 6, 1",  //4
            " 16, 13, 19",  //5
            " 19, 8, 19",  //6
    })
    public void restrictedTest(int v1, int v2, int v3) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        Triangle.instrumentedClassify(v1, v2, v3, coveredBranches);

        HashSet<Integer> expectedCoveredBranches = new HashSet<>(Arrays.asList(2, 3, 5, 7));
        assertTrue(coveredBranches.containsAll(expectedCoveredBranches));
    }
}