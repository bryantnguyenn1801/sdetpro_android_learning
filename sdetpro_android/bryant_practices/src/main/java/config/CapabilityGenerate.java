package config;

import core.MobileCapabilityType;
import core.android.AndroidCapabilityType;
import io.appium.java_client.remote.options.BaseOptions;
import models.devices.Devices;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilityGenerate{
    public static DesiredCapabilities withProperties(Devices devicesManager){
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, devicesManager.getPLATFORM());
        cap.setCapability(BaseOptions.PLATFORM_VERSION_OPTION,
                devicesManager.getPLATFORM_VERSION());
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, devicesManager.getDEVICE_NAME());
        cap.setCapability(BaseOptions.AUTOMATION_NAME_OPTION, devicesManager.getAUTOMATION_NAME());
        cap.setCapability(BaseOptions.NEW_COMMAND_TIMEOUT_OPTION, 60 * 7);
        cap.setCapability(BaseOptions.PRINT_PAGE_SOURCE_ON_FIND_FAILURE_OPTION, true);
        if (devicesManager.getPLATFORM().toLowerCase().contains("android")){
            if (!devicesManager.isIS_REAL()){
                cap.setCapability(AndroidCapabilityType.AVD_OPTION, devicesManager.getDEVICE_NAME());
                cap.setCapability(AndroidCapabilityType.NO_SIGN_OPTION, true);
            }
            else {
                cap.setCapability(AndroidCapabilityType.UDID_OPTION, devicesManager.getDEVICE_NAME());
            }
            cap.setCapability(AndroidCapabilityType.AUTO_GRANT_PERMISSIONS_OPTION, true);
            cap.setCapability(AndroidCapabilityType.SYSTEM_PORT_OPTION, devicesManager.getSYSTEM_PORT());
            cap.setCapability(AndroidCapabilityType.APP_PACKAGE_OPTION, devicesManager.getAPP_PACKAGE());
            cap.setCapability(AndroidCapabilityType.APP_WAIT_ACTIVITY_OPTION, devicesManager.getAPP_WAIT_ACTIVITY());
            cap.setCapability(AndroidCapabilityType.APP_WAIT_DURATION_OPTION, 60000);
            cap.setCapability(AndroidCapabilityType.NO_RESET_OPTION, "false");
            cap.setCapability(AndroidCapabilityType.AUTO_ACCEPT_ALERTS, true);
            cap.setCapability(AndroidCapabilityType.WAIT_FOR_IDLE_TIMEOUT_SETTINGS, 100);
        }
        return cap;
    }
}
