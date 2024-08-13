package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class MyListener  implements WebDriverListener {
    public void afterClick(WebElement element) {
        System.out.println("Clicking on an element: " + element);
    }

    public void afterQuit(WebDriver driver) {
        System.out.println("Test execution completed");

    }

    public void afterGet(WebDriver driver, String url) {
        System.out.println("Navigated to: " + url);
    }

    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        System.out.println("Typing in an element: " + element + "entered the value as " + keysToSend);
    }
}
