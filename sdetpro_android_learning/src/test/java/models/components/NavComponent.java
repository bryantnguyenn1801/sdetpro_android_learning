package models.components;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@ComponentXpathSelector(value = "//android.view.ViewGroup[2]/android.view.View")
public class NavComponent extends Component{

    private final AppiumDriver appiumDriver;

    private final static By homeIconSel = AppiumBy.accessibilityId("fasf");
    private final static By webviewIconSel = AppiumBy.accessibilityId("asfdas");
    private final static By loginIconSel = AppiumBy.accessibilityId("Login");
    private final static By FormsIconSel = AppiumBy.accessibilityId("afsaf");
    private final static By swipeIconSel = AppiumBy.accessibilityId("faf");
    private final static By dragIconSel = AppiumBy.accessibilityId("fasfa");

    public NavComponent(AppiumDriver appiumDriver, WebElement component) {
        super(appiumDriver, component);
        this.appiumDriver = appiumDriver;
    }

    public void clickOnLoginIcon() {
        component.findElement(loginIconSel).click();
        // TODO: Make sure we are on the Login screen. Implement below...
    }

}