package learning.models.pages;

import io.appium.java_client.AppiumBy;
import learning.models.components.Component;
import learning.models.components.NavComponent;
import io.appium.java_client.AppiumDriver;
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