package models.components;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

//    public <T extends Component> findComponent(Class<T> componentClass) {
//        By componentSel;
//        try {
//            componentSel = getComponentSel(componentClass);
//        } catch (Exception e) {
//            throw new RuntimeException("The component must have xpath selector");
//        }
//    }

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
