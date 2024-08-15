package learning.tests.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTestNGHook {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("<!-- BeforeSuite Hook -->");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("<!-- AfterSuite Hook -->");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("\t<!-- BeforeTest Hook -->");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("\t<!-- AfterTest Hook -->");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("\t\t<!-- BeforeClass Hook -->");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("\t\t<!-- AfterClass Hook -->");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("\t\t\t<!-- BeforeMethod Hook -->");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("\t\t\t<!-- AfterMethod Hook -->");
    }

}