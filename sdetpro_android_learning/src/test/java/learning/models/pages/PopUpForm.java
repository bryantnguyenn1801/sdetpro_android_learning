package learning.models.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PopUpForm extends BasePage {
    private final static By dialogMsgLoc = AppiumBy.id("android:id/message");
    private final static By dialogBtnLoc = AppiumBy.id("android:id/button1");

    public PopUpForm(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }


    public WebElement messageLoginSucceed() {
        return component.findElement(dialogMsgLoc);
    }
    public WebElement dialogButton() {
        return component.findElement(dialogBtnLoc);
    }

    public PopUpForm verifyMessage(String expectedMessage) {
        Assert.assertEquals(this.messageLoginSucceed().getText(), expectedMessage);

        return this;
    }

}