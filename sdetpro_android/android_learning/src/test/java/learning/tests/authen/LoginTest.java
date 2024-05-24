package learning.tests.authen;

import io.appium.java_client.AppiumDriver;
import learning.capabilities.Platform;
import learning.driver.DriverFactory;
import learning.test_flows.authentication.LoginFlow;

import java.util.ArrayList;
import java.util.List;

public class LoginTest {
    public static void main(String[] args) {
        AppiumDriver appiumDriver = DriverFactory.getDriver(Platform.ANDROID);
        List<LoginCredData> loginCredData = new ArrayList<>();
        loginCredData.add(new LoginCredData("abc", "12345678"));
        loginCredData.add(new LoginCredData("abc@gmail.com", "123456"));
        loginCredData.add(new LoginCredData("abc@gmail.com", "12345678"));
        for (LoginCredData loginCred : loginCredData) {
            try {
                LoginFlow loginFlow = new LoginFlow(appiumDriver, loginCred.getEmail(), loginCred.getPassword());
                loginFlow.gotoLoginScreen();
                loginFlow.login();
                loginFlow.verifyLogin();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        appiumDriver.quit();
    }

    public static class LoginCredData {
        private String email;

        private String password;

        public LoginCredData(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
