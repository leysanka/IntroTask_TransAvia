package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.business_objects.bo_factory.NewBookingStaticFactory;
import com.epam.transavia.demo.reporting.TestsLogger;
import com.epam.transavia.demo.services.SearchFlightsService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchFlightErrorsTests extends BaseTestBeforeClass {

    @BeforeMethod()
    public void beforeMethod() {
        Assert.assertTrue(new SearchFlightsService().navigateToWhereToGoWindow(), "Where To Go window is not displayed, cannot proceed with further tests.");

    }

    @Test(description = "Verify that error message \"Unfortunately we do not fly from Dubai, United Arab Emirates to...\" is shown for the unsupported flight destination")
    public void doNotFlyDestinationErrorShouldBeDisplayed() {
        TestsLogger.info("doNotFlyDestinationErrorShouldBeDisplayed test has started.");
        SearchFlightsService searchFlightsService = new SearchFlightsService();
        NewBooking notFlyingBooking = NewBookingStaticFactory.createNotFlyingBooking();

        searchFlightsService.searchDefaultOneWayOneUserFlight(notFlyingBooking);

        Assert.assertEquals(searchFlightsService.getBookingError(), notFlyingBooking.getBookingError(), "Error on Booking page does not meet to the expected: " + notFlyingBooking.getBookingError());
    }
}
