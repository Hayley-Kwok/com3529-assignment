package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.generateTestSuite.TestSuite;
import uk.ac.shef.com3529.assignment.model.BinaryRelatedNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EntryPoint {
    public static void main(String[] args) {
        restrictedNotCoveredAllMajorsExample();
        bmiExample();
        daysExample();
        triangleExamples();
    }

    /**
     * bmi example from lab
     */
    private static void bmiExample() {
        TestRequirements bmiUnderweightRequirements = generateTestRequirements(ParsedExamples.getBmiUnderweight());
        TestSuite bmiUnderweight = new TestSuite("BMIUnderWeight",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.underWeight(%s, coveredBranches);",
                bmiUnderweightRequirements, false);
        bmiUnderweight.writeToFile();

        TestSuite bmiUnderweightWithBranch = new TestSuite("BMIUnderWeightWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.underWeight(%s, coveredBranches);",
                bmiUnderweightRequirements, true);
        bmiUnderweightWithBranch.writeToFile();

        TestRequirements bmiNormalRequirements = generateTestRequirements(ParsedExamples.getBmiNormal());
        TestSuite bmiNormal = new TestSuite("BMINormal",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.normal(%s, coveredBranches);",
                bmiNormalRequirements, false);
        bmiNormal.writeToFile();

        TestSuite bmiNormalWithBranch = new TestSuite("BMINormalWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.normal(%s, coveredBranches);",
                bmiNormalRequirements, true);
        bmiNormalWithBranch.writeToFile();

        TestRequirements bmiOverweightRequirements = generateTestRequirements(ParsedExamples.getBmiOverweight());
        TestSuite bmiOverweight = new TestSuite("BMIOverweight",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.overweight(%s, coveredBranches);",
                bmiOverweightRequirements, false);
        bmiOverweight.writeToFile();

        TestSuite bmiOverweightWithBranch = new TestSuite("BMIOverweightWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.overweight(%s, coveredBranches);",
                bmiOverweightRequirements, true);
        bmiOverweightWithBranch.writeToFile();

        TestRequirements bmiObeseRequirements = generateTestRequirements(ParsedExamples.getBmiObese());
        TestSuite bmiObese = new TestSuite("BMIObeseweight",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.obese(%s, coveredBranches);",
                bmiObeseRequirements, false);
        bmiObese.writeToFile();

        TestSuite bmiObeseWithBranch = new TestSuite("BMIObeseWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.BMI;",
                "BMI.obese(%s, coveredBranches);",
                bmiObeseRequirements, true);
        bmiObeseWithBranch.writeToFile();
    }

    /**
     * A node that the restricted mcdc cannot cover all majors
     */
    private static void restrictedNotCoveredAllMajorsExample() {
        TestRequirements restrictedNotCoveredAllMajorsRequirements = generateTestRequirements(ParsedExamples.getRestrictedNotCoveringAllMajors());
        TestSuite restrictedNotCoveredAllMajorsTestSuite = new TestSuite("RestrictedNotCoveringAllMajors",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.RandomExample;",
                "RandomExample.RestrictedNotCoveringAllMajors(%s, coveredBranches);",
                restrictedNotCoveredAllMajorsRequirements, false);
        restrictedNotCoveredAllMajorsTestSuite.writeToFile();

        TestSuite restrictedNotCoveredAllMajorsTestSuiteWithBranch = new TestSuite("RestrictedNotCoveringAllMajorsWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.RandomExample;",
                "RandomExample.RestrictedNotCoveringAllMajors(%s, coveredBranches);",
                restrictedNotCoveredAllMajorsRequirements, true);
        restrictedNotCoveredAllMajorsTestSuiteWithBranch.writeToFile();
    }

    /**
     * calendar example from lab
     */
    private static void daysExample() {
        TestRequirements daysRequirement = generateTestRequirements(ParsedExamples.getDaysBetweenTwoDates());
        TestSuite dayBetweenTwoDatesTestSuite = new TestSuite("DayBetweenTwoDates",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Calendar;",
                "Calendar.daysBetweenTwoDates(%s, coveredBranches);",
                daysRequirement, false);
        dayBetweenTwoDatesTestSuite.writeToFile();

        TestSuite dayBetweenTwoDatesTestSuitWithBranch = new TestSuite("DayBetweenTwoDatesWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Calendar;",
                "Calendar.daysBetweenTwoDates(%s, coveredBranches);",
                daysRequirement, true);
        dayBetweenTwoDatesTestSuitWithBranch.writeToFile();
    }

    /**
     * Triangle example from lab
     */
    private static void triangleExamples() {
        TestRequirements isoscelesRequirements = generateTestRequirements(ParsedExamples.getIsoscelesPractical());
        TestSuite isoscelesTestSuite = new TestSuite("IsoscelesTriangle",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.isoscelesClassify(%s, coveredBranches);",
                isoscelesRequirements, false);
        isoscelesTestSuite.writeToFile();

        TestSuite isoscelesTestSuiteWithCoveredBranch = new TestSuite("IsoscelesTriangleWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.isoscelesClassify(%s, coveredBranches);",
                isoscelesRequirements, true);
        isoscelesTestSuiteWithCoveredBranch.writeToFile();

        TestRequirements scaleneRequirements = generateTestRequirements(ParsedExamples.getScaleneBranch());
        TestSuite scaleneTestSuite = new TestSuite("ScaleneTriangle",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.scaleneClassify(%s, coveredBranches);",
                scaleneRequirements, false);
        scaleneTestSuite.writeToFile();

        TestSuite scaleneTestSuiteWithCoveredBranch = new TestSuite("ScaleneTriangleWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.scaleneClassify(%s, coveredBranches);",
                scaleneRequirements, true);
        scaleneTestSuiteWithCoveredBranch.writeToFile();

        TestRequirements equilateralRequirements = generateTestRequirements(ParsedExamples.getEquilateralBranch());
        TestSuite equilateralTestSuite = new TestSuite("EquilateralTriangle",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.equilateralClassify(%s, coveredBranches);\n",
                equilateralRequirements, false);
        equilateralTestSuite.writeToFile();

        TestSuite equilateralTestSuiteWithBranch = new TestSuite("EquilateralTriangleWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.equilateralClassify(%s, coveredBranches);\n",
                equilateralRequirements, true);
        equilateralTestSuiteWithBranch.writeToFile();

        TestRequirements invalidRequirements = generateTestRequirements(ParsedExamples.getInvalidBranch());
        TestSuite invalidTestSuite = new TestSuite("InvalidTriangle",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.invalidClassify(%s, coveredBranches);\n",
                invalidRequirements, false);
        invalidTestSuite.writeToFile();

        TestSuite invalidTestSuiteWithBranch = new TestSuite("InvalidTriangleWithCoveredBranch",
                "import uk.ac.shef.com3529.assignment.generateTestSuite.instrumentedExamples.Triangle;",
                "Triangle.invalidClassify(%s, coveredBranches);\n",
                invalidRequirements, true);
        invalidTestSuiteWithBranch.writeToFile();
    }

    /**
     * Create testRequirement of the given node and print out useful information about the test requirement
     */
    private static TestRequirements generateTestRequirements(BinaryRelatedNode<?> root) {
        TestRequirements requirements = new TestRequirements(root);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Branch Predicate under consideration: " + requirements.getRoot());
        System.out.println("All Conditions: " + requirements.getAllConditions());
        System.out.println("Inputs: " + Arrays.toString(requirements.getVariables()));
        System.out.println("Majors: " + Arrays.toString(requirements.getMajors()));

        System.out.println("Full Condition Table: \n\t" + prettyPrintConditionTable(requirements.getFullConditionTable()));
        System.out.println("Restricted MCDC Test Indices: " + requirements.getRestrictedTestIndices());
        System.out.println("Restricted MCDC Table: \n\t" + prettyPrintConditionTable(requirements.getRestrictedMCDCConditionTable()));
        System.out.println("Majors that Restricted MCDC didn't manage to cover:" + requirements.getNotCoveredMajor());
        System.out.println("Correlated MCDC Test Indices: " + requirements.getCorrelatedTestIndices());
        System.out.println("Correlated MCDC Table: \n\t" + prettyPrintConditionTable(requirements.getCorrelatedMCDCConditionTable()));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        return requirements;
    }

    private static String prettyPrintConditionTable(ArrayList<ArrayList<Boolean>> table) {
        return table.stream().map(Object::toString).collect(Collectors.joining("\n\t"));
    }
}
