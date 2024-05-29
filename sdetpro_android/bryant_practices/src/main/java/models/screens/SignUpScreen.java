package models.screens;

import com.epam.reportportal.annotations.Step;
import core.ConciseApi;
import models.screens.common.NavigationBar;
import org.openqa.selenium.By;
import org.testng.Assert;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static utils.WaitUtilities.waitForElement;

public class SignUpScreen extends ConciseApi {

    private final By signUpFormLoc = accessibilityId("button-sign-up-container");
    private final By inputEmailLoc = accessibilityId("input-email");
    private final By inputPasswordLoc = accessibilityId("input-password");
    private final By confirmPasswordLoc = accessibilityId("input-repeat-password");
    private final By signUpBtnLoc = accessibilityId("button-SIGN UP");
    private final By dialogMsgLoc = id("android:id/message");
    private final By dialogBtnLoc = className("android.widget.Button");

    public NavigationBar getNavigation() {
        return new NavigationBar();
    }

    @Step("Click sign up form")
    public SignUpScreen clickSignUpForm() {
        clickOnElement(signUpFormLoc);
        return this;
    }

    @Step("Input Email")
    public SignUpScreen inputEmail(String email) {
        clickOnElement(inputEmailLoc)
                .sendKeys(email);
        return this;
    }

    @Step("Input Password")
    public SignUpScreen inputPassword(String password) {
        clickOnElement(inputPasswordLoc)
                .sendKeys(password);
        return this;
    }

    @Step("Confirm Password")
    public SignUpScreen confirmPassword(String password) {
        clickOnElement(confirmPasswordLoc)
                .sendKeys(password);
        return this;
    }

    @Step("Click sign up button")
    public SignUpScreen clickSignUpButton() {
        clickOnElement(signUpBtnLoc);
        return this;
    }

    @Step("Verify account is signed up")
    public SignUpScreen verifySignUp(){
        String actualMsg = waitForElement(dialogMsgLoc).getText().trim();
        String expectedMsg = "You successfully signed up!";
        Assert.assertEquals(actualMsg, expectedMsg);
        return this;
    }
    @Step("Click ok button")
    public SignUpScreen clickOKButton() {
        clickOnElement(dialogBtnLoc);
        return this;
    }
}
