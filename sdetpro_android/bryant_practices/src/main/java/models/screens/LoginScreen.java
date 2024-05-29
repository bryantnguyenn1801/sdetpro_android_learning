package models.screens;

import com.epam.reportportal.annotations.Step;
import io.appium.java_client.AppiumBy;
import models.screens.common.BaseScreen;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.WaitUtilities;

public class LoginScreen extends BaseScreen {
    private final static By loginPageTitleSel = AppiumBy.xpath("//android.widget.TextView[@text=\"Login / Sign up Form\"]");

    @Step("Verify the [Login] section displayed")
    public LoginScreen then_verifyLoginSectionDisplayed() throws Exception {
        Assert.assertTrue(WaitUtilities.isElementDisplayed(loginPageTitleSel));
        return this;
    }


}