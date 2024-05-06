package models.pages;

import io.appium.java_client.AppiumDriver;
import models.components.Component;
import models.components.NavComponent;
import org.openqa.selenium.By;

public class BasePage extends Component {

    protected final AppiumDriver appiumDriver;

    public BasePage(AppiumDriver appiumDriver) {
        super(appiumDriver, appiumDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout")));
        this.appiumDriver = appiumDriver;
    }

    public NavComponent navComponent() {
        return findComponent(NavComponent.class);
    }
}