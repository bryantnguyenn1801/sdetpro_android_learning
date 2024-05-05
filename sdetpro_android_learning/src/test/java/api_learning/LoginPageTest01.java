package api_learning;

import driver.DriverFactory;
import capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import models.pages.LoginPage01;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPageTest01 {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            // Navigate to the login screen
            By navloginBtnLoc = AppiumBy.accessibilityId("Login");
            WebElement navLoginBtnEle = appiumDriver.findElement(navloginBtnLoc);
            navLoginBtnEle.click();

            LoginPage01 loginPage = new LoginPage01(appiumDriver);
            loginPage.username().sendKeys("teo@sth.com");
            loginPage.password().sendKeys("12345678");
            loginPage.loginBtn().click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }

}