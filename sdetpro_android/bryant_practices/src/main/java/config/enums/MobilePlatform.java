package config.enums;

import config.ConfigManager;

public enum MobilePlatform {
    IOS("iOS"),
    ANDROID("android");

    public final String platformName;

    MobilePlatform(String platformName) {
        this.platformName = platformName;
    }

    public static boolean isAndroid() {
        return ConfigManager.getMobilePlatform().equals(ANDROID);
    }

    public static boolean isIOS() {
        return ConfigManager.getMobilePlatform().equals(IOS);
    }
}
