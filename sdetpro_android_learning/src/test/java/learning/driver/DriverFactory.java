package learning.driver;

import learning.capabilities.AndroidCapability;
import learning.capabilities.IOSCapability;
import learning.capabilities.Platform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    public static AppiumDriver getDriver(Platform platform) {
        AppiumDriver appiumDriver = null;

        URL appiumServer = null;
        try {
            appiumServer = new URL("http://localhost:4723");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (appiumServer == null) {
            throw new RuntimeException("Can't construct the appium server URL");
        }

        switch (platform) {
            case ANDROID:
                appiumDriver = new AndroidDriver(appiumServer, AndroidCapability.setCapabilities());
                break;
            case IOS:
                appiumDriver = new IOSDriver(appiumServer, IOSCapability.setCapabilities());
                break;
        }

        // Need one more thing here that we will talk in next lesson
        // Global wait time applied for the WHOLE driver session - Implicit wait
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
        return appiumDriver;
    }

}