package learning.models.components;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Component {
    protected AppiumDriver appiumDriver;
    protected WebElement component;
    protected WebDriverWait wait;

    public Component(AppiumDriver appiumDriver, WebElement component) {
        this.appiumDriver = appiumDriver;
        this.component = component;
        this.wait = new WebDriverWait(this.appiumDriver, Duration.ofSeconds(10L));
    }

    public WebElement getComponent() {
        return component;
    }

    public WebElement findElement(By by) {
        return component.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return component.findElements(by);
    }

    public <T extends Component> T findComponent(Class<T> componentClass) {
        return this.findComponents(componentClass).get(0);
    }

    public <T extends Component> List<T> findComponents(Class<T> componentClass) {
        By componentSel;
        try {
            componentSel = getComponentSel(componentClass);
        } catch (Exception e) {
            throw new RuntimeException("The component must have xpath selector");
        }

        // Wait until the component displayed on the page
        // In case the component  not on screen(for Android) need to swipe the screen
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(componentSel));

        // Find the elements
        List<WebElement> elements = component.findElements(componentSel);

        // Define component class's constructor
        Class<?>[] params = new Class[]{AppiumDriver.class, WebElement.class};
        Constructor<T> constructor;
        try {
            constructor = componentClass.getConstructor(params);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[ERR] The component must have a constructor with params " + Arrays.toString(params));
        }

        // Map the elements to components
        List<T> components = elements.stream().map(webElement -> {
            try {
                return constructor.newInstance(appiumDriver, webElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return components;
    }

    public <T extends Component> By getComponentSel(Class<T> componentClass) {
        if (componentClass.isAnnotationPresent(ComponentXpathSelector.class)) {
            return AppiumBy.xpath(componentClass.getAnnotation(ComponentXpathSelector.class).value());
        } else if (componentClass.isAnnotationPresent(ComponentAccessibilityIdSelector.class)) {
            return AppiumBy.accessibilityId(componentClass.getAnnotation(ComponentAccessibilityIdSelector.class).value());
        } else {
            throw new IllegalArgumentException("Component Class" + componentClass + "must have annotation" + ComponentAccessibilityIdSelector.class.getSimpleName() + "or" + ComponentXpathSelector.class.getSimpleName());
        }

    }
}
