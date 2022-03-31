package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {
    private boolean test(int side1, int side2, int side3) {
        return (((side1 + side2 > side3) && ((side1 == side2) || (side2 == side3))) && ((side1 != side2) || (side2 != side3)));
    }

    @ParameterizedTest
    @CsvSource({
            " 4, 4, 8, false",  //Test ID 1: [false, false, true, false]
            " 6, 4, 8, false",  //Test ID 4: [true, false, false, false]
            " 9, 9, 5, true",  //Test ID 5: [true, false, true, true]
            " 8, 3, 3, true",  //Test ID 6: [true, true, false, true]
    })
    public void restrictedTest(int side1, int side2, int side3, boolean expected) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        String type = Triangle.instrumentedClassify(side1, side2, side3, coveredBranches);

        System.out.println(type);
        System.out.println(coveredBranches);
        assertEquals(expected, test(side1, side2, side3));
    }
}