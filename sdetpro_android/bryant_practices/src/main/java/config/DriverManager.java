package config;

import config.enums.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import static org.openqa.selenium.net.PortProber.findFreePort;

public class DriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();
    private static ThreadLocal<byte[]> videoBase64LocalSession = new ThreadLocal();
    public static boolean recordingEnabled = Boolean.parseBoolean(System.getProperty("recordingEnabled"));

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
            int systemPort = findFreePort();
            capabilities.setCapability("systemPort", systemPort);
            if (platform.equals(MobilePlatform.ANDROID)) {
                currentDriverSession = setUpAndroidDriver(serviceUrl, capabilities);
                System.out.println(currentDriverSession);
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
        System.out.println("DesiredCapabilities: \n" + capabilities.toString());
        AppiumDriver currentDriverSession;
        currentDriverSession = new AndroidDriver(url, capabilities);
        return currentDriverSession;
    }

    private static AppiumDriver setUpiOSDriver(URL url, DesiredCapabilities capabilities) {
        System.out.println(" DesiredCapabilities: \n" + capabilities.toString());
        AppiumDriver currentDriverSession;
        currentDriverSession = new IOSDriver(url, capabilities);
        return currentDriverSession;
    }
    public static void startRecording() {
        if (recordingEnabled) {
            boolean isAndroid = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID);
            if (isAndroid) {
                ((AndroidDriver)getDriver()).startRecordingScreen();
            } else {
                ((IOSDriver)getDriver()).startRecordingScreen((new IOSStartScreenRecordingOptions()).withVideoType("mpeg4"));
            }
        }

    }

    public static byte[] stopRecording() throws IOException {
        if (recordingEnabled) {
            boolean isAndroid = ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID);
            if (isAndroid) {
                videoBase64LocalSession.set(Base64.getDecoder().decode(((AndroidDriver)getDriver()).stopRecordingScreen().getBytes()));
            } else {
                videoBase64LocalSession.set(Base64.getDecoder().decode(((IOSDriver)getDriver()).stopRecordingScreen().getBytes()));
            }
        }

        return (byte[])videoBase64LocalSession.get();
    }

    public static void writeToMp4(byte[] videoBase64, String fileName) throws IOException {
        if (recordingEnabled) {
            File directory = new File(System.getProperty("user.dir") + "/videos/");
            if (!directory.exists()) {
                directory.mkdir();
            }

            String var10002 = System.getProperty("user.dir");
            FileOutputStream out = new FileOutputStream(var10002 + "/videos/" + fileName + ".mp4");
            out.write(videoBase64);
            out.close();
        }

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