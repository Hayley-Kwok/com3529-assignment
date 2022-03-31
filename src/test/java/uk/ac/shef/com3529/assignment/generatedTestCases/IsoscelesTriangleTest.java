package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsoscelesTriangleTest {
    // Majors: [(side1 + side2 > side3), (side2 == side3), (side1 == side2)]
    @ParameterizedTest
    @CsvSource({
            " 3, 3, 6, false",  //Test ID 1: [false, false, true, false]
            " 6, 9, 6, false",  //Test ID 4: [true, false, false, false]
            " 5, 5, 4, true",  //Test ID 5: [true, false, true, true]
            " 2, 1, 1, true",  //Test ID 6: [true, true, false, true]
    })
    public void restrictedTest(int side1, int side2, int side3, boolean expected) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = Triangle.isoscelesClassify(side1, side2, side3, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expected, actualBranchResult);
    }
}