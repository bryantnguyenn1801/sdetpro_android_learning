package learning;

import learning.driver.DriverFactory;
import learning.capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import learning.utils.ElementHandler;

public class HandleVariantLocators {

    private static Map<Platform, By> navloginBtnLocMap = Map.of(
            Platform.ANDROID, AppiumBy.accessibilityId("Login"),
            Platform.IOS, AppiumBy.accessibilityId("try-to-have-difference-here")
    );
    private static By emailFieldLoc = AppiumBy.accessibilityId("input-email");
    private static By passwordLoc = AppiumBy.accessibilityId("input-password");
    private static By loginBtnLoc = AppiumBy.accessibilityId("button-LOGIN");

    public static void main(String[] args) {

        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
//            // Get platform info under test session
//            Capabilities caps = appiumDriver.getCapabilities();
//            String currentPlatform = CapabilityHelpers.getCapability(caps, "platformName", String.class);
//            System.out.printf("Platform name is: %s\n", currentPlatform);
//
//            // Construct the locator map
//            Map<Platform, By> loginBtnLocMap = Map.of(
//                    Platform.ANDROID, AppiumBy.id("login-btn-android"),
//                    Platform.IOS, AppiumBy.accessibilityId("ios-login-btn")
//            );
//
//            // Get locator base on current platform
//            System.out.println(loginBtnLocMap.get(Platform.valueOf(currentPlatform)));
//
//            // DEBUG PURPOSE ONLY
//            Thread.sleep(1000);

            ElementHandler elementHandler = new ElementHandler(appiumDriver);
            WebElement navLoginBtnEle = elementHandler.findElement(navloginBtnLocMap);
            navLoginBtnEle.click();

            WebElement emailFieldEle = appiumDriver.findElement(emailFieldLoc);
            emailFieldEle.clear();
            emailFieldEle.sendKeys("teo@sth.com");

            // Input password
            WebElement passwordEle = appiumDriver.findElement(passwordLoc);
            passwordEle.sendKeys("12345678");

            // Click on Login Btn
            WebElement loginBtnEle = appiumDriver.findElement(loginBtnLoc);
            loginBtnEle.click();

            // Debug purpose only
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}