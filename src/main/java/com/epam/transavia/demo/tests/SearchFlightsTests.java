package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.tests.steps.BaseTestBeforeMethod;
import com.epam.transavia.demo.tests.steps.BookingPageTests;
import com.epam.transavia.demo.tests.steps.HomePageTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class SearchFlightsTests extends BaseTestBeforeMethod {

    // TC_1: to verify that at least from 1 to 7 one way flights found for a single adult person
    @Test(priority = 1, dataProvider = "oneWayOneUserFlightProvider")
    public void oneWayOneUserFlightShouldBeFound(String destFrom, String destTo, long departDaysLag, String expPassengersNumber) {
        HomePageTests homePageTests = new HomePageTests(driver, homePage);
        BookingPageTests bookingPageTests = new BookingPageTests();

        homePageTests.testFromDestinationFillsCorrectly(destFrom);
        homePageTests.testToDestinationFillsCorrectly(destTo);
        homePageTests.testDepartOnDateFillsCorrectly(departDaysLag);
        homePageTests.verifyReturnOnIsChecked();
        homePage.returnOnCheckBoxClick();
        homePageTests.verifyReturnOnIsUnchecked();
        homePageTests.verifyExpectedPassengersCountIsShown(expPassengersNumber);
        homePage.searchBtnSubmit();
        bookingPage = bookingPageTests.createBookingPageIfValid(driver);
        bookingPageTests.testExpectedDatesSpinnersCountShown(7);
        bookingPageTests.verifyFlightsAreFound();
        bookingPageTests.testCorrectNumberOfOneWayFlightsIsFound();
        getTestLogger().info("\"oneWayOneUserFlightShouldBeFound\" Test executed successfully.");
    }

    /*TC_8: to verify that error message "Unfortunately we do not fly from Dubai, United Arab Emirates to..." is shown for the unsupported flight destination*/
    @Test(priority = 1)
    public void doNotFlyDestinationErrorShouldBeShown() {

        final String DEST_FROM = "Dubai, United Arab Emirates";
        final String DEST_TO = "Agadir, Morocco";
        final String ERROR_MSG = "Unfortunately we do not fly from Dubai, United Arab Emirates to Agadir, Morocco." +
                                 " However, we do fly from Dubai, United Arab Emirates to other destinations." +
                                 " Please change your destination and try again.";
        HomePageTests homePageTests = new HomePageTests(driver, homePage);
        BookingPageTests bookingPageTests = new BookingPageTests();

        homePageTests.testFromDestinationFillsCorrectly(DEST_FROM);
        homePageTests.testToDestinationFillsCorrectly(DEST_TO);
        homePage.searchBtnSubmit();
        bookingPage = bookingPageTests.createBookingPageIfValid(driver);
        Assert.assertTrue(bookingPage.isUnsupportedFlightErrorShown(ERROR_MSG),
                ERROR_MSG.substring(0, 40) + "... is not displayed or does not meet to the expected.");

    }

    @DataProvider(name = "oneWayOneUserFlightProvider")
    public Object[][] oneWayOneUserFlightProvider() {
        return new Object[][]{
                {"Paris (Orly South), France", "Amsterdam (Schiphol), Netherlands", 7, "1 Adult"},
                // {"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia",14, "2"}
        };
    }
}
