package core.android;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.options.BaseOptions;

public class AndroidCapabilityType extends UiAutomator2Options {
    public static final String PLATFORM_NAME = "platformName";
    public static final String DEVICE_NAME = "deviceName";
    public static final String AUTO_ACCEPT_ALERTS = "autoAcceptAlerts";
    public static final String WAIT_FOR_IDLE_TIMEOUT_SETTINGS = "settings[waitForIdleTimeout]";
}
