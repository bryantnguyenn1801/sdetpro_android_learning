package config;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.*;

//import com.jcraft.jsch.JSchException;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
//import my.setel.utils.LogHelper;
//import my.setel.utils.SshRemoteSessionManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServicesManager {

    public static AppiumDriverLocalService getAppiumService() {
        return appiumService.get();
    }


    public static String getAppiumPort() {
        return appiumPort.get();
    }

    private static Map<String, Boolean> runningRemotePort = new HashMap<>();

    public static void setAppiumService(AppiumDriverLocalService localService) {
        appiumService.set(localService);
    }

    private static ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal<>();

    private static ThreadLocal<String> appiumPort = new ThreadLocal<>();

    public static void startAppiumServer(String mobileName, String osVersion) {
        try {
            stopAppiumServer();
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withIPAddress("127.0.0.1");
            builder.usingAnyFreePort();
            builder.withArgument(RELAXED_SECURITY)
                    .withArgument(SESSION_OVERRIDE)
                    .withArgument(LOG_LEVEL, "debug")
                    .withArgument(()-> "--allow-insecure", "chromedriver_autodownload");
            String appiumLogPath =
                    System.getProperty("user.dir") + "/appiumLogs/" + System.currentTimeMillis() + "_" + mobileName + "_" + osVersion
                            + ".text";
            builder.withLogFile(new File(appiumLogPath));
            System.out.println(appiumLogPath);
            setAppiumService(AppiumDriverLocalService.buildService(builder));
            getAppiumService().clearOutPutStreams();
            getAppiumService().start();
            if (getAppiumService() == null) {
                throw new AppiumServerHasNotBeenStartedLocallyException(
                        "An appium server node is not started!");
            }
            System.out.println("Appium server started at: - " + getAppiumService().getUrl());
        } catch (Exception e) {
            System.out.println("--------------An appium server node is not started!--------------");
            e.printStackTrace();
        }
    }

    //temporary working for macos only
//    public static void startAppiumOnRemoteServer(String mobileName, String osVersion, String user, String pwd, String host) {
//        try {
//            String appiumLogPath = System.currentTimeMillis() + "_" + mobileName + "_" + osVersion + ".text";
//            String startAppiumCommand = "export PATH=\"/usr/local/bin:$PATH\" && appium-controller --start -p 0 --host 0.0.0.0 --shutdown false --logDir '" + appiumLogPath + "'";
//            System.out.println(startAppiumCommand);
//            SshRemoteSessionManager sshSession = new SshRemoteSessionManager().getSession(user, pwd, host);
//            String appiumServerUrl = sshSession.sendCommand(startAppiumCommand);
//            appiumServerUrl = appiumServerUrl.substring(appiumServerUrl.indexOf("<"), appiumServerUrl.indexOf(">")).replaceAll("[^0-9]", "");
//            System.out.println(appiumLogPath);
//            appiumPort.set(appiumServerUrl);
//            System.out.println("Appium server started at: - " + host + appiumServerUrl);
//        } catch (Exception e) {
//            System.out.println("--------------An appium server node is not started!--------------");
//            e.printStackTrace();
//        }
//    }

    public static void stopAppiumServer() {
        try {
            if (getAppiumService() != null) {
                if (getAppiumService().isRunning()) {
                    System.out.println("----------Going to stop appium service---------");
                    System.out.println(getAppiumService().getUrl().toString());
                    System.out.println("----------Success---------");
                    getAppiumService().stop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void stopAppiumRemoteServer(String user, String pwd, String host, boolean isStopAll) {
//        try {
//            String output = "";
//            if (!isStopAll && appiumPort.get() != null) {
//                System.out.println("----------Going to stop appium service by port---------");
//                SshRemoteSessionManager sshSession = new SshRemoteSessionManager().getSession(user, pwd, host);
//                String stopAppiumCommand = "export PATH=\"/usr/local/bin:$PATH\" && appium-controller --start -p " + appiumPort.get();
//                output = sshSession.sendCommand(stopAppiumCommand);
//                LogHelper.getLogger().info(output);
//                System.out.println(output);
//            } else {
//                System.out.println("----------Going to stop all appium service in remote---------");
//                SshRemoteSessionManager sshSession = new SshRemoteSessionManager().getSession(user, pwd, host);
//                String stopAppiumCommand = "pkill -f appium";
//                output = sshSession.sendCommand(stopAppiumCommand);
//                LogHelper.getLogger().info(output);
//                System.out.println(output);
//            }
//        } catch (JSchException | IOException jSchException) {
//            jSchException.printStackTrace();
//        }
//    }

}