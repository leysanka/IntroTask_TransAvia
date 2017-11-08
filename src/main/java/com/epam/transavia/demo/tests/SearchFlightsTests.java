package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.bo_factory.NewBookingFactory;
import com.epam.transavia.demo.services.SearchFlightsService;
import com.epam.transavia.demo.tests.steps.BaseTestBeforeClass;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class SearchFlightsTests extends BaseTestBeforeClass {

    private SearchFlightsService foundFlightsService;

    @BeforeGroups(groups = {"found_flights"})
    public void searchOneWayBookingWithFlights() {
        foundFlightsService = new SearchFlightsService();
        if (foundFlightsService.navigateToWhereToGoWindow()) {
            foundFlightsService.setFlyDestinations(NewBookingFactory.createFoundRouteBooking());
            foundFlightsService.searchDefaultOneWayOneUserFlight();
        }
    }

    @Test(groups = {"found_flights"}, description = "Verify that correct number of date spinners shown, for one-way Outbound flight there must be 7 (one week).")
    public void expectedDaysCountSpinnersShouldBeDisplayed() {

        Assert.assertEquals(foundFlightsService.fetchAllDateSpinnersShownCount(), 7, "The expected spinners count '7' does not equal to the shown on Booking page.");
    }

    @Test(groups = {"found_flights"}, description = "Verify that al least one date with flight is found.")
    public void availableFlightsShouldBeFound() {

        Assert.assertTrue(foundFlightsService.fetchDateSpinnersWithFlightsCount() > 0, "No dates with available flights are found.");
    }

}