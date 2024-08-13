package learning.tests;

import com.google.common.reflect.ClassPath;
import learning.driver.IOSCapabilityType;
import learning.driver.AndroidCapabilityType;
import learning.capabilities.Platform;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class Main {

    public static void main(String[] args) throws IOException {
        // Get all test classes
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> testClasses = new ArrayList<>();
        for (ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            String classInfoName = info.getName();
            boolean startWithTestDot = classInfoName.startsWith("tests.");
            boolean isBaseTestClass = classInfoName.startsWith("tests.BaseTest");
            boolean isMainClass = classInfoName.startsWith("tests.Main");
            if (startWithTestDot && !isBaseTestClass && !isMainClass) {
                testClasses.add(info.load());
            }
        }

        // Get platform
//    String platformName = System.getProperty("platform");
        String platformName = "ANDROID";
        if (platformName == null) {
            throw new IllegalArgumentException("[ERR] Please provide platform via -Dplatform");
        }
        try {
            Platform.valueOf(platformName);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[ERR] We don't support platform " + platformName + ", supported platforms: "
                            + Arrays.toString(Platform.values()));
        }

        // Devices under test
        List<String> iPhoneDeviceList = Arrays.asList("iPhone 15 Pro Max", "iPhone 15 Pro");
        List<String> androidDeviceList = Arrays.asList("emulator-5554", "G001L90613160GKL");
        List<String> deviceList =
                platformName.equalsIgnoreCase("ios") ? iPhoneDeviceList : androidDeviceList;

        // Assign devices to test classes
        int testNumEachDevice = testClasses.size() / deviceList.size();
        Map<String, List<Class<?>>> desiredCaps = new HashMap<>();
        for (int deviceIndex = 0; deviceIndex < deviceList.size(); deviceIndex++) {
            int startIndex = deviceIndex * testNumEachDevice;
            boolean isTheLastDevice = deviceIndex == deviceList.size() - 1;
            int endIndex = isTheLastDevice ? testClasses.size() : (startIndex + testNumEachDevice);
            List<Class<?>> subTestList = testClasses.subList(startIndex, endIndex);
            desiredCaps.put(deviceList.get(deviceIndex), subTestList);
        }

        // Build dynamic test suite
        TestNG testNG = new TestNG();
        XmlSuite suite = new XmlSuite();
        suite.setName("Regression");

        List<XmlTest> allTests = new ArrayList<>();
        for (String deviceName : desiredCaps.keySet()) {
            XmlTest test = new XmlTest(suite);
            test.setName(deviceName);
            List<XmlClass> xmlClasses = new ArrayList<>();
            List<Class<?>> testClassesForDevice = desiredCaps.get(deviceName);
            for (Class<?> testClass : testClassesForDevice) {
                xmlClasses.add(new XmlClass(testClass.getName()));
            }
            test.setXmlClasses(xmlClasses);
            test.addParameter(AndroidCapabilityType.UDID_OPTION, deviceName);
            test.addParameter(AndroidCapabilityType.PLATFORM_NAME, platformName);
            test.addParameter(AndroidCapabilityType.PLATFORM_VERSION_OPTION, "17.2");
            test.addParameter(AndroidCapabilityType.SYSTEM_PORT,
                    String.valueOf(new SecureRandom().nextInt(1000) + 8300));
            allTests.add(test);
        }
        suite.setTests(allTests);
        suite.setParallel(ParallelMode.TESTS);
        suite.setThreadCount(10);
        System.out.println(suite.toXml());

        // Add TestSuite into Suite list
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        // Invoke run method
        testNG.setXmlSuites(suites);
        testNG.run();
    }

}
