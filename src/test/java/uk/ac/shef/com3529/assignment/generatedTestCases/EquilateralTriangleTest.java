package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquilateralTriangleTest { 
    // Majors: [(side1 == side2), (side2 == side3), (side1 + side2 > side3)]
    // Restricted Test Indices: [3, 5, 6, 7]
    // Correlated Test Indices: [7, 0]

    @ParameterizedTest
    @CsvSource({
                " 2, 3, 5, false",  //Test ID 0: [false, false, false, false]
                " 9, 3, 3, false",  //Test ID 3: [false, true, true, false]
                " 8, 8, 9, false",  //Test ID 5: [true, false, true, false]
                // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 6: [true, true, false, false]
                " 6, 6, 6, true",  //Test ID 7: [true, true, true, true]
              })
    public void MCDCTest(int side1, int side2, int side3, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.equilateralClassify(side1, side2, side3, coveredBranches);

        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}