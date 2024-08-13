package utils;

import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoggerUtils {
    public List<String> utilList = Collections.synchronizedList(new ArrayList<String>());
    public static ThreadLocal<LoggerUtils> loggerUtilsThreadLocal = new ThreadLocal<LoggerUtils>();

    public static LoggerUtils getInstance() {
        LoggerUtils instance = new LoggerUtils();
        loggerUtilsThreadLocal.set(instance);

        return loggerUtilsThreadLocal.get();
    }

    /**
     * Log Info, this is the highest level of logging. Should be used for
     * logging test intent, and actions.
     */
    public void info(String text) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
        String timestamp = sdf.format(date);
        text = String.format("INFO: [%s] %s", timestamp, text);
        System.out.println("----> " + text);
        utilList.add(text);
        Reporter.log(String.format("<div style=\"color:green\">%s</div>", text), false);
    }

    /**
     * Log Debug, this is designed for debug information, used to determine why
     * a test is failing.
     */
    public void debug(String text) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
        String timestamp = sdf.format(date);
        text = String.format("DEBUG: [%s] %s", timestamp, text);
        System.out.println("----> " + text);
        utilList.add(text);
        Reporter.log(String.format("<div>%s</div>", text), false);
    }

    /**
     * Log A Warning. Use this when something went wrong, but may not be
     * critical for test success. Will show up yellow in the report.
     */
    public void warning(String text) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
        String timestamp = sdf.format(date);
        text = String.format("WARNING: [%s] %s", timestamp, text);
        System.out.println("----> " + text);
        utilList.add(text);
        Reporter.log(String.format("<div style=\"background-color:yellow\">%s</div>", text), false);
    }

    public void error(String text) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
        String timestamp = sdf.format(date);
        text = String.format("ERROR: [%s] %s", timestamp, text);
        System.out.println("!---- " + text);
        utilList.add(text);
        Reporter.log(String.format("<div style=\"background-color:red; color:white\">%s</div>", text), false);
    }
}
