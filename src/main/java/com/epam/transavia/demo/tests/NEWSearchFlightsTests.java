package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.business_objects.bo_factory.NewBookingFactory;
import com.epam.transavia.demo.services.SearchFlightsService;
import com.epam.transavia.demo.tests.steps.BaseTestBeforeClass;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NEWSearchFlightsTests extends BaseTestBeforeClass {
    private SearchFlightsService foundFlightsService;
    private NewBooking flyingBooking;

    @BeforeMethod() //inheritGroups = true
    public void beforeMethod() {
        Assert.assertTrue(new SearchFlightsService().navigateToWhereToGoWindow(), "Where To Go window is not displayed, cannot proceed with further tests.");

    }

    //, dependsOnMethods = "beforeMethod"
    @BeforeGroups(groups = {"found_flights"})
    public void searchOneWayBookingWithFlights() {
        foundFlightsService = new SearchFlightsService();
        flyingBooking = NewBookingFactory.createFoundRouteBooking();
        foundFlightsService.setFlyDestinations(flyingBooking);
        foundFlightsService.searchDefaultOneWayOneUserFlight();
    }

    @Test(groups = {"found_flights"})
    public void expectedDaysCountSpinnersShouldBeDisplayed() {
        //Assert.assertEquals();
    }

    @Test(groups = {"found_flights"})
    public void availableFlightsShouldBeFound() {

    }

    @Test(description = "Verify that error message \"Unfortunately we do not fly from Dubai, United Arab Emirates to...\" is shown for the unsupported flight destination")
    public void doNotFlyDestinationErrorShouldBeDisplayed() {
        SearchFlightsService searchFlightsService = new SearchFlightsService();
        NewBooking notFlyingBooking = NewBookingFactory.createNotFlyingBooking();
        searchFlightsService.setFlyDestinations(notFlyingBooking);
        searchFlightsService.searchDefaultOneWayOneUserFlight();

        Assert.assertEquals(searchFlightsService.fetchBookingError(), notFlyingBooking.getBookingError(), "Error on Booking page does not meet to the expected: " + notFlyingBooking.getBookingError());
    }


}
