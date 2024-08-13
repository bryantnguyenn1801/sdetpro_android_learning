package learning;

import learning.driver.DriverFactory;
import learning.capabilities.Platform;
import io.appium.java_client.AppiumDriver;

public class LaunchAppTest {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        appiumDriver.quit();
    }
}