package learning.utils;

import learning.capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.internal.CapabilityHelpers;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

public class ElementHandler {

    private final AppiumDriver appiumDriver;

    public ElementHandler(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public WebElement findElement(Map<Platform, By> locatorMap) {
        By elementLocator = locatorMap.get(Platform.valueOf(getCurrentPlatform()));
        return this.appiumDriver.findElement(elementLocator);
    }

    public List<WebElement> findElements(Map<Platform, By> locatorMap) {
        By elementLocator = locatorMap.get(Platform.valueOf(getCurrentPlatform()));
        return this.appiumDriver.findElements(elementLocator);
    }

    private String getCurrentPlatform() {
        Capabilities caps = this.appiumDriver.getCapabilities();
        return CapabilityHelpers.getCapability(caps, "platformName", String.class);
    }

    public static void switchApp(AppiumDriver appiumDriver) {
        AndroidDriver androidDriver = ((AndroidDriver) appiumDriver);

        // put the current app under background till we call it back
        androidDriver.runAppInBackground(Duration.ofSeconds(-1));

        // Switch to the another app to do something
        androidDriver.activateApp("com.android.settings");
        WebElement netWorkSettingBtn = appiumDriver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"Network & internet\"]"));
        netWorkSettingBtn.click();

        WebElement internetBtn = appiumDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Internet\")"));
        internetBtn.click();

        WebElement switchWifi = appiumDriver.findElement(By.id("android:id/switch_widget"));
        switchWifi.click();
        // Switch back to the app under test to continue the follow
        androidDriver.activateApp("com.wdiodemoapp");
        androidDriver.terminateApp("com.android.settings");
    }
}