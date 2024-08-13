package config;

import config.enums.MobilePlatform;

public class ConfigManager {
    public ConfigManager() {
    }

    public static MobilePlatform getMobilePlatform() {
        String platform = System.getProperty("platformName");
        if (platform == null) {
            platform = "iOS";
        }

        return platform.equalsIgnoreCase("ios") ? MobilePlatform.IOS : MobilePlatform.ANDROID;
    }

    public static String getDeviceName() {
        String deviceName = System.getProperty("deviceName");
        if (deviceName == null) {
            throw new IllegalArgumentException("Property [deviceName] is null, please set the correct property value");
        } else {
            return deviceName;
        }
    }
}
