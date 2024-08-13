package learning.tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestNGAssertion {
    @Test
    public void hardAssertion() {
        String actualString = "Something else";
        String expectedString = "Something";
        Assert.assertEquals(actualString, expectedString, "[ERR] The msg is wrong!!");

        // Do something else -> never reach to this line because the above assertion was HARD failed assertion!!!
        Assert.fail("I want it that way");
    }

    @Test
    public void softAssertion() {
        SoftAssert softAssert = new SoftAssert();
        String actualString = "Something else";
        String expectedString = "Something";

        // Ghi xuong so tay thu vat... SG, ngay..thang..nam
        softAssert.assertEquals(actualString, expectedString, "[ERR] The msg is wrong!!");
        softAssert.fail("I want it that way");

        softAssert.assertAll();
    }
}
