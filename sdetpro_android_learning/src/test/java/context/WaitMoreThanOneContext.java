package context;

import driver.Platform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitMoreThanOneContext implements ExpectedCondition<Boolean> {

    private final AppiumDriver appiumDriver;

    public WaitMoreThanOneContext(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }


    @Override
    public Boolean apply(WebDriver input) {
        // Get platform info under test session
        Capabilities caps = appiumDriver.getCapabilities();
        String currentPlatform = CapabilityHelpers.getCapability(caps, "platformName", String.class);
        if(Platform.valueOf(currentPlatform).equals(Platform.ANDROID)){
            return ((AndroidDriver) appiumDriver).getContextHandles().size() > 1;
        } else {
            return ((IOSDriver) appiumDriver).getContextHandles().size() > 1;
        }
    }
}