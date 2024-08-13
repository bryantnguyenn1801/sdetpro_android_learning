package utils.listener;

import com.epam.reportportal.testng.BaseTestNGListener;
import com.epam.reportportal.testng.ITestNGService;
import com.epam.reportportal.testng.TestNGService;
import com.epam.reportportal.utils.MemoizingSupplier;
import org.testng.*;
import utils.*;

import java.util.*;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class PracticeBaseTestNGListener extends BaseTestNGListener {

    /* static instance with lazy init */
    public static final Supplier<ITestNGService> SERVICE = new MemoizingSupplier<>(TestNGServiceCustomize::new);

    public PracticeBaseTestNGListener() {
        super(SERVICE.get());
    }

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        LogHelper.getInstance().info("Test started: " + result.getName());
        LoggerUtils.getInstance().info("Test class started: " + result.getTestClass().getName());
        LoggerUtils.getInstance().info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogHelper.getInstance().info("Test stop with SUCCESS: " + result.getName());
        LoggerUtils.getInstance().info("Test stop with SUCCESS: " + result.getName());
        super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogHelper.getInstance().info("Test stop with FAILED: " + result.getName());
        LoggerUtils.getInstance().info("Test stop with FAILED: " + result.getName());
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
        super.onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        Map<String, String> mapContext = new TestNgUtils().getTestContextInJson(testContext);
        String currentDir = System.getProperty("user.dir");
        try {
            mapContext.put("reportPortal", TestNGService.ITEM_TREE.getLaunchId().blockingGet().trim());
        }catch (NullPointerException npe){
            mapContext.put("reportPortal", "");
        }
        String finalResult = JsonUtils.parseMapToJsonString(mapContext);
        new FileUtils().writeFile("testResult.json", finalResult , currentDir);
        LoggerUtils.getInstance().info("----------------Test Finished----------------");
        super.onFinish(testContext);
    }
    @Override
    public void onFinish(ISuite suite){
        List<ISuiteResult> suiteResults = new ArrayList<>(suite.getResults().values());
        List<ITestContext> testContexts = suiteResults.stream().map(ISuiteResult::getTestContext).collect(toList());

        Map<String, String> mapContext = new TestNgUtils().getTestContextInJson(testContexts);
        String currentDir = System.getProperty("user.dir");
        try {
            mapContext.put("reportPortal", TestNGService.ITEM_TREE.getLaunchId().blockingGet().trim());
        }catch (NullPointerException npe){
            mapContext.put("reportPortal", "");
        }
        String finalResult = JsonUtils.parseMapToJsonString(mapContext);
        new FileUtils().writeFile("testResult.json", finalResult , currentDir);
        LoggerUtils.getInstance().info("----------------Test Suite Finished----------------");
        super.onFinish(suite);
    }
}
