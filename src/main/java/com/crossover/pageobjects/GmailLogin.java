package com.crossover.pageobjects;

import com.crossover.repo.Locator;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;

/**
 * @author Mahesh akoju
 * *
 * This page is to enter username, password and clicking on sign in button of gamil
 */
public class GmailLogin extends AbstractPageObject {

    public WebDriver driver;

    Locator locator = new Locator();
    String screenshotLoc = System.getProperty("user.dir") + "/FailureScreenshots/";

    public GmailLogin(WebDriver driver) {
        this.driver = driver;
    }

    public void loginToGmail(String username, String pwd, ExtentReports report, ExtentTest extentTest) throws Exception {
        try {
            enterText(driver, locator.UName, "xpath", username);
            extentTest.log(LogStatus.PASS, "Enter valid userName:" + username + " Successfully ");


            waitForElementToBeClickable(driver, locator.Next, 20);
            extentTest.log(LogStatus.PASS, "Click On Next button Successfully ");
                Thread.sleep(3000);
            enterText(driver, locator.GPWD, "xpath", pwd);
            extentTest.log(LogStatus.PASS, "Enter valid password:" + pwd + " Successfully ");


            waitForElementToBeClickable(driver, locator.Submit, 20);
            extentTest.log(LogStatus.PASS, "Click On Submit button Successfully ");


        } catch (Exception e) {
            String errorPath = getScreenshot(driver, screenshotLoc, "clickGmailLogin");
            String imagePath = extentTest.addScreenCapture(errorPath);
            extentTest.log(LogStatus.FAIL, "Login button is not clicked: " + e.toString(), imagePath);
        }
    }

}

