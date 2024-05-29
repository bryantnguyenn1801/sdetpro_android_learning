package learning.tests.explore.parameters;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.GregorianCalendar;

public class ParameterTest01 {
    @Test
    @Parameters({"systemPort", "udid"})
    public void getParams(String systemPort, String udid) {
        System.out.println(new GregorianCalendar().getTime());
        System.out.printf("systemPort: %s | udid: %s\n", systemPort, udid);
        try {
            Thread.sleep(1000);
        } catch (Exception ignored) {
        }
    }
}
