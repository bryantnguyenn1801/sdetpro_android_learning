package models.devices;

import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.Setter;
import java.util.Properties;
import config.DevicesManager;

public class Devices {
    @Getter
    private String PLATFORM;
    @Getter
    private String PLATFORM_VERSION;
    @Getter
    private String DEVICE_NAME;
    @Getter
    private String SYSTEM_PORT;
    @Getter
    private String APP_PATH;
    @Getter
    private String APP_PACKAGE;
    @Getter
    private String APP_ACTIVITY;
    @Getter
    private boolean IS_REAL;
    @Getter
    private String APP_WAIT_ACTIVITY;
    @Getter
    private String BUNDLE_ID;
    @Getter
    private String ORG_ID;
    @Getter
    private String SIGNING_ID;
    @Getter
    @Setter
    private String AUTOMATION_NAME;
    @Getter
    private String UDID;
    @Getter
    private String UPDATED_WDA_BUNDLE_ID;
    @Getter
    private boolean IS_ON_REMOTE_MACHINE;

    public Devices getDeviceInformationFrom(String resource) throws IOException {
        InputStream is = DevicesManager.class.getResourceAsStream("/devices/" + resource);
        Properties data = new Properties();
        try{
            data.load(is);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to load properties. Looking for default.properties");
            is = DevicesManager.class.getResourceAsStream("/devices/local_android.properties");
            data.load(is);
        }
        PLATFORM = data.getProperty("platformName");
        PLATFORM_VERSION = data.getProperty("platformVersion");
        DEVICE_NAME = data.getProperty("deviceName");
        SYSTEM_PORT = data.getProperty("systemPort");
        APP_PATH =
                System.getProperty("user.dir") + "/src/test/resources/apps/" + data.getProperty("app");
        APP_PACKAGE = data.getProperty("appPackage");
        APP_ACTIVITY = data.getProperty("appActivity");
        IS_REAL = Boolean.parseBoolean(data.getProperty("isReal"));
        APP_WAIT_ACTIVITY = data.getProperty("appWaitActivity");
        BUNDLE_ID = data.getProperty("bundleId");
        ORG_ID = data.getProperty("orgId");
        SIGNING_ID = data.getProperty("signingId");
        AUTOMATION_NAME = data.getProperty("automationName");
        UDID = data.getProperty("udid");
        UPDATED_WDA_BUNDLE_ID = data.getProperty("updatedWDABundleId");
        IS_ON_REMOTE_MACHINE = Boolean.parseBoolean(data.getProperty("isOnRemoteMachine"));
        return this;
    }
}
