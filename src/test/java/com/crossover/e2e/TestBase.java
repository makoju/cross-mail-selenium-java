package com.crossover.e2e;

import com.crossover.common.BrowserFunction;
import com.crossover.common.ReportFunction;
import com.crossover.dataconstants.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;


/**
 * @author Mahesh akoju
 */
public class TestBase {
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;
    BrowserFunction browserFunction = new BrowserFunction();
    Constants constants = new Constants();

    String filePath = System.getProperty("user.dir") + "/Results/AutomationReport.html";

    public TestBase(){
        ReportFunction reportFunction = new ReportFunction();
        report = reportFunction.getInstance(filePath);
        driver = browserFunction.launchBrowser(constants.BROWSER);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        report.flush();
    }


}

