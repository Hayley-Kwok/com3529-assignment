package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BMIOverweightTest {
    // Majors: [(bmi >= 25.0), (bmi < 30.0)]
    // Restricted Test Indices: [1, 2, 3]
    // Correlated Test Indices: [3, 0]

    @ParameterizedTest
    @CsvSource({
            // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 0: [false, false, false]
            " 24.47442678317393, false",  //Test ID 1: [false, true, false]
            " 30.585724800891292, false",  //Test ID 2: [true, false, false]
            " 26.070355575004086, true",  //Test ID 3: [true, true, true]
    })
    public void MCDCTest(double bmi, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = BMI.overweight(bmi, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}