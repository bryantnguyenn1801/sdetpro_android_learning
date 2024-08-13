package learning.tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNGAExecutionSequence {
    @Test(dependsOnMethods = "testA")
    public void testB() {
        System.out.println("Test B");
    }

    @Test()
    public void testA() {
        System.out.println("Test A");
        Assert.fail("I want it that way!");
    }
}
