package config;

import config.enums.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class DriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();

    public DriverManager() {
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    public static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    public static void closeAllNodeServer() {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String cmd = "";
            if (osName.toLowerCase().contains("windows")) {
                cmd = "taskkill /f /im node.exe";
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            }
        } catch (Exception var3) {
            Exception e = var3;
            System.out.println(e.getMessage());
        }

    }

    public static void initialiseDriver(URL serviceUrl,
                                        DesiredCapabilities capabilities) {
        try {
            AppiumDriver currentDriverSession = null;
            MobilePlatform platform = ConfigManager.getMobilePlatform();
            if (platform.equals(MobilePlatform.ANDROID)) {
                currentDriverSession = setUpAndroidDriver(serviceUrl, capabilities);

            } else if (platform.equals(MobilePlatform.IOS)) {
                currentDriverSession = setUpiOSDriver(serviceUrl, capabilities);
            }
            setDriver(currentDriverSession);
        } catch (SessionNotCreatedException e) {
            System.out.println("---------Failed to start driver---------");
            throw e;
        }
    }

    public static void initialiseAndroidDriver(URL serviceUrl,
                                               DesiredCapabilities capabilities) {
        try {
            setDriver(setUpAndroidDriver(serviceUrl, capabilities));
        } catch (SessionNotCreatedException e) {
            System.out.println("---------Failed to start Android Driver---------");
            throw e;
        }
    }

    private static AppiumDriver setUpAndroidDriver(URL url, DesiredCapabilities capabilities) {
        System.out.println(" DesiredCapabilities: \n" + capabilities.toString());
        AppiumDriver currentDriverSession = null;
        currentDriverSession = new AndroidDriver(url, capabilities);
        return currentDriverSession;
    }

    private static AppiumDriver setUpiOSDriver(URL url, DesiredCapabilities capabilities) {
        System.out.println(" DesiredCapabilities: \n" + capabilities.toString());
        AppiumDriver currentDriverSession = null;
        currentDriverSession = new IOSDriver(url, capabilities);
        return currentDriverSession;
    }

    public static void launchApp() {
        getDriver().manage();
    }

    public static void closeApp() {
        getDriver().close();
    }

    public static void resetApp() {
        getDriver().resetCooldown();
    }

    public static void quit() {
        getDriver().quit();
    }

}