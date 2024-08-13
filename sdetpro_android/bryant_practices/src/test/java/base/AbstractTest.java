package base;

import com.google.common.collect.ImmutableMap;
import config.ConfigManager;
import config.DevicesManager;
import config.DriverManager;
import config.ServicesManager;
import config.enums.MobilePlatform;
import data.user.MobileUserRegistry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.AllureUtils;
import utils.listener.TestNgMobileCustomListener;

import java.io.IOException;
import java.net.URL;

@Listeners({TestNgMobileCustomListener.class})
public class AbstractTest {
    private static final DevicesManager devicesManager = new DevicesManager();

    @BeforeSuite(alwaysRun = true)
    @Parameters({"mobilePlatform", "devicePool"})
    public void suiteSetUp(@Optional() String mobilePlatform, @Optional() String devicePool) {
        System.setProperty("platformName", mobilePlatform);
        new MobileUserRegistry(System.getProperty("platformName"));
        devicesManager.loadDevicePool(devicePool);
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() {
        AllureUtils.addAllureProperties();
        DriverManager.closeAllNodeServer();
        ServicesManager.stopAppiumServer();
    }

    @BeforeTest(alwaysRun = true)
    public void init() throws Exception {
        //read DesiredCapability from device pool
        DesiredCapabilities cap = devicesManager.getDeviceFreeFromPool();
        Assert.assertNotNull(cap, "Failed to find device, cause test to fail");
        String deviceName = cap.getCapability("deviceName").toString();
        String osVersion = cap.getCapability("platformVersion").toString();
        System.out.println("Running on :" + deviceName + " " + osVersion);
        URL appiumServiceUrl;
        //start appium services
        ServicesManager.startAppiumServer(deviceName, osVersion);
        appiumServiceUrl = new URL("http://localhost:" + ServicesManager.getAppiumService().getUrl().getPort());
        //start driver
        DriverManager.initialiseDriver(appiumServiceUrl, cap);
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        devicesManager.releaseDevice();
        if (DriverManager.getDriver() != null) {
            if (ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
                DriverManager.getDriver().executeScript("mobile: performEditorAction", ImmutableMap.of("action", "hidekeyboard"));
            }
            DriverManager.getDriver().quit();
        }
        if (ServicesManager.getAppiumService() != null) {
            ServicesManager.stopAppiumServer();
            System.out.println("Appium stopped");
        }
    }

    protected boolean isAndroidPlatform() {
        return ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID);
    }
}
