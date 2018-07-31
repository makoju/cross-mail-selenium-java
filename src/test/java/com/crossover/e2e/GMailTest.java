package com.crossover.e2e;

import com.crossover.common.ReportFunction;
import com.crossover.dataconstants.Constants;
import com.crossover.dataconstants.NumericConstant;
import com.crossover.pageobjects.ComposePage;
import com.crossover.pageobjects.GmailLogin;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This is main class to execute test cases based on priority if you have multiple tests
 *
 */
public class GMailTest extends TestBase {

    GmailLogin gmailLogin;
    ComposePage composePage;

    @BeforeMethod
    public void testData() throws Exception {
        browserFunction.launchURL(constants.GMAIL_URL);
        gmailLogin = new GmailLogin(driver);
        composePage = new ComposePage(driver);

    }


    @Test(priority = 0)
    public void compseSendVerifyEmail() throws Exception {
        report = ReportFunction.getInstance(filePath);
        test = report.startTest("Gmail-Compose-TestCase");
        gmailLogin.loginToGmail(Constants.USERNAME, Constants.PASSWORD, report, test);
        composePage.clickCompose(Constants.TOADDRESS, Constants.SUBJECT, Constants.MESSAGEBODY, report, test);
        Thread.sleep(NumericConstant.MAXIMUM_WAIT_TIME);
        composePage.verifyData(report, test);
        Assert.assertEquals(composePage.actualText,Constants.SUBJECT,"Subject text is matching ");
         composePage.logout(report, test);

    }

}



