package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public class AllureUtils {
    public AllureUtils() {
    }

    public static void step(String name, StepBody body) throws Exception {
        String uuid = UUID.randomUUID().toString();
        StepResult result = (new StepResult()).setName(name);
        Allure.getLifecycle().startStep(uuid, result);

        try {
            body.execute();
            Allure.getLifecycle().updateStep(uuid, (s) -> {
                s.setStatus(Status.PASSED);
            });
        } catch (Exception var8) {
            Allure.getLifecycle().updateStep(uuid, (s) -> {
                s.setStatus(Status.FAILED).setStatusDetails((StatusDetails) ResultsUtils.getStatusDetails(var8).orElse(null));
            });
            Allure.getLifecycle().addAttachment("Exception message", "text/plain", "txt", var8.getMessage().getBytes());
        } finally {
            Allure.getLifecycle().stopStep(uuid);
        }

    }

    public static void addAllureProperties() {
        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/allure-results/environment.properties");

            try {
                Properties props = new Properties();
                Optional.ofNullable(System.getProperty("platformName")).ifPresent((s) -> {
                    props.setProperty("Platform Name", s);
                });
                Optional.ofNullable(System.getProperty("platformVersion")).ifPresent((s) -> {
                    props.setProperty("Platform Version", s);
                });
                Optional.ofNullable(System.getProperty("deviceName")).ifPresent((s) -> {
                    props.setProperty("Device Name", s);
                });
                props.store(fos, "See https://github.com/allure-framework/allure-app/wiki/Environment");
            } catch (Throwable var4) {
                try {
                    fos.close();
                } catch (Throwable var3) {
                    var4.addSuppressed(var3);
                }

                throw var4;
            }

            fos.close();
        } catch (IOException var5) {
            IOException e = var5;
            System.err.println("IO problem when writing allure properties file");
            System.err.println(e.getMessage());
        }

    }

    public interface StepBody {
        void execute() throws Exception;
    }
}
