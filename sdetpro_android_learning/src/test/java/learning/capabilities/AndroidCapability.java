package learning.capabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public class AndroidCapability {
    public static DesiredCapabilities setCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME_OPTION, "uiautomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID_OPTION, "emulator-5554");
        desiredCapabilities.setCapability(MobileCapabilityType.APP_PACKAGE_OPTION, "com.wdiodemoapp");
        desiredCapabilities.setCapability(MobileCapabilityType.APP_ACTIVITY_OPTION,
                "com.wdiodemoapp.MainActivity");
        return desiredCapabilities;
    }

}
