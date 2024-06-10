package config;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class ServicesManager {

    private static ThreadLocal<AppiumDriverLocalService> appiumService = new ThreadLocal();

    public ServicesManager() {
    }

    public static AppiumDriverLocalService getAppiumService() {
        return appiumService.get();
    }

    public static void setAppiumService(AppiumDriverLocalService localService) {
        appiumService.set(localService);
    }

    public static void startAppiumServer(String mobileName, String osVersion) {
        try {
            stopAppiumServer();
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            int port = 4723;
            builder.withIPAddress("127.0.0.1");
            builder.usingPort(port);
            builder.withArgument(GeneralServerFlag.RELAXED_SECURITY).withArgument(GeneralServerFlag.SESSION_OVERRIDE).withArgument(GeneralServerFlag.LOG_LEVEL, "debug").withArgument(() -> {
                return "--allow-insecure";
            }, "chromedriver_autodownload");
            String appiumLogPath =
                    System.getProperty("user.dir") + "/appiumLogs/" + System.currentTimeMillis() + "_" + mobileName + "_" + osVersion
                            + ".text";
            builder.withLogFile(new File(appiumLogPath));
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
    private static int findFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
    public static void stopAppiumServer() {
        try {
            if (getAppiumService() != null && getAppiumService().isRunning()) {
                System.out.println("----------Going to stop appium service---------");
                System.out.println(getAppiumService().getUrl().toString());
                System.out.println("----------Success---------");
                getAppiumService().stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}