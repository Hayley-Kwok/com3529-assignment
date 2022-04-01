package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BMIOverweightTest { 
    // Majors: [(bmi < 30.0), (bmi >= 25.0)]
    // Restricted Test Indices: [1, 2, 3]
    // Correlated Test Indices: [3, 0]

    @ParameterizedTest
    @CsvSource({
                // The program cannot find the input that satisfy this test requirement. This could mean that this requirement is infeasible.Test ID 0: [false, false, false]
                " 31.10700424051805, false",  //Test ID 1: [false, true, false]
                " 21.919315497043225, false",  //Test ID 2: [true, false, false]
                " 27.274285781462034, true",  //Test ID 3: [true, true, true]
              })
    public void MCDCTest(double bmi, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = BMI.overweight(bmi, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}