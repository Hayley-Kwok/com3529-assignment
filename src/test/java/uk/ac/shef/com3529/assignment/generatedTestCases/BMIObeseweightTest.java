package uk.ac.shef.com3529.assignment.generatedTestCases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BMIObeseweightTest { 
    // Majors: [(bmi >= 30.0)]
    // Restricted Test Indices: [0, 1]
    // Correlated Test Indices: [0, 1]

    @ParameterizedTest
    @CsvSource({
                " 18.397845908422262, false",  //Test ID 0: [false, false]
                " 39.11345701563589, true",  //Test ID 1: [true, true]
              })
    public void MCDCTest(double bmi, boolean expectedBranchResult) {
        HashSet<Integer> coveredBranches = new HashSet<>();
        boolean actualBranchResult = BMI.obese(bmi, coveredBranches);
        System.out.println(coveredBranches);
        assertEquals(expectedBranchResult, actualBranchResult);
    }
}