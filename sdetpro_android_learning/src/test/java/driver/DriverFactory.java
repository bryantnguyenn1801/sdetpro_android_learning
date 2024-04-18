package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {

    public static AppiumDriver getDriver(Platform platform) {
        AppiumDriver appiumDriver = null;
        // DesiredCaps
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME_OPTION, "uiautomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID_OPTION, "emulator-5554");
        desiredCapabilities.setCapability(MobileCapabilityType.APP_PACKAGE_OPTION, "com.wdiodemoapp");
        desiredCapabilities.setCapability(MobileCapabilityType.APP_ACTIVITY_OPTION,
                "com.wdiodemoapp.MainActivity");
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
                appiumDriver = new AndroidDriver(appiumServer, desiredCapabilities);
                break;
            case IOS:
                appiumDriver = new IOSDriver(appiumServer, desiredCapabilities);
                break;
        }

        // Need one more thing here that we will talk in next lesson
        // Global wait time applied for the WHOLE driver session - Implicit wait
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
        return appiumDriver;
    }

}