package core;

import com.epam.reportportal.annotations.Step;
import config.ConfigManager;
import config.DriverManager;
import config.enums.MobilePlatform;
import core.android.AndroidInteractions;
import core.ios.IOSInteractions;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.WaitUtilities;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static utils.WaitUtilities.*;

public class ConciseApi {
    public AppiumDriver driver;
    WebElement element;
    public Logger logger = Logger.getLogger(ConciseApi.class);
    public int screenHeight;
    public int screenWidth;
    public MobileInteractions cmd;

    public ConciseApi() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        screenHeight = driver.manage().window().getSize().getHeight();
        screenWidth = driver.manage().window().getSize().getWidth();
        this.cmd = isAndroidPlatform() ? new AndroidInteractions((AndroidDriver) driver) : new IOSInteractions((IOSDriver) driver);
    }

    public AndroidInteractions android() {
        return (AndroidInteractions) this.cmd;
    }

    public IOSInteractions ios() {
        return (IOSInteractions) this.cmd;
    }

    public boolean isAndroidPlatform() {
        return ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID);
    }

    @Step("Remove tool tip if displayed")
    public ConciseApi removeToolTipIfDisplayed() {
        By info = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                ? By.id("balloon_wrapper")
                : AppiumBy.accessibilityId("SetelToolTipView_messageLabel");
        if (isElementExistsLocatedBy(info, 2)) {
            waitForElementToAppear(info).click();
        }
        return this;
    }

    public ConciseApi goBack() {
        WaitUtilities.wait(1000);
        driver.navigate().back();

        return this;
    }

    public WebElement clickOnElement(By element) {
        WebElement el = waitForElement(element);
        try {
            el.click();
        } catch (StaleElementReferenceException e) {
            logger.warn(
                    "Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitForElement(element);
            el.click();
        }
        return el;
    }

    public WebElement clickOnElementLocatedBy(By locator) {
        WebElement el = waitForElementToAppear(locator);
        try {
            el.click();
        } catch (StaleElementReferenceException e) {
            logger.warn(
                    "Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitForElementToAppear(locator);
            el.click();
        }
        return el;
    }

    public WebElement typeValue(By element, String value) {
        WebElement el = waitForElement(element);
        try {
            el.clear();
            el.sendKeys(value);
        } catch (StaleElementReferenceException e) {
            logger.warn(
                    "Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitForElement(element);
            el.click();
        }
        return el;
    }

    public boolean isElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException toe) {
            return false;
        }
        return true;
    }

    public boolean isElementExistsLocatedBy(By locator, int timeOutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (NoSuchElementException nse) {
            return false;
        } catch (TimeoutException toe) {
            return false;
        }
        return true;
    }

    public ConciseApi hideKeyboard() {
        try {
            ((AndroidDriver) driver).hideKeyboard();
        } catch (Exception var2) {
        }
        return this;
    }

    public void swipeHorizontal(double startPercentage, double finalPercentage,
                                double anchorPercentage, int duration) throws Exception {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.height * anchorPercentage);
        int startPoint = (int) (size.width * startPercentage);
        int endPoint = (int) (size.width * finalPercentage);
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startPoint, anchor))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), endPoint, anchor))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }

    public void swipeHorizontalToElement(By e, double startPercentage, double finalPercentage, double anchorPercentage) throws Exception {
        int cycles = 10;
        int counter = 0;
        while (counter < cycles) {
            try {
                if (!isElementDisplayed(e) || !isElementEnabled(e)) {
                    throw new NoSuchElementException("Element not found yet");
                }
                break;
            } catch (NoSuchElementException er) {
                counter++;
                swipeHorizontal(startPercentage, finalPercentage, anchorPercentage, 1);
                if (counter == cycles) {
                    throw new NoSuchElementException("Element not found after " + String.valueOf(cycles) + " swipes");
                }
            }
        }
    }

    public WebElement swipeHorizontalToElementLocatedBy(By locator, double startPercentage,
                                                        double finalPercentage, double anchorPercentage) throws Exception {
        int cycles = 10;
        int counter = 0;

        WebElement element = null;
        while (counter < cycles) {
            try {
                element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    break;
                } else {
                    throw new org.openqa.selenium.NoSuchElementException(
                            "Element is present in DOM but is not visible.");
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                swipeHorizontal(startPercentage, finalPercentage, anchorPercentage, 1);
                counter++;
            }
            if (counter == cycles) {
                throw new AssertionError(String.format(
                        "Target element [%s] was not visible or present in DOM after %d swipe attempts",
                        locator.toString(), cycles));
            }
        }
        return element;
    }

    public void swipeVertical(AppiumDriver driver, double startPercentage, double finalPercentage,
                              double anchorPercentage, int duration) throws Exception {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * finalPercentage);
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startPoint, anchor))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), endPoint, anchor))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));
    }

    public void scrollByTextAndroid(String menuText) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
                            + menuText + "\").instance(0));"));
            WaitUtilities.wait(2000);
        } catch (NoSuchElementException nsee) {
            throw new AssertionError(
                    "Element with text '" + menuText + "' was not found on the page.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void horizontalSwipeByElement(WebElement startElement) {
        int startX = startElement.getLocation().getX();
        int startY = startElement.getLocation().getY();

        int endX = startX + startElement.getSize().getWidth();
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), endX - 20, startY + 5))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX + 20, startY + 5))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));
    }

    public void swipeUp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30));
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 2))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 2 - 100))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));

    }

    public void scrollDownToElementByAccessibilityId(String name, Integer cycles) {
        int counter = 0;

        while (counter < cycles) {
            if (!driver.findElements(AppiumBy.accessibilityId(name)).isEmpty() &&
                    (driver.findElement(AppiumBy.accessibilityId(name)).isDisplayed())) {
                break;
            } else {
                swipeUp();
                counter++;
                if (counter == cycles) {
                    throw new org.openqa.selenium.NoSuchElementException(
                            String.format(
                                    "Target element was not visible or present in DOM after %d swipe attempts",
                                    cycles));
                }
            }
        }
    }

    public void scrollDownToElementById(String name, Integer cycles) {
        int counter = 0;

        while (counter < cycles) {
            if (!driver.findElements(AppiumBy.id(name)).isEmpty() &&
                    (driver.findElement(AppiumBy.id(name)).isDisplayed())) {
                break;
            } else {
                swipeUp();
                counter++;
                if (counter == cycles) {
                    throw new org.openqa.selenium.NoSuchElementException(
                            String.format(
                                    "Target element was not visible or present in DOM after %d swipe attempts",
                                    cycles));
                }
            }
        }
    }

    public void scrollDownToElement(WebElement WebElement, double startPercentage,
                                    double finalPercentage) throws Exception {
        int cycles = 10;
        int counter = 0;

        while (counter < cycles) {
            try {
                if (WebElement.isDisplayed()) {
                    break;
                } else {
                    throw new org.openqa.selenium.NoSuchElementException(
                            "Element is present in DOM but is not visible.");
                }
            } catch (org.openqa.selenium.NoSuchElementException |
                     org.openqa.selenium.StaleElementReferenceException e) {
                swipeVertical(driver, startPercentage, finalPercentage, 0.5, 1);
                counter++;
            }
            if (counter == cycles) {
                throw new org.openqa.selenium.NoSuchElementException(String.format(
                        "Target element was not visible or present in DOM after %d swipe attempts",
                        cycles));
            }
        }
    }


    public WebElement scrollDownToElementLocatedBy(By locator, double startPercentage,
                                                   double finalPercentage) throws Exception {
        int cycles = 10;
        int counter = 0;

        WebElement element = null;
        while (counter < cycles) {
            try {
                element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    break;
                } else {
                    throw new org.openqa.selenium.NoSuchElementException(
                            "Element is present in DOM but is not visible.");
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                swipeVertical(driver, startPercentage, finalPercentage, 0.5, 1);
                counter++;
            }
            if (counter == cycles) {
                throw new AssertionError(String.format(
                        "Target element [%s] was not visible or present in DOM after %d swipe attempts",
                        locator.toString(), cycles));
            }
        }
        return element;
    }


    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    public boolean isElementEnabled(By locator) {
        return waitForElementToAppear(locator).isEnabled();
    }

    public String getFieldValue(WebElement element) {
        String fieldValue;
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            fieldValue = element.getText();
        } else {
            fieldValue = element.getAttribute("value");
        }
        return fieldValue;
    }

    public void tapOnElement(WebElement el) {
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            driver.executeScript("mobile: tap", el);
        } else {
            Map<String, Object> args = new HashMap<>();
            args.put("element", ((WebElement) el).getAttribute("id"));
            args.put("x", 2);
            args.put("y", 2);
            driver.executeScript("mobile: tap", args);
        }
    }

    @Step
    public void verifyElementEnabled(By e) {
        waitForElement(e);
        Assert.assertTrue(isElementEnabled(e));
    }

    @Step
    public void verifyElementDisabled(By e) {
        waitForElement(e);
        Assert.assertFalse(isElementEnabled(e));
    }

    public void swipeDown() {
        new WebDriverWait(this.driver, Duration.ofMillis(30));
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 2))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 2 + 150))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));
    }

    @Step
    public void verifyElementChecked(WebElement e) {
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            Assert.assertEquals(e.getAttribute("checked"), "true");
        } else {
            Assert.assertEquals(e.getAttribute("value"), "1");
        }
    }

    @Step
    public void waitForToastDisappear(String message) {
        By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID) ? AppiumBy.androidUIAutomator(String.format("new UiSelector().textContains(\"%s\")", message)) : AppiumBy.iOSClassChain(String.format("**/*[`label CONTAINS[cd] \"%s\"`]", message));
        waitUntilElementToDisAppearLocatedBy(locator, 30, 1);
    }

    private By parameter(String strategy, String locatorValue, Object... args) {
        String using = MessageFormat.format(locatorValue.replace("'", "''"), args);
        switch (strategy.toLowerCase()) {
            case "by.androiduiautomator":
                return AppiumBy.androidUIAutomator(using);

            case "by.iosclasschain":
                return AppiumBy.iOSClassChain(using);

            case "by.iosnspredicate":
                return AppiumBy.iOSNsPredicateString(using);

            case "by.id":
                return AppiumBy.id(using);

            case "by.classname":
                return AppiumBy.className(using);

            case "by.tagname":
                return AppiumBy.tagName(using);

            case "by.accessibilityid":
                return AppiumBy.accessibilityId(using);

            case "by.xpath":
                return AppiumBy.xpath(using);

            default:
                throw new RuntimeException("Unsupported the locator type " + strategy);
        }
    }

    public void refreshScreen() {
        driver.getPageSource();
    }
}