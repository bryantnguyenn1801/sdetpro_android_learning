package test_flows.authentication;

import io.appium.java_client.AppiumDriver;
import models.pages.LoginScreen;

public class LoginFlow {

    private AppiumDriver appiumDriver;
    private String username;
    private String password;

    public LoginFlow(AppiumDriver appiumDriver, String username, String password) {
        this.appiumDriver = appiumDriver;
        this.username = username;
        this.password = password;
    }

    public void login() {
        LoginScreen loginScreen = new LoginScreen(appiumDriver);
        if (!username.isEmpty()) {
            loginScreen.username().sendKeys(username);
        }
        if (!password.isEmpty()) {
            loginScreen.password().sendKeys(password);
        }
        loginScreen.loginBtn().click();
    }
}
