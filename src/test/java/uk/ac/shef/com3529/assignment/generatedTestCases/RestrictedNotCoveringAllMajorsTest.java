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
            " 6.298805074828621, 17.704119209181698, 3.362606315476382, false",  //Test ID 0: [false, false, false]
            " 9.870997815188465, 13.123437479781913, 13.123437479781913, true",  //Test ID 1: [false, true, true]
            " 13.158854126780495, 13.158854126780495, 13.158854126780495, true",  //Test ID 3: [true, true, true]
    })
    public void MCDCTest(double a, double b, double c, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = RandomExample.RestrictedNotCoveringAllMajors(a, b, c, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}