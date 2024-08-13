package utils.listener;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import utils.LogHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static config.DriverManager.*;

public class TestNgMobileCustomListener extends PracticeBaseTestNGListener {
    boolean recordingEnabled = Boolean.parseBoolean(System.getProperty("recordingEnabled"));

    @Override
    public void onTestStart(ITestResult result) {
        if (recordingEnabled) {
            startRecording();
        }
        super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        try {
            if (recordingEnabled) {
                stopRecording();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            LogHelper.getInstance().log(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES), "Failed test image");
            LogHelper.getInstance().log(getDriver().getPageSource().getBytes(StandardCharsets.UTF_8), "page source");
        } catch (Exception e) {
            LogHelper.getInstance().info(e.getMessage());
        }
        try {
            if (recordingEnabled) {
                writeToMp4(stopRecording(), result.getMethod().getMethodName());
            }
        } catch (InvalidElementStateException | IOException e) {
            e.printStackTrace();
        }
        super.onTestFailure(result);
    }
}
