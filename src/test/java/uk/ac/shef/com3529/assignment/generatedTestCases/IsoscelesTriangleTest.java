package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsoscelesTriangleTest {
    // Majors: [(side1 + side2 > side3), (side2 == side3), (side1 == side2)]
    // Restricted Test Indices: [1, 4, 5, 6]
    // Correlated Test Indices: [1, 6]

    @ParameterizedTest
    @CsvSource({
            " 1, 1, 4, false",  //Test ID 1: [false, false, true, false]
            " 5, 7, 4, false",  //Test ID 4: [true, false, false, false]
            " 3, 3, 5, true",  //Test ID 5: [true, false, true, true]
            " 8, 7, 7, true",  //Test ID 6: [true, true, false, true]
    })
    public void MCDCTest(int side1, int side2, int side3, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.isoscelesClassify(side1, side2, side3, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}