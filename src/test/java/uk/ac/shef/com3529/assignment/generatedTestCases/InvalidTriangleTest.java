package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidTriangleTest {
    // Majors: [(side1 + side2 <= side3)]

    @ParameterizedTest
    @CsvSource({
            " 3, 9, 6, false",  //Test ID 0: [false, false]
            " 2, 3, 5, true",  //Test ID 1: [true, true]
    })
    public void restrictedTest(int side1, int side2, int side3, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.invalidClassify(side1, side2, side3, coveredBranches);

        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}