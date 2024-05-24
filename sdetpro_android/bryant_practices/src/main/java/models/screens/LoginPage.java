package models.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final static By usernameSel = AppiumBy.accessibilityId("input-email");
    private final static By passwordSel = AppiumBy.accessibilityId("input-password");
    private final static By loginBtnSel = AppiumBy.accessibilityId("button-LOGIN");
    private final static By invalidEmailStr = AppiumBy.xpath("//*[contains(@text, 'valid email')]");
    private final static By invalidPasswordStr = AppiumBy.xpath("//*[contains(@text, 'at least 8')]");

    public LoginPage(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public WebElement email() {
        return getComponent().findElement(usernameSel);
    }

    public WebElement password() {
        return getComponent().findElement(passwordSel);
    }

    public WebElement loginBtn() {
        return getComponent().findElement(loginBtnSel);
    }


    public String getInvalidEmail() {
        return getComponent().findElement(invalidEmailStr).getText();
    }

    public String getInvalidPassword() {
        return getComponent().findElement(invalidPasswordStr).getText();
    }

    public PopUpForm openPopUpDialog() {
       return findComponent(PopUpForm.class);
    }


}