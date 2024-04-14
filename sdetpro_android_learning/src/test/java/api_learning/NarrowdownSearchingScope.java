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

public class NarrowdownSearchingScope {

    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        try {
            By formsBtnLoc = AppiumBy.accessibilityId("Forms");

            // Navigate to [Forms] screen
            appiumDriver.findElement(formsBtnLoc).click();

            // Make sure we are on the target screen before swiping up/down/left/right/any direction
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15L));
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector(). textContains(\"Form components\")")));

            // Swipe up before interacting
            Dimension windowSize = appiumDriver.manage().window().getSize();
            int screenHeight = windowSize.getHeight();
            int screenWidth = windowSize.getWidth();
            System.out.printf("%d x %d", screenWidth, screenHeight);

            // Construct coordinators
            int startX = 50 * screenWidth / 100;
            int startY = 0;
            int endX = startX;
            int endY = 50 * screenHeight / 100;

            // Specify PointerInput as [TOUCH] with name [finger1]
            PointerInput pointerInput = new PointerInput(Kind.TOUCH, "finger1");

            // Specify sequence
            Sequence sequence = new Sequence(pointerInput, 1)
                    .addAction(pointerInput.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                    .addAction(pointerInput.createPointerDown(MouseButton.LEFT.asArg()))
                    .addAction(new Pause(pointerInput, Duration.ofMillis(250)))
                    .addAction(pointerInput.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), endX, endY))
                    .addAction(pointerInput.createPointerUp(MouseButton.LEFT.asArg()));

            // Ask appium server to perform the sequence
            appiumDriver.perform(Collections.singletonList(sequence));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // DEBUG PURPOSE ONLY
        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {
        }

        appiumDriver.quit();
    }
}