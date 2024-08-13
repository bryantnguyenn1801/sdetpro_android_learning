package learning.models.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import learning.models.components.ComponentIdSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ComponentIdSelector(value = "android:id/parentPanel")
public class PopUpForm extends BasePage {
    private By dialogMsgLoc = AppiumBy.id("android:id/message");
    private By dialogBtnLoc = AppiumBy.id("android:id/button1");

    public PopUpForm(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }


    public String getDialogMsg(){
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(dialogMsgLoc));
        return this.appiumDriver.findElement(dialogMsgLoc).getText();
    }

    public WebElement btnDialog(){
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(dialogBtnLoc));
        return this.appiumDriver.findElement(dialogBtnLoc);
    }



}