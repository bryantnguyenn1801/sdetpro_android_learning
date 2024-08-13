package models.screens;

import com.epam.reportportal.annotations.Step;
import io.appium.java_client.AppiumBy;
import models.screens.common.BaseScreen;
import org.openqa.selenium.By;
import org.testng.Assert;

import static config.strings.StringConstants.*;
import static utils.WaitUtilities.waitForElement;

public class LoginScreen extends BaseScreen {
    private final static By loginPageTitleSel = AppiumBy.xpath("//android.widget.TextView[@text=\"Login / Sign up Form\"]");
    private final static By signUpFormLoc = AppiumBy.accessibilityId("button-sign-up-container");
    private final static By emailSel = AppiumBy.accessibilityId("input-email");
    private final static By passwordSel = AppiumBy.accessibilityId("input-password");
    private final static By loginBtnSel = AppiumBy.accessibilityId("button-LOGIN");
    private final static By invalidEmailStr = AppiumBy.xpath("//*[contains(@text, 'valid email')]");
    private final static By invalidPasswordStr = AppiumBy.xpath("//*[contains(@text, 'at least 8')]");

    @Step("Verify the [Login] section displayed")
    public LoginScreen then_verifyLoginSectionDisplayed() throws Exception {
        String actualMsg = waitForElement(loginPageTitleSel).getText().trim();
        Assert.assertEquals(actualMsg, LOGIN_AND_SIGNUP_TITLE);
        return this;
    }

    @Step("Click sign up form")
    public SignUpScreen clickSignUpForm() {
        clickOnElement(signUpFormLoc);
        return new SignUpScreen();
    }

    @Step("Input Valid Email")
    public LoginScreen inputValidEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        clickOnElement(emailSel)
                .sendKeys(email);
        return this;
    }

    @Step("Input Valid Password")
    public LoginScreen inputValidPassword(String password) {
        clickOnElement(passwordSel)
                .sendKeys(new String[]{password});
        return this;
    }

    @Step("Input Invalid Email")
    public LoginScreen inputInvalidEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        clickOnElement(emailSel)
                .sendKeys(email);
        return this;
    }

    @Step("Input Invalid Password")
    public LoginScreen inputInvalidPassword(String password) {
        clickOnElement(passwordSel)
                .sendKeys(new String[]{password});
        return this;
    }

    @Step("Click login button")
    public LoginScreen clickLoginButton() {
        clickOnElement(loginBtnSel);
        return this;
    }

    @Step("Click login button")
    public PopUpScreen clickLoginButtonSuccess() {
        clickOnElement(loginBtnSel);
        return new PopUpScreen();
    }

    @Step("Verify Incorrect Email")
    public LoginScreen verifyIncorrectEmail() {
        String actualMsg = waitForElement(invalidEmailStr).getText().trim();
        Assert.assertEquals(actualMsg, INVALID_EMAIL_WARNING_MSG);
        return this;
    }

    @Step("Verify Incorrect Password")
    public LoginScreen verifyIncorrectPassword() {
        String actualMsg = waitForElement(invalidPasswordStr).getText().trim();
        Assert.assertEquals(actualMsg, INVALID_PASSWORD_WARNING_MSG);
        return this;
    }
}