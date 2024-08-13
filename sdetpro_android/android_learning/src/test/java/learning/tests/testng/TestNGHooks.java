package learning.tests.testng;

import org.testng.annotations.Test;

public class TestNGHooks extends BaseTestNGHook {
    @Test
    public void method1() {
        System.out.println("\t\t\t\t >> Method 01");
    }

    @Test
    public void method2() {
        System.out.println("\t\t\t\t >> Method 02");
        Verifier.verifyEquals("Ti", "Teo");
    }
}
