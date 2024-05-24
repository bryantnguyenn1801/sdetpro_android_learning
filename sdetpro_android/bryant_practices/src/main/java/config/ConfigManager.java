package config;

import config.enums.MobilePlatform;

public class ConfigManager {
    public ConfigManager() {
    }

    public static MobilePlatform getMobilePlatform() {
        String platform = System.getProperty("setelUI.platformName");
        if (platform == null) {
            platform = "iOS";
        }

        return platform.equalsIgnoreCase("ios") ? MobilePlatform.IOS : MobilePlatform.ANDROID;
    }

    public static String getDeviceName() {
        String deviceName = System.getProperty("setelUI.deviceName");
        if (deviceName == null) {
            throw new IllegalArgumentException("Property [setelUI.deviceName] is null, please set the correct property value");
        } else {
            return deviceName;
        }
    }
}
