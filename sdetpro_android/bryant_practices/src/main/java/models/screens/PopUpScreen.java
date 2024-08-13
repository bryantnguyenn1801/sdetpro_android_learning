package models.screens;


import com.epam.reportportal.annotations.Step;
import core.ConciseApi;
import core.MultipleBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static config.strings.StringConstants.*;
import static utils.WaitUtilities.waitForElement;

public class PopUpScreen extends ConciseApi {
    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(iOSClassChain = "**/*[`name CONTAINS \"topupButton\"`]")
    public WebElement dialogOKBtnLoc;

    @AndroidFindBy(xpath = "//*[@text='ASK ME LATER']")
    @iOSXCUITFindBy(iOSClassChain = "**/*[`name CONTAINS \"topupButton\"`]")
    public WebElement dialogAMLBtnLoc;

    @AndroidFindBy(xpath = "//*[@text='CANCEL']")
    @iOSXCUITFindBy(iOSClassChain = "**/*[`name CONTAINS \"topupButton\"`]")
    public WebElement dialogCancelBtnLoc;

    private By dialogMsgLoc() {
        return new MultipleBy()
                .android(By.id("android:id/message"))
                .ios(By.xpath("//XCUIElementTypeStaticText[@name=\"Signed Up!\"]"))
                .getByPlatform();
    }

    private By dialogOKBtnLoc() {
        return new MultipleBy()
                .android(By.id("android:id/button1"))
                .ios(By.xpath("//XCUIElementTypeStaticText[@name=\"Signed Up!\"]"))
                .getByPlatform();
    }

    private By dialogAMLBtnLoc() {
        return new MultipleBy()
                .android(By.xpath("//*[@text='ASK ME LATER']"))
                .ios(By.xpath("//XCUIElementTypeStaticText[@name=\"Signed Up!\"]"))
                .getByPlatform();
    }

    private By dialogCancelBtnLoc() {
        return new MultipleBy()
                .android(By.id("//*[@text='CANCEL']"))
                .ios(By.xpath("//XCUIElementTypeStaticText[@name=\"Signed Up!\"]"))
                .getByPlatform();
    }

    @Step("Verify account is signed up")
    public PopUpScreen verifySignUp() {
        By locator = dialogMsgLoc();
        String actualMsg = waitForElement((WebElement) locator).getText().trim();
        Assert.assertEquals(actualMsg, POP_UP_SIGNED_UP_MSG);
        return this;
    }

    @Step("Verify account is logged in")
    public PopUpScreen verifyLogin() {
        By locator = dialogMsgLoc();
        String actualMsg = waitForElement((WebElement) locator).getText().trim();
        Assert.assertEquals(actualMsg, POP_UP_LOGGED_IN_MSG);
        return this;
    }

    @Step("Verify button Active is clicked")
    public PopUpScreen verifyBtnActive() {
        By locator = dialogMsgLoc();
        String actualMsg = waitForElement((WebElement) locator).getText().trim();
        Assert.assertEquals(actualMsg, POP_UP_ACTIVE_BTN_MSG);
        return this;
    }

    @Step("Verify buttons are displayed")
    public PopUpScreen verifyBtnDisplayed() {
        Assert.assertTrue(isElementEnabled(dialogOKBtnLoc));
        Assert.assertTrue(isElementEnabled(dialogCancelBtnLoc));
        Assert.assertTrue(isElementEnabled(dialogAMLBtnLoc));
        return this;
    }

    @Step("Click ok button")
    public void clickOKBtn() {
        clickOnElement(dialogOKBtnLoc);
    }

}