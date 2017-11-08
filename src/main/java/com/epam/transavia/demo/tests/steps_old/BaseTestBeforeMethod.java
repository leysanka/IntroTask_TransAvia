package com.epam.transavia.demo.tests.steps_old;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.BookingPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.business_objects.WelcomeScreenLanguages;
import com.epam.transavia.demo.gui.pages.WelcomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

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
        driver.navigate().to(homePageUrl);

        WelcomePage welcomePage = new WelcomePage(driver);
        homePage = welcomePage.selectLocaleAndOpenHomePage(WelcomeScreenLanguages.OTHER_COUNTRY);
        testLogger.info("Try to select Other countries locale...");
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.whereToGoWindowIsDisplayed(), "Window \"Where do you want to go?\" is not displayed.");

    }

    protected Logger getTestLogger() {
        return testLogger;
    }

    @AfterSuite(alwaysRun = true)
    protected void tearDownAfterMethod() {
        driver.quit();
        Driver.clearInstances();
    }
}
