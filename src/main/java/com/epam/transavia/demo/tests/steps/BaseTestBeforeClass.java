package com.epam.transavia.demo.tests.steps;

import com.epam.transavia.demo.business_objects.WelcomeScreenLanguages;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.WelcomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestBeforeClass {

    protected WebDriver driver;

    private static String welcomePageUrl = "https://www.transavia.com/";
    private static Logger testLogger = LogManager.getLogger("test");

    @BeforeClass
    protected void setUpBeforeMethod() {

        driver = Driver.getDriverByName("chrome");
        //Move to service
        driver.get(welcomePageUrl);
        WelcomePage welcomePage = new WelcomePage(driver);
        HomePage homePage = welcomePage.selectLocaleAndOpenHomePage(WelcomeScreenLanguages.OTHER_COUNTRY);
        testLogger.info("Try to select Other countries locale...");
    }

    protected Logger getTestLogger() {
        return this.testLogger;
    }

    @AfterClass(alwaysRun = true)
    protected void tearDownAfterMethod() {

        Driver.closeDriver(driver);
    }
}
