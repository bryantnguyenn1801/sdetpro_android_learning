package tests;

import base.AbstractTest;
import config.PageFactoryManager;
import config.strings.StringConstants;
import models.screens.common.BaseScreen;
import org.testng.annotations.Test;

public class FormComponentsTest extends AbstractTest {

    @Test(description = "E2E | Forms Components Test Flow",
            groups = {"regression"})
    public void forms_components() throws Exception {
        PageFactoryManager.get(BaseScreen.class)
                .getNavigation()
                .goFormsScreen()
                .then_verifyFormsSectionDisplayed()
                .inputField()
                .verifyTextResult()
                .switchONOFF()
                .clickDropdown(StringConstants.DropDownValues.WEB_DRIVER_IO.toString())
                .clickDropdown(StringConstants.DropDownValues.APPIUM.toString())
                .clickDropdown(StringConstants.DropDownValues.THIS_APP.toString())
                .clickInactiveButton()
                .clickActiveButton()
                .verifyBtnActive()
                .verifyBtnDisplayed()
                .clickOKBtn();
    }
}
