package learning;

import learning.capabilities.Platform;
import learning.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import learning.models.components.NavComponent;
import learning.models.pages.BasePage;

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