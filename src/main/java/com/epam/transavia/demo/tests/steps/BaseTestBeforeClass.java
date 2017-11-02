package com.epam.transavia.demo.tests.steps;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseTestBeforeClass {

    protected WebDriver driver;
    protected HomePage homePage;

    private static String homePageUrl = "https://www.transavia.com/";
    private static Logger testLogger = LogManager.getLogger("test");

    @BeforeClass
    protected void setUpBeforeMethod() {

        //driver = Driver.getDefaultDriver();
        driver = Driver.getDriverByName("chrome");
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(homePageUrl);

        homePage = new HomePage(driver);
        testLogger.info("Try to select Other countries locale...");
        homePage.selectOtherCountriesLocale();
        Assert.assertTrue(homePage.whereToGoWindowIsDisplayed(), "Window \"Where do you want to go?\" is not displayed.");

    }

    protected Logger getTestLogger() {
        return this.testLogger;
    }

    @AfterClass(alwaysRun = true)
    protected void tearDownAfterMethod() {
        driver.quit();
        Driver.clearInstances();
    }
}
