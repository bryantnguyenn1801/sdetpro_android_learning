package learning.test_flows.authentication;

import io.appium.java_client.AppiumDriver;
import learning.models.pages.LoginScreen;
import learning.models.pages.PopUpForm;
import learning.test_flows.BaseFlow;
import org.apache.commons.validator.routines.EmailValidator;
import org.testng.Assert;

public class LoginFlow extends BaseFlow {

    private String email;
    private String password;
    private LoginScreen loginScreen;


    public LoginFlow(AppiumDriver appiumDriver, String email, String password) {
        super(appiumDriver);
        this.email = email;
        this.password = password;
        loginScreen = new LoginScreen(appiumDriver);
    }

    public void login() {
        this.loginScreen = new LoginScreen(appiumDriver);
        if (!email.isEmpty()) {
            loginScreen.email().clear();
            loginScreen.email().sendKeys(email);
        }
        if (!password.isEmpty()) {
            loginScreen.password().clear();
            loginScreen.password().sendKeys(password);
        }
        loginScreen.loginBtn().click();

    }

    public void verifyLogin() {
        final int minLength = 8;
        boolean isEmailValid = EmailValidator.getInstance().isValid(email);
        boolean isPasswordValid = password.length() >= minLength;

        if (isEmailValid && isPasswordValid) {
            verifyCorrectLoginCreds();
        }
        if (!isEmailValid) {
            verifyIncorrectEmail();
        }
        if (!isPasswordValid) {
            verifyIncorrectPassword();
        }
    }

    public void verifyCorrectLoginCreds() {
        String expectedSuccessMsg = "You are logged in!";
        String actualSuccessMsg = loginScreen.openPopUpDialog().getDialogMsg();
        Assert.assertEquals(expectedSuccessMsg, actualSuccessMsg, "[ERR] Wrong success login msg");
        loginScreen.openPopUpDialog().btnDialog().click();
    }

    public void verifyIncorrectEmail() {
//        LoginScreen loginScreen = new LoginScreen(appiumDriver);
        String expectedStr = "Please enter a valid email address";
        String actualStr = loginScreen.getInvalidEmail();
        if (!actualStr.equalsIgnoreCase(expectedStr)) {
            throw new RuntimeException("Error: Invalid email");
        }
    }

    public void verifyIncorrectPassword() {
//        LoginScreen loginScreen = new LoginScreen(appiumDriver);
        String expectedStr = "Please enter at least 8 characters";
        String actualStr = loginScreen.getInvalidPassword();
        if (!actualStr.equalsIgnoreCase(expectedStr)) {
            throw new RuntimeException("Error: Invalid password");
        }
    }
}
