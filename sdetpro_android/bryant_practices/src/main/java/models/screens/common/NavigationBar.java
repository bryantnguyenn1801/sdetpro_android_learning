package models.screens.common;

import com.epam.reportportal.annotations.Step;
import core.ConciseApi;
import io.appium.java_client.AppiumBy;
import models.screens.FormComponentsScreen;
import models.screens.LoginScreen;
import models.screens.SignUpScreen;
import org.openqa.selenium.By;

import static utils.WaitUtilities.waitForElement;

public class NavigationBar extends ConciseApi {
    private final static By loginIconSel = AppiumBy.accessibilityId("Login");
    private final static By formsIconSel = AppiumBy.accessibilityId("Forms");

    public NavigationBar isNavigationBarVisible() {
        waitForElement(loginIconSel);
        return this;
    }

    @Step("User clicks on [Login] icon")
    public LoginScreen goLoginScreen() {
        clickOnElement(loginIconSel);
        return new LoginScreen();
    }

    @Step("User clicks on [Forms] icon")
    public FormComponentsScreen goFormsScreen() {
        clickOnElement(formsIconSel);
        return new FormComponentsScreen();
    }

}
