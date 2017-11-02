package com.epam.transavia.demo.tests.steps;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.BookingPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTestBeforeMethod {

    protected WebDriver driver;
    protected HomePage homePage = null;
    protected BookingPage bookingPage;


    private static String homePageUrl = "https://www.transavia.com/";
    static Logger testLogger = LogManager.getLogger("test");

    @BeforeMethod
    protected void setUpBeforeMethod() {

        driver = Driver.getDefaultDriver(); //Singletone usage added
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(homePageUrl);

        //TODO Divide welcome and homepage
        homePage = new HomePage(driver);
        testLogger.info("Try to select Other countries locale...");
        homePage.selectOtherCountriesLocale();
        Assert.assertTrue(homePage.whereToGoWindowIsDisplayed(), "Window \"Where do you want to go?\" is not displayed.");

    }

    protected Logger getTestLogger() {
        return this.testLogger;
    }

    @AfterSuite(alwaysRun = true)
    protected void tearDownAfterMethod() {
        driver.quit();
        Driver.clearInstances();
    }
}
