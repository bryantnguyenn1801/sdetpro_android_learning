package learning.test_flows.webview;

import learning.models.pages.BasePage;
import io.appium.java_client.AppiumDriver;

public class BaseFlow {
    protected final AppiumDriver appiumDriver;

    public BaseFlow(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void gotoLoginScreen(){
        new BasePage(appiumDriver).navComponent().clickOnLoginIcon();
    }
}
