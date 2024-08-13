package tests;

import base.AbstractTest;
import config.PageFactoryManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.screens.common.BaseScreen;
import org.testng.annotations.Test;

import static data.user.MobileUserRegistry.USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD;

@Epic("Bryant is Practicing")
@Feature("Sign Up Account")
public class SignUpTest extends AbstractTest {

    @Test(groups = {"regression"})
    public void signUp_with_valid_email_and_pwd() {
        PageFactoryManager.get(BaseScreen.class)
                .getNavigation()
                .goLoginScreen()
                .clickSignUpForm()
                .inputEmail(USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD.validEmail)
                .inputPassword(USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD.validEmail)
                .confirmPassword(USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD.validEmail)
                .clickSignUpButton()
                .verifySignUp()
                .clickOKBtn();

    }
}
