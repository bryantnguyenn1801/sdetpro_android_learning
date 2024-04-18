package api_learning;

import driver.DriverFactory;
import driver.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.internal.CapabilityHelpers;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandleMultiApps {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        try {

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

            // SWITCH to another app | Handle multi app on same device
            Capabilities caps = appiumDriver.getCapabilities();
            String currentPlatform = CapabilityHelpers.getCapability(caps, "platformName", String.class);
            if (Platform.valueOf(currentPlatform).equals(Platform.ANDROID)) {
                AndroidDriver androidDriver = ((AndroidDriver) appiumDriver);

                // put the current app under background till we call it back
                androidDriver.runAppInBackground(Duration.ofSeconds(-5));

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