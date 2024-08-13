package learning.tests.testng;

import io.appium.java_client.AppiumDriver;
import learning.capabilities.Platform;
import learning.driver.DriverFactory;
import org.testng.annotations.Test;

@Test
public class TestNGBasic {
    public void openApp(){
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        appiumDriver.quit();
    }
}
