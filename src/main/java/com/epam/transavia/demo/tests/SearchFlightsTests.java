package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.bo_factory.NewBookingStaticFactory;
import com.epam.transavia.demo.services.SearchFlightsService;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;


public class SearchFlightsTests extends BaseTestBeforeClass {

    private SearchFlightsService foundFlightsService;

    @BeforeGroups(groups = {"found_flights"})
    public void searchOneWayBookingWithFlights() {

        foundFlightsService = new SearchFlightsService();
        foundFlightsService.navigateToWhereToGoWindow();
        foundFlightsService.searchDefaultOneWayOneUserFlight(NewBookingStaticFactory.createAlwaysFoundRouteBooking());
    }

    @Test(groups = {"found_flights"},
            description = "Verify that correct number of date spinners shown, for one-way Outbound flight there must be 7 (one week).")
    public void expectedDaysCountSpinnersShouldBeDisplayed() {

        Assert.assertEquals(foundFlightsService.getAllDateSpinnersCount(), 7, "The expected spinners count '7' does not equal to the shown on Booking page.");
    }

    @Test(groups = {"found_flights"}, description = "Verify that al least one date with flight is found.")
    public void availableFlightsShouldBeFound() {

        Assert.assertTrue(foundFlightsService.getDateSpinnersWithFlightsCount() > 0, "No dates with available flights are found.");
    }

}
