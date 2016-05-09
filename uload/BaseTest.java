package com.facebook.test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.Base64;

/**
 * Created by Arghya on 08-05-2016.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setup(){
        driver=new FirefoxDriver();

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {

        driver.quit();
    }

    @AfterMethod(alwaysRun = true)
    public void catchExceptions(ITestResult result) {
        String methodName = result.getName();
        Reporter.setCurrentTestResult(result);
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        if (!result.isSuccess()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String encoded = Base64.getEncoder().withoutPadding().encodeToString(screenshot);
            Reporter.log("<a href=\"data:image/png;base64," + encoded + "\"><img src=\"data:image/png;base64,"
                    +encoded+"\" alt height='100' width='100'/></a><br/>");
            result.setAttribute("imageBytes",encoded);
        }
    }
}
