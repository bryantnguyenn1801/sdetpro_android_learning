package api_learning;

import driver.DriverFactory;
import capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.internal.CapabilityHelpers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHandler;

public class HandleMultiApps {
    static AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

    public static void inputUserDetails() {
        // Login Action
        By navloginBtnLoc = AppiumBy.accessibilityId("Login");
        WebElement navLoginBtnEle = appiumDriver.findElement(navloginBtnLoc);
        navLoginBtnEle.click();

        // Input username
        By emailFieldLoc = AppiumBy.accessibilityId("input-email");
        WebElement emailFieldEle = appiumDriver.findElement(emailFieldLoc);
        emailFieldEle.sendKeys("teo@sth.com");
        // Input password
        By passwordLoc = AppiumBy.accessibilityId("input-password");
        WebElement passwordEle = appiumDriver.findElement(passwordLoc);
        passwordEle.sendKeys("12345678");

        // Click on Login Btn
        By loginBtnLoc = AppiumBy.accessibilityId("button-LOGIN");
        WebElement loginBtnEle = appiumDriver.findElement(loginBtnLoc);
        loginBtnEle.click();
    }

    public static void main(String[] args) {
        try {
            // SWITCH to another app | Handle multi app on same device
            Capabilities caps = appiumDriver.getCapabilities();
            String currentPlatform = CapabilityHelpers.getCapability(caps, "platformName", String.class);
            if (Platform.valueOf(currentPlatform).equals(Platform.ANDROID)) {
                ElementHandler.switchApp(appiumDriver);
                inputUserDetails();
            }

            // Wait for the dialog displayed
            By dialogMsgLoc = AppiumBy.id("android:id/message");
            By dialogBtnLoc = AppiumBy.id("android:id/button1");

            // Using explicit wait
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15));
            WebElement dialogMsgEle = wait.until(ExpectedConditions.visibilityOfElementLocated(dialogMsgLoc));
            System.out.printf("Dialog msg: %s\n", dialogMsgEle.getText());
            appiumDriver.findElement(dialogBtnLoc).click();

            // DEBUG PURPOSE ONLY
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }

}