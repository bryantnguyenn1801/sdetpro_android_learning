package learning;

import learning.driver.DriverFactory;
import learning.capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginFormInteraction {

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
            passwordEle.sendKeys("987654321");

            // Click on Login Btn
            By loginBtnLoc = AppiumBy.accessibilityId("button-LOGIN");
            WebElement loginBtnEle = appiumDriver.findElement(loginBtnLoc);
            loginBtnEle.click();

            // Wait for the dialog displayed
            By dialogMsgLoc = AppiumBy.id("android:id/message");
            By dialogBtnLoc = AppiumBy.id("android:id/button1");

            // Using explicit wait
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15));
            WebElement dialogMsgEle = wait.until(ExpectedConditions.visibilityOfElementLocated(dialogMsgLoc));
            System.out.printf("Dialog msg: %s\n", dialogMsgEle.getText());
            appiumDriver.findElement(dialogBtnLoc).click();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // DEBUG PURPOSE ONLY
        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {
        }

        appiumDriver.quit();
    }

}