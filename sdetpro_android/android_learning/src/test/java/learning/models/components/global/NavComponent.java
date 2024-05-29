package learning.models.components.global;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import learning.models.components.Component;
import learning.models.components.ComponentXpathSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@ComponentXpathSelector(value = "//android.view.ViewGroup[2]/android.view.View")
public class NavComponent extends Component {

    private final AppiumDriver appiumDriver;

    private final static By homeIconSel = AppiumBy.accessibilityId("fasf");
    private final static By webviewIconSel = AppiumBy.accessibilityId("asfdas");
    private final static By loginIconSel = AppiumBy.accessibilityId("Login");
    private final static By FormsIconSel = AppiumBy.accessibilityId("afsaf");
    private final static By swipeIconSel = AppiumBy.accessibilityId("faf");
    private final static By dragIconSel = AppiumBy.accessibilityId("fasfa");
    private final static By loginPageTitleSel = AppiumBy.xpath("//android.widget.TextView[@text=\"Login / Sign up Form\"]");


    public NavComponent(AppiumDriver appiumDriver, By component) {
        super(appiumDriver, component);
        this.appiumDriver = appiumDriver;
    }

    public void clickOnLoginIcon() {
        getComponent().findElement(loginIconSel).click();
        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15));
        WebElement actualStr = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPageTitleSel));
        String expectedMsg = "Login / Sign up Form";
        if (!(actualStr.getText()).equalsIgnoreCase(expectedMsg)){
            throw new RuntimeException("Error: Could not navigate to Login Page");
        }
    }

}