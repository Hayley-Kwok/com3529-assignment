package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsoscelesTriangleTest { 
    // Majors: [(side1 == side2), (side2 == side3), (side1 + side2 > side3)]
    // Restricted Test Indices: [1, 2, 3, 5]
    // Correlated Test Indices: [2, 5]

    @ParameterizedTest
    @CsvSource({
                " 7, 2, 7, false",  //Test ID 1: [false, false, true, false]
                // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 2: [false, true, false, false]
                " 8, 9, 9, true",  //Test ID 3: [false, true, true, true]
                " 8, 8, 2, true",  //Test ID 5: [true, false, true, true]
              })
    public void MCDCTest(int side1, int side2, int side3, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.isoscelesClassify(side1, side2, side3, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}