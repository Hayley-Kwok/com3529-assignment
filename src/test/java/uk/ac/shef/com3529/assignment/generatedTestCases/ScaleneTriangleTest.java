package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScaleneTriangleTest {
    // Majors: [!(side1 == side2), !(side2 == side3), (side1 + side2 > side3), !(side1 == side3)]

    @ParameterizedTest
    @CsvSource({
            " 5, 5, 7, false",  //Test ID 7: [false, true, true, true, false]
            " 2, 7, 7, false",  //Test ID 11: [true, false, true, true, false]
            " 4, 2, 9, false",  //Test ID 13: [true, true, false, true, false]
            " 6, 5, 6, false",  //Test ID 14: [true, true, true, false, false]
            " 2, 8, 7, true",  //Test ID 15: [true, true, true, true, true]s
    })
    public void restrictedTest(int side1, int side2, int side3, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.scaleneClassify(side1, side2, side3, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}