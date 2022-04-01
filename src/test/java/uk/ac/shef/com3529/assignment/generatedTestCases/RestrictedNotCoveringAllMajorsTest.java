package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.RandomExample;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestrictedNotCoveringAllMajorsTest {
    // Majors: [(a == b), (b == c)]
    // Restricted Test Indices: [0, 1]
    // Correlated Test Indices: [0, 3]

    @ParameterizedTest
    @CsvSource({
            " 18.92371, 14.1058235, 11.340123, false",  //Test ID 0: [false, false, false]
            " 6.536007, 14.582566, 14.582566, true",  //Test ID 1: [false, true, true]
            " 15.36295, 15.36295, 15.36295, true",  //Test ID 3: [true, true, true]
    })
    public void MCDCTest(float a, float b, float c, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = RandomExample.RestrictedNotCoveringAllMajors(a, b, c, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}