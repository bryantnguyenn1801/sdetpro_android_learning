package models.screens.common;

import com.epam.reportportal.annotations.Step;
import core.ConciseApi;
import io.appium.java_client.AppiumBy;
import models.screens.SignUpScreen;
import org.openqa.selenium.By;

import static utils.WaitUtilities.waitForElement;

public class NavigationBar extends ConciseApi {
    private final static By loginIconSel = AppiumBy.accessibilityId("Login");

    public NavigationBar isNavigationBarVisible() {
        waitForElement(loginIconSel);
        return this;
    }

    @Step("User clicks on [Login] icon")
    public SignUpScreen goLoginScreen() {
        clickOnElement(loginIconSel);
        return new SignUpScreen();
    }

}
