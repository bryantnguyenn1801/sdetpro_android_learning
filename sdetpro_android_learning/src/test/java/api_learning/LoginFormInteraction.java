package api_learning;

import driver.DriverFactory;
import driver.Platform;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginFormInteraction {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            // Login Action
            By navloginBtnLoc = By.xpath("//android.widget.Button[@content-desc='Login']");
            WebElement navLoginBtnEle = appiumDriver.findElement(navloginBtnLoc);
            navLoginBtnEle.click();

            // Input username
            By emailFieldLoc = By.xpath("//android.widget.EditText[@content-desc=\"input-email\"]");
            WebElement emailFieldEle = appiumDriver.findElement(emailFieldLoc);
            emailFieldEle.sendKeys("teo@sth.com");


            // Input password
            By passwordLoc = By.xpath("//android.widget.EditText[@content-desc=\"input-password\"]");
            WebElement passwordEle = appiumDriver.findElement(passwordLoc);
            passwordEle.sendKeys("987654321");

            // Click on Login Btn
            By loginBtnLoc = By.xpath("//android.widget.Button[@content-desc='button-LOGIN']");
            WebElement loginBtnEle = appiumDriver.findElement(loginBtnLoc);
            loginBtnEle.click();

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