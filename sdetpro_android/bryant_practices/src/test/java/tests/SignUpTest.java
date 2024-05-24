package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import capabilities.Platform;
import config.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import models.screens.SignUpPage;

import static org.testng.Assert.assertEquals;

public class SignUpTest {
    private AppiumDriver appiumDriver;

    @BeforeClass
    public void setUp() {
        appiumDriver = DriverManager.getDriver(Platform.ANDROID);
    }

    @Test
    public void signUpAccount() {
        SignUpPage signUpPage = new SignUpPage((AndroidDriver) appiumDriver);
        String actualMsg = signUpPage.signUpUser("bryant.nguyen1801@gmail.com", "123456789");
        assertEquals(actualMsg, "You successfully signed up!");
        signUpPage.clickOKButton();
    }

    @AfterClass
    public void tearDown() {
        appiumDriver.quit();
    }
}
