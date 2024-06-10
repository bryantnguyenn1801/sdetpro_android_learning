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

    private final By inputEmailLoc = accessibilityId("input-email");
    private final By inputPasswordLoc = accessibilityId("input-password");
    private final By confirmPasswordLoc = accessibilityId("input-repeat-password");
    private final By signUpBtnLoc = accessibilityId("button-SIGN UP");

    public NavigationBar getNavigation() {
        return new NavigationBar();
    }


    @Step("Input Email")
    public SignUpScreen inputEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        clickOnElement(inputEmailLoc)
                .sendKeys(email);
        return this;
    }

    @Step("Input Password")
    public SignUpScreen inputPassword(String password) {
        clickOnElement(inputPasswordLoc)
                .sendKeys(new String[]{password});
        return this;
    }

    @Step("Confirm Password")
    public SignUpScreen confirmPassword(String password) {
        clickOnElement(confirmPasswordLoc)
                .sendKeys(new String[]{password});
        return this;
    }

    @Step("Click sign up button")
    public PopUpScreen clickSignUpButton() {
        clickOnElement(signUpBtnLoc);
        return new PopUpScreen();
    }

}
