package core.ios;

import com.google.common.collect.ImmutableMap;
import core.MobileInteractions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;

public class IOSInteractions extends MobileInteractions {
    private IOSDriver driver;

    public IOSInteractions(IOSDriver driver) {
        this.driver = driver;
    }

    @Override
    public void pressEnterKeyboard() {
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "enter"));
    }

    @Override
    public PointOption getMiddlePointOfElementFromDOM(String xpathExpression) {
        return null;
    }
}
