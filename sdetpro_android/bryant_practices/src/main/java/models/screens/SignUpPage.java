package models.screens;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SignUpPage {

    private final By loginTabBtnLoc = accessibilityId("Login");
    private final By signUpFormLoc = accessibilityId("button-sign-up-container");
    private final By inputEmailLoc = accessibilityId("input-email");
    private final By inputPasswordLoc = accessibilityId("input-password");
    private final By confirmPasswordLoc = accessibilityId("input-repeat-password");
    private final By signUpBtnLoc = accessibilityId("button-SIGN UP");
    private final By dialogMsgLoc = id("android:id/message");
    private final By dialogBtnLoc = className("android.widget.Button");
    private final WebDriverWait wait;

    public SignUpPage(AndroidDriver androidDriver) {
        this.wait = new WebDriverWait(androidDriver, ofSeconds(10L));
    }

    public String signUpUser(String userName, String userPwd) {
        waitForElement(loginTabBtnLoc).click();
        waitForElement(signUpFormLoc)
                .click();
        waitForElement(inputEmailLoc)
                .sendKeys(userName);
        waitForElement(inputPasswordLoc)
                .sendKeys(userPwd);
        waitForElement(confirmPasswordLoc)
                .sendKeys(userPwd);
        waitForElement(signUpBtnLoc)
                .click();
        return waitForVisibility(dialogMsgLoc).getText().trim();
    }

    public void clickOKButton() {
        waitForElement(dialogBtnLoc).click();
    }

    private WebElement waitForElement(By locator) {
        return wait.until(elementToBeClickable(locator));
    }

    private WebElement waitForVisibility(By locator) {
        return wait.until(visibilityOfElementLocated(locator));
    }

}
