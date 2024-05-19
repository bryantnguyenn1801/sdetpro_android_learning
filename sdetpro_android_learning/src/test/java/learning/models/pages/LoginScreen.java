package learning.models.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginScreen extends BasePage {

    private final static By usernameSel = AppiumBy.accessibilityId("input-email");
    private final static By passwordSel = AppiumBy.accessibilityId("input-password");
    private final static By loginBtnSel = AppiumBy.accessibilityId("button-LOGIN");
    private final static By invalidEmailStr = AppiumBy.xpath("//*[contains(@text, 'valid email')]");
    private final static By invalidPasswordStr = AppiumBy.xpath("//*[contains(@text, 'at least 8')]");

    public LoginScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public WebElement email() {
        return component.findElement(usernameSel);
    }

    public WebElement password() {
        return component.findElement(passwordSel);
    }

    public WebElement loginBtn() {
        return component.findElement(loginBtnSel);
    }


    public String getInvalidEmail() {
        return component.findElement(invalidEmailStr).getText();
    }

    public String getInvalidPassword() {
        return component.findElement(invalidPasswordStr).getText();
    }

    public PopUpForm openPopUp() {
       return new PopUpForm(appiumDriver);
    }


}