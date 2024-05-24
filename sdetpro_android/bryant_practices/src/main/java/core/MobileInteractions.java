package core;

import config.ConfigManager;
import config.DriverManager;
import config.enums.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Collections;

public abstract class MobileInteractions {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    AppiumDriver appiumDriver = DriverManager.getDriver();

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        VERTICAL,
        HORIZONTAL,
        VERTICAL_DOWN_FIRST,
        HORIZONTAL_RIGHT_FIRST
    }

    static final int DEFAULT_TOUCH_ACTION_DURATION = 1000;
    static final int DEFAULT_MAX_SWIPE_COUNT = 50;
    static final int DEFAULT_MIN_SWIPE_COUNT = 1;
    static final int DEFAULT_WAIT = 5;

    /**--------------------
     * Abstract Methods
     --------------------*/

    /**
     * Swipe gesture by mobile command execution
     * Advance usage: swipe(WebElementOption.element(startWebElement).adjustByMultiplyBaseSize(0.5f, 0.5f),
     * WebElementOption.element(endWebElement).adjustByMultiplyBaseSize(0.5f, 0.5f));
     *
     * @param start    PointOption
     * @param end      PointOption
     * @param duration int: in seconds
     */
    public void swipe(PointOption start, PointOption end, int duration) {
        LOGGER.info("Swipe gesture by point");
        int startX = (int) start.build().get("x");
        int startY = (int) start.build().get("y");
        int endX = (int) end.build().get("x");
        int endY = (int) end.build().get("y");
        swipe(startX, startY, endX, endY, duration * 1000);
    }

    /**
     * swipe till element using TouchActions
     *
     * @param element WebElement
     * @return boolean
     */
    public boolean swipe(final WebElement element) {
        return swipe(element, null, Direction.UP, DEFAULT_MAX_SWIPE_COUNT, DEFAULT_TOUCH_ACTION_DURATION);
    }

    /**
     * swipe till element using TouchActions
     *
     * @param element WebElement
     * @param count   int
     * @return boolean
     */
    public boolean swipe(final WebElement element, int count) {
        return swipe(element, null, Direction.UP, count, DEFAULT_TOUCH_ACTION_DURATION);
    }

    /**
     * swipe till element using TouchActions
     *
     * @param element   WebElement
     * @param direction Direction
     * @return boolean
     */
    public boolean swipe(final WebElement element, Direction direction) {
        return swipe(element, null, direction, DEFAULT_MAX_SWIPE_COUNT, DEFAULT_TOUCH_ACTION_DURATION);
    }

    /**
     * swipe till element using TouchActions
     *
     * @param element  WebElement
     * @param count    int
     * @param duration int
     * @return boolean
     */
    public boolean swipe(final WebElement element, int count, int duration) {
        return swipe(element, null, Direction.UP, count, duration);
    }

    public abstract void pressEnterKeyboard();

    /**
     * Check if keyboard is showing
     * return false if driver is not ios or android driver
     * @return boolean
     *
     * @param element   WebElement
     * @param direction Direction
     * @param count     int
     * @param duration  int
     * /**
     * swipe till element using TouchActions
     */
    public boolean swipe(final WebElement element, Direction direction, int count, int duration) {
        return swipe(element, null, direction, count, duration);
    }

    /**
     * Swipe to element inside container in specified direction while element
     * will not be present on the screen. If element is on the screen already,
     * scrolling will not be performed.
     *
     * @param element   element to which it will be scrolled
     * @param container element, inside which scrolling is expected. null to scroll
     * @param direction direction of scrolling. HORIZONTAL and VERTICAL support swiping in both directions automatically
     * @param count     for how long to scroll, ms
     * @param duration  pulling timeout, ms
     * @return boolean
     */
    public boolean swipe(WebElement element, WebElement container, Direction direction,
                         int count, int duration) {

        boolean isVisible = isElementVisible(element, 1);
        if (element.isDisplayed()) {
            LOGGER.info("Element already present before swipe");
            return true;
        } else {
            LOGGER.info("Swiping to element");
        }

        Direction oppositeDirection = Direction.DOWN;
        boolean bothDirections = false;

        switch (direction) {
            case UP:
                oppositeDirection = Direction.DOWN;
                break;
            case DOWN:
                oppositeDirection = Direction.UP;
                break;
            case LEFT:
                oppositeDirection = Direction.RIGHT;
                break;
            case RIGHT:
                oppositeDirection = Direction.LEFT;
                break;
            case HORIZONTAL:
                direction = Direction.LEFT;
                oppositeDirection = Direction.RIGHT;
                bothDirections = true;
                break;
            case HORIZONTAL_RIGHT_FIRST:
                direction = Direction.RIGHT;
                oppositeDirection = Direction.LEFT;
                bothDirections = true;
                break;
            case VERTICAL:
                direction = Direction.UP;
                oppositeDirection = Direction.DOWN;
                bothDirections = true;
                break;
            case VERTICAL_DOWN_FIRST:
                direction = Direction.DOWN;
                oppositeDirection = Direction.UP;
                bothDirections = true;
                break;
            default:
                throw new RuntimeException("Unsupported direction for swipeInContainerTillElement: " + direction);
        }

        int currentCount = count;

        while (!isVisible && currentCount-- > 0) {
            LOGGER.debug("Element not present! Swipe " + direction + " will be executed to element");
            swipeInContainer(container, direction, duration);

            LOGGER.info("Swipe was executed. Attempts remain: " + currentCount);
            isVisible = isElementVisible(element, 1);
        }

        currentCount = count;
        while (bothDirections && !isVisible && currentCount-- > 0) {
            LOGGER.debug(
                    "Element not present! Swipe " + oppositeDirection + " will be executed to element");
            swipeInContainer(container, oppositeDirection, duration);
            LOGGER.info("Swipe was executed. Attempts remain: " + currentCount);
            isVisible = isElementVisible(element, 1);
        }

        LOGGER.info("Result: " + isVisible);
        return isVisible;
    }

    /**
     * Swipe by coordinates using TouchAction (platform independent)
     *
     * @param startX   int
     * @param startY   int
     * @param endX     int
     * @param endY     int
     * @param duration int Millis
     */
    public void swipe(int startX, int startY, int endX, int endY, int duration) {
        LOGGER.debug("Starting swipe...");
        WebDriver driver = castDriver();

        LOGGER.debug("Getting driver dimension size...");
        Dimension scrSize = driver.manage().window().getSize();
        LOGGER.debug("Finished driver dimension size...");
        // explicitly limit range of coordinates
        if (endX >= scrSize.width) {
            LOGGER.warn("endx coordinate is bigger then device width! It will be limited!");
            endX = scrSize.width - 1;
        } else {
            endX = Math.max(1, endX);
        }

        if (endY >= scrSize.height) {
            LOGGER.warn("endy coordinate is bigger then device height! It will be limited!");
            endY = scrSize.height - 1;
        } else {
            endY = Math.max(1, endY);
        }

        LOGGER.debug("startx: " + startX + "; starty: " + startY + "; endx: " + endX + "; endy: " + endY
                + "; duration: " + duration);

        // Specify PointerInput as [TOUCH] with name [finger1]
        PointerInput pointerInput = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        // Specify sequence
        Sequence sequence = new Sequence(pointerInput, 1)
                .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(pointerInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), endX, endY))
                .addAction(pointerInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Ask appium server to perform the sequence
        appiumDriver.perform(Collections.singletonList(sequence));
        LOGGER.debug("Finished swipe...");
    }

    /**
     * swipeInContainer
     *
     * @param container ExtendedWebElement
     * @param direction Direction
     * @param duration  int
     * @return boolean
     */
    public boolean swipeInContainer(WebElement container, Direction direction, int duration) {
        return swipeInContainer(container, direction, DEFAULT_MIN_SWIPE_COUNT, duration);
    }

    /**
     * swipeInContainer
     *
     * @param container ExtendedWebElement
     * @param direction Direction
     * @param count     int
     * @param duration  int
     * @return boolean
     */
    public boolean swipeInContainer(WebElement container, Direction direction, int count, int duration) {
        int startx = 0;
        int starty = 0;
        int endx = 0;
        int endy = 0;

        Point elementLocation = null;
        Dimension elementDimensions = null;

        if (container == null) {
            // whole screen/driver is a container!
            WebDriver driver = castDriver();
            elementLocation = new Point(0, 0); // initial left corner for that case

            elementDimensions = driver.manage().window().getSize();
        } else {
            if (isElementVisible(container, 5)) {
                LOGGER.info("Cannot swipe! Element container already present");
            }
            elementLocation = container.getLocation();
            elementDimensions = container.getSize();
        }

        double minCoefficient = 0.25;
        double maxCoefficient = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID) ? 0.5 : 0.8;

        switch (direction) {
            case LEFT:
                starty = endy = elementLocation.getY() + Math.round(elementDimensions.getHeight() / 2f);

                startx = (int) (elementLocation.getX() + Math.round(maxCoefficient * elementDimensions.getWidth()));
                endx = (int) (elementLocation.getX() + Math.round(minCoefficient * elementDimensions.getWidth()));
                break;
            case RIGHT:
                starty = endy = elementLocation.getY() + Math.round(elementDimensions.getHeight() / 2f);

                startx = (int) (elementLocation.getX() + Math.round(minCoefficient * elementDimensions.getWidth()));
                endx = (int) (elementLocation.getX() + Math.round(maxCoefficient * elementDimensions.getWidth()));
                break;
            case UP:
                startx = endx = elementLocation.getX() + Math.round(elementDimensions.getWidth() / 2f);

                starty = (int) (elementLocation.getY() + Math.round(maxCoefficient * elementDimensions.getHeight()));
                endy = (int) (elementLocation.getY() + Math.round(minCoefficient * elementDimensions.getHeight()));
                break;
            case DOWN:
                startx = endx = elementLocation.getX() + Math.round(elementDimensions.getWidth() / 2f);

                starty = (int) (elementLocation.getY() + Math.round(minCoefficient * elementDimensions.getHeight()));
                endy = (int) (elementLocation.getY() + Math.round(maxCoefficient * elementDimensions.getHeight()));
                break;
            default:
                throw new RuntimeException("Unsupported direction: " + direction);
        }

        LOGGER.debug(String.format("Swipe from (X = %d; Y = %d) to (X = %d; Y = %d)", startx, starty, endx, endy));

        try {
            for (int i = 0; i < count; ++i) {
                swipe(startx, starty, endx, endy, duration);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(String.format("Error during Swipe from (X = %d; Y = %d) to (X = %d; Y = %d): %s", startx, starty, endx, endy, e));
        }
        return false;
    }

    /**
     * Swipe up
     *
     * @param duration int
     */
    public void swipeUp(final int duration) {
        LOGGER.info("Swipe up will be executed.");
        swipeInContainer(null, Direction.UP, duration);
    }

    /**
     * Swipe up several times
     *
     * @param times    int
     * @param duration int
     */
    public void swipeUp(final int times, final int duration) {
        for (int i = 0; i < times; i++) {
            swipeUp(duration);
        }
    }

    /**
     * Swipe down
     *
     * @param duration int
     */
    public void swipeDown(final int duration) {
        LOGGER.info("Swipe down will be executed.");
        swipeInContainer(null, Direction.DOWN, duration);
    }

    /**
     * Swipe down several times
     *
     * @param times    int
     * @param duration int
     */
    public void swipeDown(final int times, final int duration) {
        for (int i = 0; i < times; i++) {
            swipeDown(duration);
        }
    }

    /**
     * Swipe left several times
     *
     * @param times    int
     * @param duration int
     */
    public void swipeLeft(final int times, final int duration) {
        for (int i = 0; i < times; i++) {
            swipeLeft(duration);
        }
    }

    /**
     * Swipe left in container
     *
     * @param container WebElement
     * @param duration  int
     */
    public void swipeLeft(WebElement container, final int duration) {
        LOGGER.info("Swipe left will be executed.");
        swipeInContainer(container, Direction.LEFT, duration);
    }

    /**
     * Swipe left
     *
     * @param duration int
     */
    public void swipeLeft(final int duration) {
        LOGGER.info("Swipe left will be executed.");
        swipeLeft(null, duration);
    }

    /**
     * Swipe right
     *
     * @param duration int
     */
    public void swipeRight(final int duration) {
        LOGGER.info("Swipe right will be executed.");
        swipeRight(null, duration);
    }

    /**
     * Swipe right several times
     *
     * @param times    int
     * @param duration int
     */
    public void swipeRight(final int times, final int duration) {
        for (int i = 0; i < times; i++) {
            swipeRight(duration);
        }
    }

    /**
     * Swipe right in container
     *
     * @param container WebElement
     * @param duration  int
     */
    public void swipeRight(WebElement container, final int duration) {
        LOGGER.info("Swipe right will be executed.");
        swipeInContainer(container, Direction.RIGHT, duration);
    }

    public WebDriver castDriver() {
        WebDriver webDriver = DriverManager.getDriver();
        if (webDriver instanceof EventFiringDecorator) {
            webDriver = (WebDriver) ((EventFiringDecorator<?>) webDriver).getDecoratedDriver();
        }
        return webDriver;
    }

    public boolean isElementVisible(WebElement element, long timeout) {
        boolean res = false;
        try {
            WebDriverWait wait = new WebDriverWait(castDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOf(element));
            res = element.isDisplayed();
        } catch (Exception e) {
            LOGGER.debug("waitUntil: ", e);
        }
        return res;
    }

    public boolean isElementVisible(WebElement element) {
        boolean res = false;
        try {
            WebDriverWait wait = new WebDriverWait(castDriver(), Duration.ofSeconds(DEFAULT_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            res = element.isDisplayed();
        } catch (Exception e) {
            LOGGER.debug("waitUntil: ", e);
        }
        return res;
    }

    public boolean isElementNotVisible(WebElement element, long timeout) {
        boolean res = false;
        try {
            WebDriverWait wait = new WebDriverWait(castDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.invisibilityOf(element));
            res = !element.isDisplayed();
        } catch (Exception e) {
            LOGGER.debug("waitUntil: ", e);
        }
        return res;
    }

    public boolean isElementNotVisible(WebElement element) {
        boolean res = false;
        try {
            WebDriverWait wait = new WebDriverWait(castDriver(), Duration.ofSeconds(DEFAULT_WAIT));
            wait.until(ExpectedConditions.invisibilityOf(element));
            res = !element.isDisplayed();
        } catch (Exception e) {
            LOGGER.debug("waitUntil: ", e);
        }
        return res;
    }
    /**
     * Get middle point of element from pageSource()
     * ===> Using for lazy loading, giant source,...
     * @param xpathExpression Xpath Expression
     */
    public abstract PointOption getMiddlePointOfElementFromDOM(String xpathExpression);

}
