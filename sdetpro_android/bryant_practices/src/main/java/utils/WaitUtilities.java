package utils;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import config.ConfigManager;
import config.DriverManager;
import config.enums.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtilities{

    public static WebElement waitForElement(WebElement el){
        try{
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30)).ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.visibilityOf(el));
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return el;
    }

    public static List<WebElement> waitForNonZeroListOfElements(List<WebElement> els){
        try{
            (new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15)))
                    .until(new ExpectedCondition<Boolean>(){
                        public Boolean apply(WebDriver driver){
                            return els.size() > 0;
                        }
                    });
        } catch (TimeoutException toe){
            throw new AssertionError("Transactions count is equal to zero!");
        }
        return els;
    }

    public static List<WebElement> waitForSizeOfElementsToBe(List<WebElement> els, int expected){
        try{
            (new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15)))
                    .until(new ExpectedCondition<Boolean>(){
                        public Boolean apply(WebDriver driver){
                            return els.size() == expected;
                        }
                    });
        } catch (TimeoutException toe){
            throw new AssertionError("Transactions count is equal to zero!");
        }
        return els;
    }

    public static WebElement waitForElementToBeEnabled(WebElement el){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.attributeToBe(el, "enabled", "true"));
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return el;
    }

    public static void waitForElementToDisAppear(By locator){
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    public static WebElement waitForElementToAppear(By by){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(45));
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
    }

    public static boolean waitForTextPresentInElementLocatedBy(By locator, String text){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(45));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (NoSuchElementException nse){
            return false;
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return true;
    }

    public static boolean waitForTextPresentInElement(WebElement el, String text){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(60));
            wait.until(ExpectedConditions.textToBePresentInElement(el, text));
        } catch (NoSuchElementException nse){
            return false;
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return true;
    }

    public static void waitUntilElementToDisAppearLocatedBy(By by, Integer timeoutSec,
                                                            Integer pollinSec){

        FluentWait wait = new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(timeoutSec))
                .pollingEvery(Duration.ofMillis(pollinSec))
                .ignoring(StaleElementReferenceException.class);

        try{
            wait.until(new Function<WebDriver, Boolean>(){
                public Boolean apply(WebDriver driver){
                    try{
                        return !driver.findElement(by).isEnabled();
                    } catch (NoSuchElementException nse){
                        return true;
                    }
                }
            });
        } catch (TimeoutException toe){
            throw new AssertionError(
                    String.format("Element located by [%s] did not disappear after %s sec.\n %s",
                            by.toString(), timeoutSec, toe.getMessage()));
        }
    }

    public void waitUntilTextAppearsInElementLocatedBy(By by, String text){

        int timeout = 30;
        boolean update;

        FluentWait wait = new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(StaleElementReferenceException.class);

        try{
            update = (boolean) wait.until((Function<WebDriver, Boolean>) driver -> {
                String actualTextValue;
                try{
                    if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)){
                        actualTextValue = driver.findElement(by).getText();
                    } else{
                        actualTextValue = driver.findElement(by).getAttribute("label");
                    }
                    return actualTextValue.contains(text);
                } catch (NoSuchElementException nse){
                    return false;
                }
            });
        } catch (TimeoutException toe){
            throw new AssertionError(
                    String.format("Element located by [%s] with text '%s' did not appear after %s sec.\n",
                            by.toString(), text, timeout));
        }
    }

    public static void waitUntilElementAppearLocatedBy(By by, Integer timeoutSec,
                                                       Integer pollInSec){

        FluentWait wait = new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(timeoutSec))
                .pollingEvery(Duration.ofSeconds(pollInSec))
                .ignoring(StaleElementReferenceException.class);

        try{
            wait.until(new Function<WebDriver, Boolean>(){
                public Boolean apply(WebDriver driver){
                    try{
                        return driver.findElement(by).isEnabled();
                    } catch (NoSuchElementException nse){
                        return false;
                    }
                }
            });
        } catch (TimeoutException toe){
            throw new AssertionError(
                    String.format("Element located by [%s] did not appear after %s sec.\n %s",
                            by.toString(), timeoutSec, toe.getMessage()));
        }
    }

    public static WebElement waitForElementIsNotVisible(WebElement el){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOf(el));
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return el;
    }

    /**
     * It sleeps the driver for n seconds.
     *
     * @param millis to be slept.
     */
    public static void wait(int millis){
        long start = new Date().getTime();
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e){
            long end = new Date().getTime();
            do{
                end = new Date().getTime();
            } while ((start + millis) > end);
        }
    }

    public static boolean isElementDisplayed(WebElement element){
        boolean elementDisplayed = false;
        try{
            if (element.isDisplayed()){
                elementDisplayed = true;
            }
        } catch (Exception e){
            return false;
        }
        return elementDisplayed;
    }

    public static WebElement waitForTextNotPresentInElement(WebElement el, String text) {
        (new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return el.isEnabled() && !el.getText().contains(text);
            }
        });
        return el;
    }

    public static WebElement waitForElementCustomTimeout(WebElement el,
                                                            Integer timeoutSeconds){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOf(el));
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return el;
    }
    public static WebElement waitForElementClickable(WebElement el){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(el));
        } catch (TimeoutException toe){
            throw new AssertionError(toe.getMessage());
        }
        return el;
    }
}
