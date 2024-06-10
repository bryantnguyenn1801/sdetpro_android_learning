package models.screens;


import com.epam.reportportal.annotations.Step;
import core.ConciseApi;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.Assert;

import static config.strings.StringConstants.*;
import static utils.WaitUtilities.waitForElement;

public class PopUpScreen extends ConciseApi {
    private final static By dialogMsgLoc = AppiumBy.id("android:id/message");
    private final static By dialogOKBtnLoc = AppiumBy.id("android:id/button1");
    private final static By dialogAMLBtnLoc = AppiumBy.xpath("//*[@text='ASK ME LATER']");
    private final static By dialogCancelBtnLoc = AppiumBy.xpath("//*[@text='CANCEL']");

    @Step("Verify account is signed up")
    public PopUpScreen verifySignUp() {
        String actualMsg = waitForElement(dialogMsgLoc).getText().trim();
        Assert.assertEquals(actualMsg, POP_UP_SIGNED_UP_MSG);
        return this;
    }

    @Step("Verify account is logged in")
    public PopUpScreen verifyLogin() {
        String actualMsg = waitForElement(dialogMsgLoc).getText().trim();
        Assert.assertEquals(actualMsg, POP_UP_LOGGED_IN_MSG);
        return this;
    }

    @Step("Verify button Active is clicked")
    public PopUpScreen verifyBtnActive() {
        String actualMsg = waitForElement(dialogMsgLoc).getText().trim();
        Assert.assertEquals(actualMsg, POP_UP_ACTIVE_BTN_MSG);
        return this;
    }

    @Step("Verify buttons are displayed")
    public PopUpScreen verifyBtnDisplayed() {
        Assert.assertTrue(isElementEnabled(dialogAMLBtnLoc));
        Assert.assertTrue(isElementEnabled(dialogCancelBtnLoc));
        Assert.assertTrue(isElementEnabled(dialogOKBtnLoc));
        return this;
    }

    @Step("Click ok button")
    public void clickOKBtn() {
        clickOnElement(dialogOKBtnLoc);
    }

}