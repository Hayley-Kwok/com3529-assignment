package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScaleneTriangleTest {
    // Majors: [!(side1 == side2), !(side2 == side3), (side1 + side2 > side3), !(side1 == side3)]
    // Restricted Test Indices: [7, 11, 13, 14, 15]
    // Correlated Test Indices: [15, 0]

    @ParameterizedTest
    @CsvSource({
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 0: [false, false, false, false, false]
            " 4, 4, 1, false",  //Test ID 7: [false, true, true, true, false]
            " 1, 5, 5, false",  //Test ID 11: [true, false, true, true, false]
            " 3, 5, 8, false",  //Test ID 13: [true, true, false, true, false]
            " 8, 6, 8, false",  //Test ID 14: [true, true, true, false, false]
            " 8, 3, 9, true",  //Test ID 15: [true, true, true, true, true]
    })
    public void MCDCTest(int side1, int side2, int side3, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.scaleneClassify(side1, side2, side3, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}