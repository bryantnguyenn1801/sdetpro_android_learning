package models.screens;

import com.epam.reportportal.annotations.Step;
import config.strings.StringConstants;
import io.appium.java_client.AppiumBy;
import models.screens.common.BaseScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.testng.Assert;
import utils.CommonUtils;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

import static config.strings.StringConstants.*;
import static utils.WaitUtilities.waitForElement;

public class FormComponentsScreen extends BaseScreen {
    private final static By formsPageTitleSel = AppiumBy.xpath("//android.widget.TextView[@text=\"Form components\"]");
    private final static By inputFieldSel = AppiumBy.accessibilityId("text-input");
    private final static By inputTxtResultSel = AppiumBy.accessibilityId("input-text-result");
    private final static By switchBtnSel = AppiumBy.accessibilityId("switch");
    private final static By switchTextSel = AppiumBy.accessibilityId("switch-text");
    private final static By dropDownSel = AppiumBy.xpath("//android.widget.EditText[@resource-id=\"text_input\"]");
    private final static By firstOptionSel = AppiumBy.xpath("//*[@text='webdriver.io is awesome']");
    private final static By secondOptionSel = AppiumBy.xpath("//*[@text='Appium is awesome']");
    private final static By thirdOptionSel = AppiumBy.xpath("//*[@text='This app is awesome']");
    private final static By activeBtnSel = AppiumBy.accessibilityId("button-Active");
    private final static By inactiveBtnSel = AppiumBy.accessibilityId("button-Inactive");
    private final String randomText = CommonUtils.getRandomFiveCharsString().toLowerCase();

    @Step("Verify the [Forms] section displayed")
    public FormComponentsScreen then_verifyFormsSectionDisplayed() throws Exception {
        String actualMsg = waitForElement(formsPageTitleSel).getText().trim();
        Assert.assertEquals(actualMsg, FORMS_COMPONENTS_TITLE);
        return this;
    }

    @Step("Input field")
    public FormComponentsScreen inputField() {
        clickOnElement(inputFieldSel)
                .sendKeys(randomText);
        hideKeyboard();
        return this;
    }

    @Step("Verify text result")
    public FormComponentsScreen verifyTextResult() {
        String actualMsg = waitForElement(inputTxtResultSel).getText().trim();
        Assert.assertEquals(actualMsg, randomText);
        return this;
    }

    @Step("Switch ONorOFF")
    public FormComponentsScreen switchONOFF() throws InterruptedException {
        String currentStatus = waitForElement(switchTextSel).getText().trim();
        if (currentStatus.equalsIgnoreCase(TURN_ON_SWITCH_MESSAGE)) {
            clickOnElement(switchBtnSel);
            Thread.sleep(3000);
            Assert.assertEquals(waitForElement(switchTextSel).getText().trim(), TURN_OFF_SWITCH_MESSAGE);
        } else {
            clickOnElement(switchBtnSel);
            Thread.sleep(3000);
            Assert.assertEquals(currentStatus, TURN_ON_SWITCH_MESSAGE);
        }
        return this;
    }

    @Step("Click Dropdown")
    public FormComponentsScreen clickDropdown(String dropdown) throws InterruptedException {
        clickOnElement(dropDownSel);
        switch (Objects.requireNonNull(StringConstants.DropDownValues.of(dropdown))) {
            case WEB_DRIVER_IO:
                clickOnElement(firstOptionSel);
                Assert.assertEquals(waitForElement(dropDownSel).getText().trim(), DROPDOWN[0]);
                return this;

            case APPIUM:
                clickOnElement(secondOptionSel);
                Assert.assertEquals(waitForElement(dropDownSel).getText().trim(), DROPDOWN[1]);
                return this;

            case THIS_APP:
                clickOnElement(thirdOptionSel);
                Assert.assertEquals(waitForElement(dropDownSel).getText().trim(), DROPDOWN[2]);
                return this;
        }
        Thread.sleep(5000);
        return null;
    }

    @Step("Click Active Button")
    public PopUpScreen clickActiveButton() {
        if (isElementClickable(activeBtnSel)){
            clickOnElementLocatedBy(activeBtnSel);
        } else {
            System.out.println("Active Button is unclickabled");
        }
        return new PopUpScreen();
    }

    @Step("Click Inactive Button")
    public FormComponentsScreen clickInactiveButton() {
        Assert.assertTrue(isElementClickable(inactiveBtnSel));
        return this;
    }
}