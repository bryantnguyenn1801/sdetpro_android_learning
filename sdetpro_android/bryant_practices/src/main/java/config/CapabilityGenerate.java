package config;

import core.android.AndroidCapabilityType;
import core.ios.IOSCapabilityType;
import io.appium.java_client.remote.options.BaseOptions;
import models.devices.Devices;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.SocketUtils;

public class CapabilityGenerate {
    private static final int MIN = 9001;
    private static final int MAX = 11000;

    public static DesiredCapabilities withProperties(Devices devicesManager) {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(AndroidCapabilityType.PLATFORM_NAME, devicesManager.getPLATFORM());
        cap.setCapability(BaseOptions.PLATFORM_VERSION_OPTION,
                devicesManager.getPLATFORM_VERSION());
        cap.setCapability(AndroidCapabilityType.DEVICE_NAME, devicesManager.getDEVICE_NAME());
        cap.setCapability(BaseOptions.AUTOMATION_NAME_OPTION, devicesManager.getAUTOMATION_NAME());
        cap.setCapability(BaseOptions.NEW_COMMAND_TIMEOUT_OPTION, 60 * 7);
        cap.setCapability(BaseOptions.PRINT_PAGE_SOURCE_ON_FIND_FAILURE_OPTION, true);
        if (devicesManager.getPLATFORM().toLowerCase().contains("android")) {
            if (!devicesManager.isIS_REAL()) {
                cap.setCapability(AndroidCapabilityType.AVD_OPTION, devicesManager.getAVD());
                cap.setCapability(AndroidCapabilityType.NO_SIGN_OPTION, true);
            } else {
                cap.setCapability(AndroidCapabilityType.UDID_OPTION, devicesManager.getUDID());
            }
            cap.setCapability(AndroidCapabilityType.AUTO_GRANT_PERMISSIONS_OPTION, true);
            cap.setCapability(AndroidCapabilityType.SYSTEM_PORT_OPTION, devicesManager.getSYSTEM_PORT());
            cap.setCapability(AndroidCapabilityType.APP_PACKAGE_OPTION, devicesManager.getAPP_PACKAGE());
            cap.setCapability(AndroidCapabilityType.APP_WAIT_ACTIVITY_OPTION, devicesManager.getAPP_WAIT_ACTIVITY());
            cap.setCapability(AndroidCapabilityType.APP_ACTIVITY_OPTION, devicesManager.getAPP_ACTIVITY());
            cap.setCapability(AndroidCapabilityType.APP_WAIT_DURATION_OPTION, 60000);
            cap.setCapability(AndroidCapabilityType.NO_RESET_OPTION, "false");
            cap.setCapability(AndroidCapabilityType.AUTO_ACCEPT_ALERTS, true);
            cap.setCapability(AndroidCapabilityType.WAIT_FOR_IDLE_TIMEOUT_SETTINGS, 100);
        } else if (devicesManager.getPLATFORM().toLowerCase().contains("ios")) {
            cap.setCapability(IOSCapabilityType.CONNECT_HARDWARE_KEYBOARD_OPTION, false);
            cap.setCapability(IOSCapabilityType.AUTO_ACCEPT_ALERTS, false);
            cap.setCapability(IOSCapabilityType.MAX_TYPING_FREQUENCY_OPTION, "60");
            cap.setCapability(IOSCapabilityType.USE_JSON_SOURCE_OPTION, true);
            cap.setCapability(IOSCapabilityType.WDA_LOCAL_PORT_OPTION, SocketUtils.findAvailableTcpPort(MIN, MAX));
            cap.setCapability(IOSCapabilityType.NO_RESET_OPTION, false);
            cap.setCapability(IOSCapabilityType.SHOULD_USE_SINGLETON_TEST_MANAGER_OPTION, false);
            cap.setCapability(IOSCapabilityType.WDA_STARTUP_RETRIES_OPTION, 20);
            cap.setCapability(IOSCapabilityType.WDA_STARTUP_RETRY_INTERVAL_OPTION, 12000);
            cap.setCapability(IOSCapabilityType.USE_PREBUILT_WDA_OPTION, true);
            cap.setCapability(IOSCapabilityType.BUNDLE_ID_OPTION, devicesManager.getBUNDLE_ID());

            if (devicesManager.isIS_REAL()) {
                cap.setCapability(IOSCapabilityType.UPDATED_WDA_BUNDLE_ID_OPTION, devicesManager.getUPDATED_WDA_BUNDLE_ID());
                cap.setCapability(IOSCapabilityType.UDID_OPTION, devicesManager.getUDID());
            }
        }
        return cap;
    }
}
