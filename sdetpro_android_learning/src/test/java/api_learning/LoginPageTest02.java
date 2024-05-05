package api_learning;

import driver.DriverFactory;
import capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import models.pages.LoginPage02;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPageTest02 {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            // Navigate to the login screen
            By navloginBtnLoc = AppiumBy.accessibilityId("Login");
            WebElement navLoginBtnEle = appiumDriver.findElement(navloginBtnLoc);
            navLoginBtnEle.click();

            LoginPage02 loginPage = new LoginPage02(appiumDriver);
            loginPage.inputUsername("teo@sth.com");
            loginPage.inputPassword("12345678");
            loginPage.clickOnLoginBtn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }

}