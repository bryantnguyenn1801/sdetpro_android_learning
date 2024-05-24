package config;

import java.lang.reflect.InvocationTargetException;

public class PageFactoryManager {
    private PageFactoryManager() {
    }

    public static <TPage> TPage get(Class<TPage> pageClass) {
        TPage page = null;
        try {
            page = pageClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return page;
    }
}
