package com.epam.transavia.demo.tests.steps;

import com.epam.transavia.demo.gui.pages.BookingPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected BookingPage bookingPage;


    private static String homePageUrl = "https://www.transavia.com/";

    static Logger testLogger = LogManager.getLogger("test");

    @BeforeMethod
    protected void setUpBeforeMethod(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(homePageUrl);
        homePage = new HomePage(driver);
        try {
            testLogger.info("Try to select Other countries locale...");
             homePage.selectOtherCountriesLocale();
        }
        catch (NullPointerException ex){
            testLogger.error("Could not select Other Countries");
            homePage = null;
        }
        Assert.assertNotNull(homePage, "Home page is not created.");
        Assert.assertTrue(homePage.whereToGoWindowIsDisplayed(), "Window \"Where do you want to go?\" is not displayed.");

    }

    protected Logger getTestLogger(){
        return this.testLogger;
    }

    @AfterMethod (alwaysRun = true)
    protected void tearDownAfterMethod() {
        driver.quit();
    }
}