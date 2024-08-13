package core;

import config.ConfigManager;
import config.enums.MobilePlatform;
import org.openqa.selenium.By;

public class MultipleBy {
    private By androidLocator;
    private By iosLocator;

    public MultipleBy android(By locator) {
        this.androidLocator = locator;
        return this;
    }

    public MultipleBy ios(By locator) {
        this.iosLocator = locator;
        return this;
    }

    public By getByPlatform() {
        return ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID) ? androidLocator : iosLocator;
    }
}
