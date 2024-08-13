package models.screens;

import com.epam.reportportal.annotations.Step;
import core.ConciseApi;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import models.screens.common.NavigationBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static utils.WaitUtilities.waitForElement;

public class SignUpScreen extends ConciseApi {

//    private final By inputEmailLoc = accessibilityId("input-email");
//    private final By inputPasswordLoc = accessibilityId("input-password");
//    private final By confirmPasswordLoc = accessibilityId("input-repeat-password");
//    private final By signUpBtnLoc = accessibilityId("button-SIGN UP");

    @AndroidFindBy(accessibility = "input-email")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`name == \"input-email\"`]")
    private WebElement inputEmailLoc;

    @AndroidFindBy(accessibility = "input-password")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSecureTextField[`name == \"input-password\"`]")
    private WebElement inputPasswordLoc;

    @AndroidFindBy(accessibility = "input-repeat-password")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSecureTextField[`name == \"input-repeat-password\"`]")
    private WebElement confirmPasswordLoc;

    @AndroidFindBy(accessibility = "button-SIGN UP")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name == \"button-SIGN UP\"`]")
    private WebElement signUpBtnLoc;

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
