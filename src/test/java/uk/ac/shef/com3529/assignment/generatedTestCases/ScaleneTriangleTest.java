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
            " 7, 7, 2, false",  //Test ID 7: [false, true, true, true, false]
            " 6, 3, 3, false",  //Test ID 11: [true, false, true, true, false]
            " 5, 1, 6, false",  //Test ID 13: [true, true, false, true, false]
            " 4, 8, 4, false",  //Test ID 14: [true, true, true, false, false]
            " 8, 6, 7, true",  //Test ID 15: [true, true, true, true, true]
    })
    public void restrictedTest(int side1, int side2, int side3, boolean expected) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.scaleneClassify(side1, side2, side3, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expected, actualBranchResult);
    }
}