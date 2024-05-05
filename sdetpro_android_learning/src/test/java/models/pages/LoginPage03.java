package models.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

// INTRODUCING MAIN INTERACTION METHODS
public class LoginPage03 extends BasePage {

    // Scope 01: Keep the selector
    private final static By usernameSel = AppiumBy.accessibilityId("input-email");
    private final static By passwordSel = AppiumBy.accessibilityId("input-password");
    private final static By loginBtnSel = AppiumBy.accessibilityId("button-LOGIN");

    // Scope 02: Constructor to POM_AdvancedConcept.md the appiumDriver
    public LoginPage03(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    // Scope 03: INTRODUCING MAIN INTERACTION METHODS
    public LoginPage03 inputUsername(String username) {
        appiumDriver.findElement(usernameSel).sendKeys(username);
        return this;
    }

    public LoginPage03 inputPassword(String password) {
        appiumDriver.findElement(passwordSel).sendKeys(password);
        return this;
    }

    public void clickOnLoginBtn() {
        appiumDriver.findElement(loginBtnSel).click();
    }

}
