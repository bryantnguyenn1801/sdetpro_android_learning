package tests;

import base.AbstractTest;
import config.PageFactoryManager;
import models.screens.common.BaseScreen;
import org.testng.annotations.Test;
import utils.DataUser;

public class SignUpTest extends AbstractTest {
    DataUser user = new DataUser();

    @Test(groups = {"regression"})
    public void signUp_with_valid_email_and_pwd() {
        PageFactoryManager.get(BaseScreen.class)
                .getNavigation()
                .goLoginScreen()
                .clickSignUpForm()
                .inputEmail(user.validEmail)
                .inputPassword(user.validPassword)
                .confirmPassword(user.validPassword)
                .clickSignUpButton()
                .verifySignUp()
                .clickOKButton();

    }
}
