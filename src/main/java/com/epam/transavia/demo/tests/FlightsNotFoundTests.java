package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.tests.pages.BaseTest;
import com.epam.transavia.demo.tests.pages.BookingPageTests;
import com.epam.transavia.demo.tests.pages.HomePageTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class FlightsNotFoundTests extends BaseTest {

    /*TC_8: to verify that error message "Unfortunately we do not fly from Dubai, United Arab Emirates to..." is shown for the unsupported flight destination*/
    @Test(priority = 1)

    public void doNotFlyDestinationErrorShouldBeShown(){

        final String DEST_FROM = "Dubai, United Arab Emirates";
        final String DEST_TO = "Agadir, Morocco";
        final String ERROR_MSG = "Unfortunately we do not fly from Dubai, United Arab Emirates to Agadir, Morocco." +
                " However, we do fly from Dubai, United Arab Emirates to other destinations." +
                " Please change your destination and try again.";
        HomePageTests homePageTests = new HomePageTests(driver,homePage);
        BookingPageTests bookingPageTests = new BookingPageTests();

        homePageTests.testFromDestinationFillsCorrectly(DEST_FROM);
        homePageTests.testToDestinationFillsCorrectly(DEST_TO);
        homePage.searchBtnSubmit();
        bookingPage = bookingPageTests.createBookingPageIfValid(driver);
        Assert.assertTrue(bookingPage.isUnsupportedFlightErrorShown(ERROR_MSG),
                ERROR_MSG.substring(0,40)+"... is not displayed or does not meet to the expected.");

    }

}
