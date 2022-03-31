package uk.ac.shef.com3529.assignment.generatedTestCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

public class InvalidTriangleTest { 
// Majors: [(side1 + side2 <= side3)]
    @ParameterizedTest
    @CsvSource({
                " 7, 4, 4, false",  //Test ID 0: [false, false]
                " 3, 1, 4, true",  //Test ID 1: [true, true]
              })
    public void restrictedTest(int side1, int side2, int side3, boolean expected) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult =Triangle.invalidClassify(side1, side2, side3, coveredBranches);

        System.out.println(coveredBranches);
        assertEquals(expected, actualBranchResult);
    }
}