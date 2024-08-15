package learning.tests.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNGDataProvider {
    @Test(dataProvider = "teo")
    public void test(String value) {
        System.out.println(value);
    }

    @DataProvider(name = "teo")
    public String[] testData() {
        return new String[]{"Value 1", " value 2"};
    }
}
