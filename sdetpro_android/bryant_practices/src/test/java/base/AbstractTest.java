package base;

import actions.CommonActions;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class AbstractTest {
    private static final String STF_SERVICE_URL = "http://localhost:7100";  // Change this URL
    private static final String ACCESS_TOKEN = "1151e170b6cb4b3e8f30676dd6500b7d8d8cb5ed77c84b34b629c5aa5917078f";  // Change this access token

    protected CommonActions userActions = new CommonActions();
    private static final DevicesManager devicesManager = new DevicesManager();
    private static final DriverManager driverManager = new DriverManager();
    private final String destPath = "/Users/setelqa/apps/";
    ;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"mobilePlatform", "devicePool", "env", "simulator"})
    public void suiteSetUp(@Optional() String mobilePlatform, @Optional() String devicePool, @Optional("staging") String env, @Optional("off") String simulator) throws IOException {
//        ReportPortalManager.setReportPortal(true);
        System.setProperty("platformName", mobilePlatform);
        if (System.getProperty("baseEnv") == null) {
            System.setProperty("baseEnv", env);
        }
        new MobileUserRegistry(System.getProperty("platformName"));
//        new Url(System.getProperty("setelAPI.baseEnv"));
//        boolean isCiEnv = Boolean.parseBoolean(System.getenv("CI"));
        if (ConfigManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            devicesManager.loadDevicePool(devicePool);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() throws IOException {
        DriverManager.closeAllNodeServer();
        ServicesManager.stopAppiumServer();
    }


    @BeforeMethod(alwaysRun = true)
    public void init() throws Exception {
        //read DesiredCapability from device pool
        DesiredCapabilities cap = devicesManager.getDeviceFreeFromPool();
        Assert.assertNotNull(cap, "Failed to find device, cause test to fail");
        String deviceName = cap.getCapability("deviceName").toString();
        String osVersion = cap.getCapability("platformVersion").toString();
        System.out.println("Running on :" + deviceName + osVersion);
        URL appiumServiceUrl;
        //start appium services
        if (DevicesManager.deviceIsRemote.get(deviceName)) {
            ServicesManager.startAppiumServer(deviceName, osVersion);
            appiumServiceUrl = new URL(String.format("http://%s:%s/", ServicesManager.getAppiumPort()));
            cap.setCapability("app", destPath + "Setel-Staging.app");
            System.out.println("------redefine app path for remote machine -----");
            System.out.println("------ " + destPath + " -----");
        } else {
            ServicesManager.startAppiumServer(deviceName, osVersion);
            appiumServiceUrl = ServicesManager.getAppiumService().getUrl();
        }

        System.out.println(appiumServiceUrl.toString());
        //start driver
        DriverManager.initialiseDriver(appiumServiceUrl, cap);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
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
        } else {
            ServicesManager.stopAppiumServer();
        }
    }

    protected boolean isAndroidPlatform() {
        return ConfigManager.getMobilePlatform().equals(MobilePlatform.ANDROID);
    }
}
