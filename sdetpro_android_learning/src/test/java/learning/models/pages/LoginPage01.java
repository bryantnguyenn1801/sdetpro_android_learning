package learning.models.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// INTRODUCING FOUND ELEMENTS
public class LoginPage01 {

    private final AppiumDriver appiumDriver;

    // Scope 01: Keep the selector
    private final static By usernameSel = AppiumBy.accessibilityId("input-email");
    private final static By passwordSel = AppiumBy.accessibilityId("input-password");
    private final static By loginBtnSel = AppiumBy.accessibilityId("button-LOGIN");

    // Scope 02: Constructor to POM_AdvancedConcept.md the appiumDriver
    public LoginPage01(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    // Scope 03: INTRODUCING FOUND ELEMENTS
    public WebElement username(){
        return appiumDriver.findElement(usernameSel);
    }

    public WebElement password(){
        return appiumDriver.findElement(passwordSel);
    }

    public WebElement loginBtn(){
        return appiumDriver.findElement(loginBtnSel);
    }

}