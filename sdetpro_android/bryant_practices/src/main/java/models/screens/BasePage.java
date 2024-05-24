package models.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import learning.models.components.Component;
import learning.models.components.NavComponent;
import org.openqa.selenium.By;

public class BasePage extends Component {
    private static final By rootEle = AppiumBy.xpath("/hierarchy/android.widget.FrameLayout");

    protected final AppiumDriver appiumDriver;

    public BasePage(AppiumDriver appiumDriver) {
        super(appiumDriver, rootEle);
        this.appiumDriver = appiumDriver;
    }

    public NavComponent navComponent() {
        return findComponent(NavComponent.class);
    }
}