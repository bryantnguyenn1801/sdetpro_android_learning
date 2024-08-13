package learning;

import learning.context.Contexts;
import learning.context.WaitMoreThanOneContext;
import learning.driver.DriverFactory;
import learning.capabilities.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HandleHybridContext {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            // Click on the Web view button
            By formsBtnLoc = AppiumBy.accessibilityId("Webview");
            // Navigate to [Web view] screen
            appiumDriver.findElement(formsBtnLoc).click();

            // Get platform info under test session
            Capabilities caps = appiumDriver.getCapabilities();
            String currentPlatform = CapabilityHelpers.getCapability(caps, "platformName", String.class);

            // Custom Explicit wait
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15L));
            wait.until(new WaitMoreThanOneContext(appiumDriver));
            //casting
            if (Platform.valueOf(currentPlatform).equals(Platform.ANDROID)) {
                AndroidDriver androidDriver = ((AndroidDriver) appiumDriver);
                System.out.println(androidDriver.getContextHandles());

                // Switch to WEB VIEW CONTEXT
                androidDriver.context(Contexts.WEB_VIEW);

                // Interact with WEB VIEW
                WebElement navToggleBtnEle = androidDriver.findElement(By.cssSelector("button[class*='navbar__toggle']"));
                navToggleBtnEle.click();

                // Get all menu item elements
                List<WebElement> menuItemElems = androidDriver.findElements(
                        By.cssSelector(".menu__list li a"));
                // TODO: need to check the element list is not empty before looping for verifying
                if (menuItemElems.isEmpty()) {
                    throw new RuntimeException("The menuItemElems is empty");
                }

                List<MenuItemData> currentNavItemData = new ArrayList<>();
                for (WebElement menuItemElem : menuItemElems) {
                    String itemText = menuItemElem.getText();
                    if (itemText.isEmpty()) {
                        itemText = menuItemElem.getAttribute("aria-label");
                    }
                    String itemHref = menuItemElem.getAttribute("href");
                    currentNavItemData.add(new MenuItemData(itemText, itemHref));
                }

                // Verification
                System.out.println(currentNavItemData);

                // SWITCH back to the native context for native elements
                androidDriver.context(Contexts.NATIVE);
                androidDriver.findElement(AppiumBy.accessibilityId("Forms")).click();


            } else {
                System.out.println(((IOSDriver) appiumDriver).getContextHandles());
            }


            // DEBUG PURPOSE ONLY
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appiumDriver.quit();
    }

    // TODO: good to explore - Lombook
    public static class MenuItemData {

        private final String name;
        private final String href;

        public MenuItemData(String name, String href) {
            this.name = name;
            this.href = href;
        }

        public String getName() {
            return name;
        }

        public String getHref() {
            return href;
        }

        @Override
        public String toString() {
            return "MenuItemData{" +
                    "name='" + name + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }
}