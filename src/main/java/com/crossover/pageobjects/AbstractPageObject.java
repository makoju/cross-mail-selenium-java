package com.crossover.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mahesh akoju
 *  Element locators will be identified by this class
 */
public class AbstractPageObject {
    WebDriver driver;
    private WebDriverWait wait;

    public WebElement getElement(WebDriver driver, String locator, String elementType) {
        WebElement element;
        elementType = elementType.toLowerCase();
        if (elementType.equals("id")) {
             element = driver.findElement(By.id(locator));
            System.out.println("element found with id :" + locator);
            return element;
        } else if (elementType.equals("name")) {
             element = driver.findElement(By.name(locator));
            System.out.println("element found with name:" + locator);
            return element;
        } else if (elementType.equals("xpath")) {
             element = driver.findElement(By.xpath(locator));
            System.out.println("element found with xpath :" + locator);
            return element;
        } else if (elementType.equals("classname")) {
             element = driver.findElement(By.className(locator));
            System.out.println("element found with className :" + locator);
            return element;
        } else if (elementType.equals("tagname")) {
             element = driver.findElement(By.tagName(locator));
            System.out.println("element found with tagname:" + locator);
            return element;
        } else if (elementType.equalsIgnoreCase("css")) {
             element = driver.findElement(By.cssSelector(locator));
            System.out.println("element found with cssSelector :" + locator);
            return element;
        } else if (elementType.equalsIgnoreCase("linktext")) {
             element = driver.findElement(By.linkText(locator));
            System.out.println("element found with linktext :" + locator);
            return element;
        } else if (elementType.equalsIgnoreCase("partiallinktext")) {
             element = driver.findElement(By.partialLinkText(locator));
            System.out.println("element found with partiallinktext:" + locator);
            return element;
        } else {
            System.out.println("element not found");
            return null;
        }
    }


    public List<WebElement> getElementList(WebDriver driver, String locator, String type) {
        type = type.toLowerCase();
        List<WebElement> elementList = new ArrayList<WebElement>();
        if (type.equals("id")) {
            elementList = driver.findElements(By.id(locator));

        } else if (type.equals("xpath")) {
            elementList = driver.findElements(By.xpath(locator));

        } else if (type.equals("css")) {
            elementList = driver.findElements(By.cssSelector(locator));

        } else if (type.equals("name")) {
            elementList = driver.findElements(By.name(locator));

        } else if (type.equals("tagname")) {
            elementList = driver.findElements(By.tagName(locator));

        } else if (type.equals("linktext")) {
            elementList = driver.findElements(By.linkText(locator));

        } else if (type.equals("partiallinktext")) {
            elementList = driver.findElements(By.partialLinkText(locator));

        } else {
            System.out.println("element not found ");
        }
        return elementList;
    }


    public boolean isElementPresent(WebDriver driver, String locator, String type) {
        List<WebElement> elementList = getElementList(driver, locator, type);

        int size = elementList.size();

        if (size > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Wait for element to be displayed on page
     *
     * @param element WebElement
     */
    public void waitForElementDisplayed(WebElement element) {

        wait.until(ExpectedConditions.visibilityOf(element));
    }



    public WebElement waitForElement(WebDriver driver, By locator, int timeout) {
        WebElement element = null;
        try {
            System.out.println("Waiting for max:: " + timeout + " seconds for element to be available");

            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Element appeared on the web page");
        } catch (Exception e) {
            System.out.println("Element not appeared on the web page: " + e);

        }
        return element;
    }


    public void scrollIntoView(WebDriver driver, String locator, int timeout) {
        try {
            WebElement element = null;
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Element not appeared on the web page");
        }
    }


    public WebDriver waitForElementToBeClickable(WebDriver driver, String locator, int timeout) {

        try {
            WebElement element = null;
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
            element.click();
        } catch (Exception e) {
            System.out.println("Element not appeared on the web page");
        }
        return driver;
    }


    public void clickOnElementByUsingJS(WebDriver driver, String locator, int timeout) {
        try {
            WebElement element = null;
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            element.click();
        } catch (Exception e) {
            System.out.println("Element not appeared on the web page");
        }
    }


    public String getScreenshot(WebDriver driver, String destinationLocation, String errorName) throws Exception {
        String fileName = errorName + timestamp() + ".png";
        String destination = destinationLocation + fileName;
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(sourceFile, new File(destination));
        return destination;
    }


    public static String timestamp() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    }


    public void handleJavaScriptAlert(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }


    public void handleJavaScriptConfirm(WebDriver driver, String action) {
        Alert alert = driver.switchTo().alert();
        if (action == "OK")
            alert.accept();
        else
            alert.dismiss();
    }


    public Boolean verifyTextOnElement(WebDriver driver, String locator, String type, String expectedText) {
        String actText = readTextOnElement(driver, locator, type);
        System.out.println("Text On Element: " + actText);
        if (actText.contains(expectedText)) {
            System.out.println("true");
            return true;

        } else {
            System.out.println("false");
            return false;
        }
    }


    public String readTextOnElement(WebDriver driver, String locator, String type) {
        String textOnElement = getElement(driver, locator, type).getText();
        return textOnElement;
    }


    public void enterText(WebDriver driver, String locator, String type, String data) {
        getElement(driver, locator, type).clear();
        getElement(driver, locator, type).sendKeys(data);
    }


    public Boolean verifyBrowserTitle(WebDriver driver, String expectedTitle) {
        String actTitle = driver.getTitle();
        if (actTitle.equals(expectedTitle)) {
            return true;
        } else {
            return false;
        }
    }


    public void selectListValues(WebDriver driver, String locator, String listVal) {

        List<WebElement> ele = driver.findElements(By.xpath(locator));
        System.out.println("The total Size is:   " + ele.size());
        for (WebElement ee : ele) {
            String val = ee.getText();
            System.out.println("The text is:   " + val);
            if (val.equals(listVal)) {
                ee.click();
                break;
            }
        }

    }


    public void mouseOverElement(WebDriver driver, String locator1, String type1, String locator2, String type2) {
        WebElement element = getElement(driver, locator1, type1);
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        WebElement subElement = getElement(driver, locator2, type2);
        action.moveToElement(subElement);
        action.click();
        action.perform();

    }


}
