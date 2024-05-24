package core;

import com.epam.reportportal.annotations.Step;
import com.google.common.collect.ImmutableMap;
import config.ConfigManager;
import config.DriverManager;
import config.enums.MobilePlatform;
import core.android.AndroidInteractions;
import core.ios.IOSInteractions;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.Map;

import static utils.WaitUtilities.*;

public class ConciseApi {

    private static final String LOCATOR_SEPARATOR = ":" + StringUtils.SPACE;
    public AppiumDriver driver;
    public Logger logger = Logger.getLogger(ConciseApi.class);
    public int screenHeight;
    public int screenWidth;
    public MobileInteractions cmd;

    public ConciseApi(){
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

    @Step("Verify toast message: '{0}'")
    public ConciseApi verifyToast(String message){
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)){
            waitForToastMessageToAppear(message);
        } else{
            By locator = By.id("snackbar_text");
            String actual = WaitUtilities.waitForElementToAppear(locator).getText();
            Assert.assertTrue(actual.contains(message),
                            "Error toast is incorrect: Expected " + actual + " to contain " + message);
            hideKeyboard();
        }
        return this;
    }

    @Step("Verify toast message: '{0}'")
    public ConciseApi verifyToastMessage(String message){
        Assert.assertTrue(isToastMessageDisplayed(message, 2));
        return this;
    }

    @Step("Remove tool tip if displayed")
    public ConciseApi removeToolTipIfDisplayed(){
        By info = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                ?By.id("balloon_wrapper")
                : AppiumBy.accessibilityId("SetelToolTipView_messageLabel");
        if(isElementExistsLocatedBy(info,2)){
            waitForElementToAppear(info).click();
        }
        return this;
    }

    public ConciseApi goBack(){
        WaitUtilities.wait(1000);
        driver.navigate().back();

        return this;
    }

    public ConciseApi closeWebview(){
        By locator;
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            locator = By.name("WebBrowserViewController_closeButton");
            clickBackOrCloseBtn();
        }
        return this;
    }


    public WebElement clickOnElement(WebElement element){
        WebElement el = waitForElement(element);
        try{
            el.click();
        } catch (StaleElementReferenceException e){
            logger.warn(
                    "Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitForElement(element);
            el.click();
        }
        return el;
    }

    public WebElement clickOnElementLocatedBy(By locator){
        WebElement el = waitForElementToAppear(locator);
        try{
            el.click();
        } catch (StaleElementReferenceException e){
            logger.warn(
                    "Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitForElementToAppear(locator);
            el.click();
        }
        return el;
    }

    public WebElement typeValue(WebElement element, String value){
        WebElement el = waitForElement(element);
        try{
            el.clear();
            el.sendKeys(value);
        } catch (StaleElementReferenceException e){
            logger.warn(
                    "Element was stale immediately after waiting to be clickable in BaseSeleniumActions#click. Waiting for element to be clickable again.");
            el = waitForElement(element);
            el.click();
        }
        return el;
    }

    public boolean isElementClickable(By by){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException toe){
            return false;
        }
        return true;
    }

    public boolean isElementExistsLocatedBy(By locator, int timeOutInSeconds){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (NoSuchElementException nse){
            return false;
        } catch (TimeoutException toe){
            return false;
        }
        return true;
    }

    public ConciseApi hideKeyboard(){
        try{
            driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "hideKeyboard"));
        } catch (Exception e){
            // TODO: handle exception
        }
        return this;
    }

    public void swipeHorizontal(double startPercentage, double finalPercentage,
                                double anchorPercentage, int duration) throws Exception{
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
    public void swipeHorizontalToElement(WebElement e,double startPercentage, double finalPercentage, double anchorPercentage) throws Exception{
        int cycles = 10;
        int counter = 0;
        while(counter<cycles){
            try{
                if(!isElementDisplayed(e) || !isElementEnabled(e)){
                    throw new NoSuchElementException("Element not found yet");
                }
                break;
            }
            catch (NoSuchElementException er){
                counter ++;
                swipeHorizontal(startPercentage,finalPercentage,anchorPercentage,1);
                if(counter == cycles){
                    throw new NoSuchElementException("Element not found after "+ String.valueOf(cycles)+" swipes");
                }
            }
        }
    }
    public WebElement swipeHorizontalToElementLocatedBy(By locator, double startPercentage,
                                                        double finalPercentage, double anchorPercentage) throws Exception{
        int cycles = 10;
        int counter = 0;

        WebElement element = null;
        while (counter < cycles){
            try{
                element = driver.findElement(locator);
                if (element.isDisplayed()){
                    break;
                } else{
                    throw new org.openqa.selenium.NoSuchElementException(
                            "Element is present in DOM but is not visible.");
                }
            } catch (org.openqa.selenium.NoSuchElementException e){
                swipeHorizontal(startPercentage, finalPercentage, anchorPercentage, 1);
                counter++;
            }
            if (counter == cycles){
                throw new AssertionError(String.format(
                        "Target element [%s] was not visible or present in DOM after %d swipe attempts",
                        locator.toString(), cycles));
            }
        }
        return element;
    }

    public void swipeVertical(AppiumDriver driver, double startPercentage, double finalPercentage,
                              double anchorPercentage, int duration) throws Exception{
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

    public void scrollByTextAndroid(String menuText){
        try{
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
                            + menuText + "\").instance(0));"));
            WaitUtilities.wait(2000);
        } catch (NoSuchElementException nsee){
            throw new AssertionError(
                    "Element with text '" + menuText + "' was not found on the page.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void horizontalSwipeByElement(WebElement startElement){
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

    public void swipeUp(){
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

    public void scrollDownToElementByAccessibilityId(String name, Integer cycles){
        int counter = 0;

        while (counter < cycles){
            if (!driver.findElements(AppiumBy.accessibilityId(name)).isEmpty() &&
                    (driver.findElement(AppiumBy.accessibilityId(name)).isDisplayed())){
                break;
            } else{
                swipeUp();
                counter++;
                if (counter == cycles){
                    throw new org.openqa.selenium.NoSuchElementException(
                            String.format(
                                    "Target element was not visible or present in DOM after %d swipe attempts",
                                    cycles));
                }
            }
        }
    }

    public void scrollDownToElement(WebElement WebElement, double startPercentage,
                                    double finalPercentage) throws Exception{
        int cycles = 10;
        int counter = 0;

        while (counter < cycles){
            try{
                if (WebElement.isDisplayed()){
                    break;
                } else{
                    throw new org.openqa.selenium.NoSuchElementException(
                            "Element is present in DOM but is not visible.");
                }
            } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e){
                swipeVertical(driver, startPercentage, finalPercentage, 0.5, 1);
                counter++;
            }
            if (counter == cycles){
                throw new org.openqa.selenium.NoSuchElementException(String.format(
                        "Target element was not visible or present in DOM after %d swipe attempts",
                        cycles));
            }
        }
    }

    public void scrollDownToElementWhichPresentsInDomBy(By WebElementBy, double startPercentage, double finalPercentage) throws Exception {
        int cycles = 10;
        int counter = 0;

        while(counter < cycles) {
            try {
                if(driver.findElements(WebElementBy).size() < 1){
                    throw new NoSuchElementException("Element is present in DOM but is not visible.");
                }
                break;
            } catch (NoSuchElementException var9) {
                System.out.println("Element not found. Scroll down.");
                swipeVertical(this.driver, startPercentage, finalPercentage, 0.5D, 1);
                ++counter;
                if (counter == cycles) {
                    throw new NoSuchElementException(String.format("Target element was not visible or present in DOM after %d swipe attempts", Integer.valueOf(cycles)));
                }
            }
        }
    }

    public WebElement scrollDownToElementLocatedBy(By locator, double startPercentage,
                                                   double finalPercentage) throws Exception{
        int cycles = 10;
        int counter = 0;

        WebElement element = null;
        while (counter < cycles){
            try{
                element = driver.findElement(locator);
                if (element.isDisplayed()){
                    break;
                } else{
                    throw new org.openqa.selenium.NoSuchElementException(
                            "Element is present in DOM but is not visible.");
                }
            } catch (org.openqa.selenium.NoSuchElementException e){
                swipeVertical(driver, startPercentage, finalPercentage, 0.5, 1);
                counter++;
            }
            if (counter == cycles){
                throw new AssertionError(String.format(
                        "Target element [%s] was not visible or present in DOM after %d swipe attempts",
                        locator.toString(), cycles));
            }
        }
        return element;
    }

    @Step("User can see the following text on screen: '{0}'")
    public boolean isTextDisplayed(String expectedText){

        boolean isDisplayed = true;

        try {
            By by = ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)
                    ? AppiumBy.iOSClassChain("**/*[`label CONTAINS[cd] \"" + expectedText + "\"`]")
                    : AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + expectedText + "\")");
            waitForElementToAppear(by);
        }

        catch (NoSuchElementException nsee) {
            isDisplayed = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        if (!isDisplayed){
            throw new AssertionError(
                    "Element with text: " + expectedText + " is not found on the screen.");
        }

        return isDisplayed;
    }

    @Step("User cannot see the following text on screen: '{0}'")
    public boolean isTextNotDisplayed(String expectedText) {

        boolean isNotDisplayed = true;

        try {
            By by = ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)
                    ? AppiumBy.iOSClassChain("**/*[`label CONTAINS[cd] \"" + expectedText + "\"`]")
                    : AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + expectedText + "\")");
            waitForElementToDisAppear(by);
        }

        catch (NoSuchElementException nsee) {
            isNotDisplayed = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        if(!isNotDisplayed)
            throw new AssertionError("Element with text: " + expectedText + " is found on the screen.");

        return isNotDisplayed;
    }

    public void waitForLoadingIconToDisappear(Integer seconds){
        By by = ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)
                ? AppiumBy.accessibilityId("common-loader")
                : AppiumBy.id("progressBar");
        waitUntilElementToDisAppearLocatedBy(by, seconds, 250);
    }

    @Step("User waits the following toast message on screen: '{0}'")
    public void waitForToastMessageToAppear(String message){
        By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                ? AppiumBy.androidUIAutomator(String.format("new UiSelector().textContains(\"%s\")", message))
                : AppiumBy.iOSClassChain(String.format("**/*[`label CONTAINS[cd] \"%s\"`]", message));
        waitForElementToAppear(locator);
    }

    public void clickBackOrCloseBtn(){
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            By closeBtn = AppiumBy.accessibilityId("close black");
            if (isElementExistsLocatedBy(closeBtn, 2)) {
                WaitUtilities.waitForElementToAppear(closeBtn).click();
                WaitUtilities.wait(1000);
            }
            else {
                WaitUtilities.waitForElementToAppear(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label BEGINSWITH 'navigation' OR label CONTAINS[cd] 'back' OR label CONTAINS[cd] 'close' AND visible == 1`]")).click();
            }
        } else {
            ((AndroidDriver)this.driver).pressKey(new KeyEvent(AndroidKey.BACK));
        }
    }

    public void clickNextBtn(){
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)){
            waitForElementToAppear(
                    AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"topup right arrow\"`]"))
                    .click();
        } else{
            waitForElementToAppear(By.id("buttonNext1")).click();
        }
    }

    //only for android
    public void confirmPumpSelection(){
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)){
            By locator = AppiumBy.id("android:id/button1");
            boolean exists = isElementExistsLocatedBy(locator, 1);
            if (exists){
                clickOnElementLocatedBy(locator);
            }
        }
    }

    public boolean isElementEnabled(WebElement element){
        return element.isEnabled();
    }

    public boolean isElementEnabled(By locator){
        return waitForElementToAppear(locator).isEnabled();
    }

    public String getFieldValue(WebElement element){
        String fieldValue;
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)){
            fieldValue = element.getText();
        } else{
            fieldValue = element.getAttribute("value");
        }
        return fieldValue;
    }

    public void tapOnElement(WebElement el){
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)){
            driver.executeScript("mobile: tap", el);
        } else{
            Map<String, Object> args = new HashMap<>();
            args.put("element", ((WebElement) el).getAttribute("id"));
            args.put("x", 2);
            args.put("y", 2);
            driver.executeScript("mobile: tap", args);
        }
    }


    public void isTextArrayPresent(String... textTitles) throws Exception{
        for (int i = 0; i < textTitles.length; i++){
            By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                    ? AppiumBy.androidUIAutomator(
                    String.format("new UiSelector().textContains(\"%s\")", textTitles[i]))
                    : AppiumBy.iOSClassChain(String
                    .format("**/XCUIElementTypeStaticText[`label CONTAINS[cd] \"%s\"`]",
                            textTitles[i]));
            scrollDownToElementLocatedBy(locator, 0.8, 0.6);
        }
    }

    public void dismissDialog(){
        By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                ? AppiumBy.androidUIAutomator("new UiSelector().textContains(\"NO\")")
                : AppiumBy.iOSClassChain("**/*[`label CONTAINS[cd] \"NO\"`]");
        clickOnElementLocatedBy(locator);
    }

    @Step
    public void confirmLocationPermissionDialog(){
        By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)
                ? AppiumBy.accessibilityId("Allow Once")
                : By.id("com.android.packageinstaller:id/permission_allow_button");
        clickOnElementLocatedBy(locator);
    }

    @Step
    public void confirmAccessPermissionDialog(){
        By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)
                ? AppiumBy.iOSClassChain("**/*[`label CONTAINS[cd] \"OK\" OR label == \"Allow Access to All Photos\"`]")
                : AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"^(OK|ALLOW|Allow)*\")");
        boolean exists = isElementExistsLocatedBy(locator, 3);
        if (exists){
            clickOnElementLocatedBy(locator);
        }
    }

    @Step
    public void verifyElementEnabled(WebElement e){
        waitForElement(e);
        Assert.assertTrue(isElementEnabled(e));
    }

    @Step
    public void verifyElementDisabled(WebElement e){
        waitForElement(e);
        Assert.assertFalse(isElementEnabled(e));
    }

    public void swipeDown(){
        new WebDriverWait(this.driver, Duration.ofMillis(30));
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 2))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 2 +150))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));

    }

    @Step
    public void verifyElementChecked(WebElement e){
        if(ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)){
            Assert.assertEquals(e.getAttribute("checked"),"true");
        }
        else{
            Assert.assertEquals(e.getAttribute("value"),"1");
        }
    }
    @Step
    public void waitForToastDisappear(String message){
        By locator = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID) ? AppiumBy.androidUIAutomator(String.format("new UiSelector().textContains(\"%s\")", message)) : AppiumBy.iOSClassChain(String.format("**/*[`label CONTAINS[cd] \"%s\"`]", message));
        waitUntilElementToDisAppearLocatedBy(locator,30,1);
    }

    @Step("User tap [GOT IT] Btn if displayed")
    public void tapGotItIfDisplayed(){

        By gotItBtn = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                ? By.xpath("//*[contains(@resource-id,'button_ok') or (contains(@resource-id,'button1') and text()='Got it')]")
                : AppiumBy.accessibilityId("GOT IT");
        if(isElementExistsLocatedBy(gotItBtn,2)){
            waitForElementToAppear(gotItBtn).click();
        }
    }

    /**
     * Get list of Mobile Element with message format from (Android|IOS) Findbys annotaion
     * @param elements  : List of mobile elements
     * @param args      : arguments of message format
     * @return List of elements with right locator field
     * Pay attention: Don't use Group strategy for this method
     */
    public List<WebElement> getElementsWithArgs(List<WebElement> elements, Object... args) {
        if (args != null && args.length != 0) {
            String strLocator = elements.get(0).toString().substring(elements.get(0).toString().indexOf("{") + 1, elements.get(0).toString().lastIndexOf("}"));
            String strategy = strLocator.indexOf(LOCATOR_SEPARATOR) == -1 ? "" : strLocator.substring(0, strLocator.indexOf(LOCATOR_SEPARATOR)).trim();
            String locatorValue = strLocator.indexOf(LOCATOR_SEPARATOR) == -1 ? "" : strLocator.substring(strLocator.indexOf(LOCATOR_SEPARATOR) + 2, strLocator.length()).trim();
            if (strategy == null) {
                return elements;
            } else {
                try {
                    return driver.findElements(parameter(strategy, locatorValue, args));
                } catch (NoSuchElementException | StaleElementReferenceException ex) {
                    return elements;
                }
            }
        } else {
            return elements;
        }
    }

    /**
     * Get Mobile Element with message format from (Android|IOS) Findby annotaion
     * @param element   : mobile element
     * @param args      : arguments of message format
     * @return mobile element by right locator field with message format
     * Pay attention: Don't use Group strategy for this method
     */
    public WebElement getElementWithArgs(WebElement element, Object... args) {
        if (args != null && args.length != 0) {
            String strLocator = element.toString().substring(element.toString().indexOf("{") + 1, element.toString().lastIndexOf("}"));
            String strategy = strLocator.indexOf(LOCATOR_SEPARATOR) == -1 ? "" : strLocator.substring(0, strLocator.indexOf(LOCATOR_SEPARATOR)).trim();
            String locatorValue = strLocator.indexOf(LOCATOR_SEPARATOR) == -1 ? "" : strLocator.substring(strLocator.indexOf(LOCATOR_SEPARATOR) + 2, strLocator.length()).trim();
            if (strategy == null) {
                return element;
            } else {
                try {
                    return (WebElement) driver.findElement(parameter(strategy, locatorValue, args));
                } catch (NoSuchElementException | StaleElementReferenceException ex) {
                    return element;
                }
            }
        } else {
            return element;
        }
    }

    public boolean isToastMessageDisplayed(String msg) {
        String xpathLocator = isAndroidPlatform()
                ? String.format("//*[@text = '%s']", msg)
                : String.format("//*[@label = '%s']", msg);
        PointOption toastEle = cmd.getMiddlePointOfElementFromDOM(xpathLocator);
        return toastEle != null;
    }

    public boolean isToastMessageDisplayed(String msg, long timeout) {
        int retry = 0;
        boolean isDisplayed = isToastMessageDisplayed(msg);
        while (!isDisplayed && retry < timeout) {
            WaitUtilities.wait(1000);
            isDisplayed = isToastMessageDisplayed(msg);
            retry++;
        }
        return isDisplayed;
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