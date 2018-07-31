package com.crossover.pageobjects;

import com.crossover.common.BrowserFunction;
import com.crossover.dataconstants.Constants;
import com.crossover.dataconstants.NumericConstant;
import com.crossover.repo.Locator;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import static com.crossover.dataconstants.Constants.MESSAGEBODY;


/**
 * @author Mahesh akoju
 *         This class will click on compose button and fill TO field Subject field and Body text and take the screen shots if any failure on compose page
 *
 *         It also upload and veriy attachments
 */
public class ComposePage extends AbstractPageObject {
   public  String Testdata_SubjectText = Constants.SUBJECT;
    public String actualText;
    public WebDriver driver;
    Locator locator = new Locator();
    BrowserFunction browserFunction = new BrowserFunction();

    String screenshotLoc = System.getProperty("user.dir") + "/FailureScreenshots/";

    public ComposePage(WebDriver driver) {
        this.driver = driver;
    }



    /**
     * This Method is used to click on compose and entering TO,Subject and message fields
     *
     * @throws Exception
     */
    public void clickCompose(String to, String sub, String mess, ExtentReports report, ExtentTest extentTest) throws Exception {
        try {
            waitForElementToBeClickable(driver, locator.compose, 10);
            extentTest.log(LogStatus.PASS, "Click On Compose button Successfully ");
            Thread.sleep(NumericConstant.MINIMUM_WAIT_TIME);
            WebElement ele = driver.findElement(By.xpath("//*[@name='to']"));
            ele.sendKeys(to);
            ele.sendKeys(Keys.ENTER);

            extentTest.log(LogStatus.PASS, "Enter valid email:" + to + " Successfully ");
            waitForElementToBeClickable(driver, locator.Subject, 10);

            enterText(driver, locator.Subject, "xpath", sub);
            extentTest.log(LogStatus.PASS, "Enter subject:" + sub + " Successfully ");

            enterText(driver, locator.body, "xpath", mess);
            extentTest.log(LogStatus.PASS, "Enter body:" + mess + " Successfully ");

            Thread.sleep(NumericConstant.MINIMUM_WAIT_TIME);
            uploadAttachments();

            Thread.sleep(NumericConstant.MAXIMUM_WAIT_TIME);
            waitForElementToBeClickable(driver, locator.Send, 10);
            extentTest.log(LogStatus.PASS, "Click On send button Successfully ");
            Thread.sleep(NumericConstant.MAXIMUM_WAIT_TIME);

        } catch (Exception e) {
            String errorPath = getScreenshot(driver, screenshotLoc, "clickCompose");
            String imagePath = extentTest.addScreenCapture(errorPath);
            extentTest.log(LogStatus.FAIL, "Compose is not clicked: " + e.toString(), imagePath);

        }
    }

    /**
     * @throws IOException  This method will upload the documents to gmail by Using Robot class
     * @throws AWTException
     */
    public void uploadAttachments() throws IOException, AWTException {

        WebElement uploadElement = driver.findElement(By.xpath(locator.attachmentIcon));
        uploadElement.click();
        Robot robot = new Robot();
        robot.setAutoDelay(2000);
        StringSelection stringSelection = new StringSelection(Constants.ATTACHMENTPATH + Constants.ATTACHMENTFILENAME);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        robot.setAutoDelay(3000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);


    }


    public void logout(ExtentReports report, ExtentTest extentTest) throws Exception {
        try {
            waitForElementToBeClickable(driver, locator.logbutton, 10);
            extentTest.log(LogStatus.PASS, "Click On logout button Successfully ");
            Thread.sleep(NumericConstant.MAXIMUM_WAIT_TIME);
            waitForElementToBeClickable(driver, locator.logout, 10);

        } catch (Exception e) {
            String errorPath = getScreenshot(driver, screenshotLoc, "logout");
            String imagePath = extentTest.addScreenCapture(errorPath);
            extentTest.log(LogStatus.FAIL, "logout is not clicked: " + e.toString(), imagePath);
        }
    }


    /**
     * It is used to verify subject,body of the gmail and attachments
     *
     * @param extentTest
     * @throws Exception
     */
    public void verifyData(ExtentReports report, ExtentTest extentTest) throws Exception {
        String attachementTitle = Constants.ATTACHMENTFILENAME;
        waitForElementToBeClickable(driver, locator.inboxButton, 20);
        Thread.sleep(NumericConstant.MAXIMUM_WAIT_TIME);
        waitForElementToBeClickable(driver, locator.clickemail, 10);
         actualText = readTextOnElement(driver, locator.readSubject, "xpath");

        if (actualText.equalsIgnoreCase(Testdata_SubjectText)) {
            extentTest.log(LogStatus.PASS, "Verify subject Text :::: " + actualText + "----Matched Successfully");
        }
        verifyEmailBodyMessage(extentTest);
        verifyEmailAttachment(attachementTitle);
        extentTest.log(LogStatus.PASS, "Verify Body  Text :::: " + MESSAGEBODY + "----Matched Successfully");

    }

    /**
     * This Method will count the unread message of Inbox
     *
     * @param extentTest
     */
    public void verifyEmailBodyMessage(ExtentTest extentTest) {
        List<WebElement> unreademail = driver.findElements(By.xpath("//*[@class='zF']"));
        System.out.println("Un read messages of Inbox: " + unreademail.size());
        for (int i = 0; i < unreademail.size(); i++) {
            if (unreademail.get(i).isDisplayed() == true) {
                if (unreademail.get(i).getText().equals(MESSAGEBODY)) {
                    System.out.println("Yes we have got mail form------- " + MESSAGEBODY);
                    break;
                } else {
                    System.out.println("No mail form-------- " + MESSAGEBODY);
                }
            }
        }
    }

    /**
     * This method will verify Email attachment name and print the attachment title name
     *
     * @param attachmentTitle
     * @return
     */
    public boolean verifyEmailAttachment(String attachmentTitle) {
        String currentAttachTitle = driver.findElement(By.xpath("//div[@class='gK']//img[@class='f gW']")).getAttribute("title");
        System.out.println("Attachment Title : " + attachmentTitle);
        if (attachmentTitle.equalsIgnoreCase(currentAttachTitle))
            return true;
        else
            return false;
    }

}


