package utils;

import driver.Platform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.internal.CapabilityHelpers;
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
}