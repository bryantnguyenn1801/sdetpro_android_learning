package api_learning;

import driver.DriverFactory;
import driver.Platform;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwipeHorizontally {

    public static void main(String[] args) {

        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        try {
            By formsBtnLoc = AppiumBy.accessibilityId("Swipe");
            // Navigate to [Swipe] screen
            appiumDriver.findElement(formsBtnLoc).click();

            // Make sure we are on the target screen
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15L));
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector(). textContains(\"Swipe horizontal\")")));

            // Get dimension data
            Dimension windowSize = appiumDriver.manage().window().getSize();
            int screenHeight = windowSize.getHeight();
            int screenWidth = windowSize.getWidth();

            // Construct coordinators
            int startX = 70 * screenWidth / 100;
            int startY = 70 * screenHeight / 100;
            int endX = 30 * screenHeight / 100;
            int endY = startY;

            // Specify PointerInput as [TOUCH] with name [finger1]
            PointerInput pointerInput = new PointerInput(Kind.TOUCH, "finger1");

            final int MAX_SWIPE_TIME = 5;
            for (int swipeCounter = 0; swipeCounter < MAX_SWIPE_TIME; swipeCounter++) {
                // Specify sequence
                Sequence sequence = new Sequence(pointerInput, 1)
                        .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                        .addAction(pointerInput.createPointerDown(MouseButton.LEFT.asArg()))
                        .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                        .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), endX, endY))
                        .addAction(pointerInput.createPointerUp(MouseButton.LEFT.asArg()));

                // Ask appium server to perform the sequence
                appiumDriver.perform(Collections.singletonList(sequence));

                // Wait on purpose
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DEBUG PURPOSE ONLY
        try {
            Thread.sleep(1000);
        } catch (Exception ignored) {
        }

        appiumDriver.quit();
    }
}