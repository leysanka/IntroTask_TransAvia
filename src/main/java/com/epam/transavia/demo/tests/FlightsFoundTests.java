package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.tests.steps.BaseTest;
import com.epam.transavia.demo.tests.steps.BookingPageTests;
import com.epam.transavia.demo.tests.steps.HomePageTests;
import org.testng.annotations.*;

@Test
public class FlightsFoundTests extends BaseTest {

    // TC_1: to verify that at least from 1 to 7 one way flights found for a single adult person
    @Test(priority = 0, dataProvider = "oneWayOneUserFlightProvider")
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

    @DataProvider(name = "oneWayOneUserFlightProvider")
    public Object[][] oneWayOneUserFlightProvider() {
        return new Object[][]{
                {"Paris (Orly South), France", "Amsterdam (Schiphol), Netherlands", 7, "1 Adult"},
                // {"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia",14, "2"}
        };
    }
}
