package api_learning;

import capabilities.Platform;
import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import models.components.NavComponent;
import models.pages.BasePage;

public class TestComponentBaseModel {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            BasePage page = new BasePage(appiumDriver);
            NavComponent navComponent = page.navComponent();
            // Navigate to the login screen
            navComponent.clickOnLoginIcon();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }

}