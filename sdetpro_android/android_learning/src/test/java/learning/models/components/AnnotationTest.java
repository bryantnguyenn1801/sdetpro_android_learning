package learning.models.components;

import learning.models.components.global.NavComponent;

public class AnnotationTest {
    public <T> void printComponentXpathSelector(Class<T> componentClass) {
        String xpathSelector = componentClass.getAnnotation(ComponentXpathSelector.class).value();
        System.out.println(xpathSelector);
    }

    public static void main(String[] args) {
        new AnnotationTest().printComponentXpathSelector(NavComponent.class);
    }
}
