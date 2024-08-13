package learning.tests.testng;

public class Verifier {
    public static void verifyEquals(String actualResults, String expectedResults) {
        if (!actualResults.equalsIgnoreCase(expectedResults)) {
            throw new AssertionError("Expected value and actual value are different");
        }
    }
}
