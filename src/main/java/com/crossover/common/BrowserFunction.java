package com.crossover.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author Mahesh akoju
 */
public class BrowserFunction {
    WebDriver driver;

    /**
     *Returns an information driver about the active browser
     *
     * @param browserName
     * @return
     */
    public WebDriver launchBrowser(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir") + "/Drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/Drivers/MicrosoftWebDriver.exe");
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * To Launch URL
     * @param url
     */
    public void launchURL(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    public void refresh() {
        driver.navigate().refresh();
    }
}

