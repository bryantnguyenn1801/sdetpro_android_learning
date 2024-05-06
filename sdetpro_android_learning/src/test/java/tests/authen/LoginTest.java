package tests.authen;

import capabilities.Platform;
import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import models.components.NavComponent;
import models.pages.BasePage;
import test_flows.authentication.LoginFlow;

public class LoginTest {
    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            BasePage page = new BasePage(appiumDriver);
            NavComponent navComponent = page.navComponent();
            // Navigate to the login screen
            navComponent.clickOnLoginIcon();

            LoginFlow loginFlow = new LoginFlow(appiumDriver, "bryant.nguyen@gmail.com", "12345678");
            loginFlow.login();
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }
}
