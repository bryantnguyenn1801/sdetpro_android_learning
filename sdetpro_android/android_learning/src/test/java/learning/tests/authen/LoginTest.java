package learning.tests.authen;

import learning.test_flows.authentication.LoginFlow;
import learning.tests.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_data.DataObjectBuilder;
import test_data.models.LoginCred;

public class LoginTest extends BaseTest {

//    @Test
//    public void loginWithCreds() {
//        List<LoginCredData> loginCredData = new ArrayList<>();
//        loginCredData.add(new LoginCredData("abc", "12345678"));
//        loginCredData.add(new LoginCredData("abc@gmail.com", "123456"));
//        loginCredData.add(new LoginCredData("abc@gmail.com", "12345678"));
//        for (LoginCredData loginCred : loginCredData) {
//            LoginFlow loginFlow = new LoginFlow(appiumDriver, loginCred.getEmail(), loginCred.getPassword());
//            loginFlow.gotoLoginScreen();
//            loginFlow.login();
//            loginFlow.verifyLogin();
//        }

    @Test(dataProvider = "loginCredData")
    public void loginWithCreds(LoginCred loginCred) {
        LoginFlow loginFlow = new LoginFlow(
                appiumDriver, loginCred.getEmail(), loginCred.getPassword()
        );
        loginFlow.gotoLoginScreen();
        loginFlow.login();
        loginFlow.verifyLogin();
    }

    @DataProvider
    public LoginCred[] loginCredData() {
        String loginCredDataPath = "/src/test/java/test_data/authen/LoginCredData.json";
        return DataObjectBuilder.buildDataObject(loginCredDataPath, LoginCred[].class);
    }

}

//public static class LoginCredData {
//    private String email;
//
//    private String password;
//
//    public LoginCredData(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//}

