package utils;

import org.testng.ITestContext;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestNgUtils {
    public TestNgUtils() {
    }

    public Map<String, String> getTestContextInJson(ITestContext testContext) {
        int passedTest = testContext.getPassedTests().getAllResults().size();
        int failedTest = testContext.getFailedTests().getAllResults().size();
        int skippedTest = testContext.getSkippedTests().getAllResults().size();
        float totalTest = (float)(passedTest + failedTest + skippedTest);
        LoggerUtils.getInstance().info("Total Passed test: " + passedTest);
        LoggerUtils.getInstance().info("Total Failed test: " + failedTest);
        LoggerUtils.getInstance().info("Total Skipped test: " + skippedTest);
        LoggerUtils.getInstance().info("Total test: " + totalTest);
        new DecimalFormat("#.##");
        String passingRate = totalTest == 0.0F ? "100" : String.format("%.2f", (float)passedTest / totalTest * 100.0F);
        Map<String, String> mapTestContext = new HashMap();
        mapTestContext.put("passed", "" + passedTest);
        mapTestContext.put("failed", "" + failedTest);
        mapTestContext.put("skipped", "" + skippedTest);
        mapTestContext.put("passPercentage", passingRate);
        LoggerUtils.getInstance().info("Passing rate: " + passingRate);
        return mapTestContext;
    }

    public Map<String, String> getTestContextInJson(List<ITestContext> testContexts) {
        Map<String, String> mapTestContext = new HashMap();
        int passedTest = 0;
        int failedTest = 0;
        int skippedTest = 0;

        ITestContext testContext;
        for(Iterator var7 = testContexts.iterator(); var7.hasNext(); skippedTest += testContext.getSkippedTests().getAllResults().size()) {
            testContext = (ITestContext)var7.next();
            passedTest += testContext.getPassedTests().getAllResults().size();
            failedTest += testContext.getFailedTests().getAllResults().size();
        }

        float totalTest = (float)(passedTest + failedTest + skippedTest);
        LoggerUtils.getInstance().info("Total Passed test: " + passedTest);
        LoggerUtils.getInstance().info("Total Failed test: " + failedTest);
        LoggerUtils.getInstance().info("Total Skipped test: " + skippedTest);
        LoggerUtils.getInstance().info("Total test: " + totalTest);
        new DecimalFormat("#.##");
        String passingRate = totalTest == 0.0F ? "100" : String.format("%.2f", (float)passedTest / totalTest * 100.0F);
        mapTestContext.put("passed", "" + passedTest);
        mapTestContext.put("failed", "" + failedTest);
        mapTestContext.put("skipped", "" + skippedTest);
        mapTestContext.put("passPercentage", passingRate);
        LoggerUtils.getInstance().info("Passing rate: " + passingRate);
        return mapTestContext;
    }
}
