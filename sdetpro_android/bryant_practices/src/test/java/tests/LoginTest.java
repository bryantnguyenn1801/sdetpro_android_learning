package tests;

import base.AbstractTest;
import config.PageFactoryManager;
import models.screens.common.BaseScreen;
import org.testng.annotations.Test;

import static data.user.MobileUserRegistry.*;

public class LoginTest extends AbstractTest {

    @Test(description = "E2E | Login test flow with invalid email", groups = {"regression"})
    public void user_login_with_invalid_email() throws Exception {
        PageFactoryManager.get(BaseScreen.class)
                .getNavigation()
                .goLoginScreen()
                .then_verifyLoginSectionDisplayed()
                .inputInvalidEmail(USER_LOGIN_WITH_INVALID_EMAIL.invalidEmail)
                .inputValidPassword(USER_LOGIN_WITH_INVALID_EMAIL.validPassword)
                .clickLoginButton()
                .verifyIncorrectEmail();
    }

    @Test(description = "E2E | Login test flow with invalid password", groups = {"regression"})
    public void user_login_with_invalid_password() throws Exception {
        PageFactoryManager.get(BaseScreen.class)
                .getNavigation()
                .goLoginScreen()
                .then_verifyLoginSectionDisplayed()
                .inputValidEmail(USER_LOGIN_WITH_INVALID_PASSWORD.validEmail)
                .inputInvalidPassword(USER_LOGIN_WITH_INVALID_PASSWORD.invalidPassword)
                .clickLoginButton()
                .verifyIncorrectPassword();
    }

    @Test(description = "E2E | Login test flow with valid data", groups = {"regression"})
    public void user_login_with_valid_email_and_password() throws Exception {
        PageFactoryManager.get(BaseScreen.class)
                .getNavigation()
                .goLoginScreen()
                .then_verifyLoginSectionDisplayed()
                .inputValidEmail(USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD.validEmail)
                .inputValidPassword(USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD.validPassword)
                .clickLoginButtonSuccess()
                .verifyLogin()
                .clickOKBtn();
    }
}
