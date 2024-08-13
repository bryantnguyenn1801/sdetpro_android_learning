package learning.tests;

import io.appium.java_client.AppiumDriver;
import learning.capabilities.Platform;
import learning.driver.DriverFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected static AppiumDriver appiumDriver;

    @BeforeTest
    @Parameters({"systemPort", "udid"})
    public void initAppiumDriverSession(String systemPort, String udid) {
        appiumDriver = DriverFactory.getDriver(Platform.ANDROID, systemPort, udid);
    }

    @AfterTest
    public void quitAppiumDriverSession() {
        appiumDriver.quit();
    }


}
