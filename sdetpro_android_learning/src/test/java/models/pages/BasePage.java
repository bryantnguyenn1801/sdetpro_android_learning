package models.pages;

import io.appium.java_client.AppiumDriver;
import models.components.NavComponent;

public class BasePage {

    protected final AppiumDriver appiumDriver;

    public BasePage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public NavComponent navComponent() {
        return new NavComponent(this.appiumDriver);
    }
}