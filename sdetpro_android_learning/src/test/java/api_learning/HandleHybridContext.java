package api_learning;

import context.WaitMoreThanOneContext;
import driver.DriverFactory;
import driver.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.ios.IOSDriver;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandleHybridContext {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            // Click on the Webview button
            By formsBtnLoc = AppiumBy.accessibilityId("Webview");
            // Navigate to [Webview] screen
            appiumDriver.findElement(formsBtnLoc).click();

            // Get platform info under test session
            Capabilities caps = appiumDriver.getCapabilities();
            String currentPlatform = CapabilityHelpers.getCapability(caps, "platformName", String.class);

            // Custom Explicit wait
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15L));
            wait.until(new WaitMoreThanOneContext(appiumDriver));

            if(Platform.valueOf(currentPlatform).equals(Platform.ANDROID)){
                System.out.println(((AndroidDriver) appiumDriver).getContextHandles());
            } else {
                System.out.println(((IOSDriver) appiumDriver).getContextHandles());
            }


            // DEBUG PURPOSE ONLY
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }

}