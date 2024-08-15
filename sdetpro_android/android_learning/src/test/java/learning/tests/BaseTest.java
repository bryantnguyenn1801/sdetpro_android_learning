package learning.tests;

import learning.driver.DriverFactory;
import learning.capabilities.Platform;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    private static final List<DriverFactory> driverThreadPool = Collections.synchronizedList(
            new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;
    private String udid;
    private String systemPort;
    private String platformName;
    private String platformVersion;

    protected AppiumDriver getDriver() {
        return driverThread.get()
                .getDriver(Platform.valueOf(platformName), systemPort, udid, platformVersion);
    }

    @BeforeTest
    public void initAppiumDriverSession() {
        driverThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverThread = new DriverFactory();
            driverThreadPool.add(driverThread);
            return driverThread;
        });
    }

    @BeforeClass
    @Parameters({"systemPort", "udid", "platformName", "platformVersion"})
    public void getTestParams(String systemPort, String udid, String platformName,
                              @Optional("platformVersion") String platformVersion) {
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.systemPort = systemPort;
        this.udid = udid;
    }

    @AfterTest(alwaysRun = true)
    public void quitAppiumSession() {
        driverThread.get().quitAppiumDriver();
    }

    @AfterMethod(description = "Capture screenshot when test is failed")
    public void captureScreenshot(ITestResult results) {
        boolean testIsFailed = results.getStatus() == ITestResult.FAILURE;
        if (testIsFailed) {
            String randomScreenshotName = generateScreenshotNameBasedOnTime(results);
            // Capture screenshot and attach to the report
            File screenshotBase64Data = getDriver().getScreenshotAs(OutputType.FILE);
            try {
                String screenshotLocation =
                        System.getProperty("user.dir") + "/screenshots/" + randomScreenshotName;
                FileUtils.copyFile(screenshotBase64Data,
                        new File(screenshotLocation));
                Path screenshotContentPath = Paths.get(screenshotLocation);
                InputStream inputStream = Files.newInputStream(screenshotContentPath);
                Allure.addAttachment(results.getName(), inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String generateScreenshotNameBasedOnTime(ITestResult results) {
        // 1. GEt the test method name
        String methodName = results.getName();

        // 2. Get current time | yyyy-mm-dd-hr-m-s
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        String takenTime = year + "-" + month + "-" + day + "-" + hr + "-" + min + "-" + sec;
        return methodName + "-" + takenTime + ".png";
    }

}