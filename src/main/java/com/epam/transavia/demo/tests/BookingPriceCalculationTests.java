package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.services.BookingService;
import com.epam.transavia.demo.services.SearchFlightsService;
import com.epam.transavia.demo.util.DateTimeHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookingPriceCalculationTests extends BaseTestBeforeClass {

    private BookingService bookingService;
    private SearchFlightsService flightService;
    private NewBooking newBooking;

    @BeforeClass () //dependsOnMethods = "navigateToHomePage"
    public void setupForBookingTests() {
        flightService = new SearchFlightsService();
        bookingService = new BookingService();
        newBooking = new NewBooking();
        newBooking.setFromDestination("Edinburgh, United Kingdom");
        newBooking.setToDestination("Paris (Orly South), France");
        newBooking.setAdultsCount(2);
        newBooking.setChildrenCount(1);
        newBooking.setBabiesCount(1);
        newBooking.setDepartDate(DateTimeHelper.calculateDateNowPlusLag(1));
        newBooking.setReturnDate(DateTimeHelper.calculateDateNowPlusLag(8));

    }


    @Test(description = "2 Adults, 1 Children and 1 Baby passengers round trip search, select 1st found inbound and outbound flights, fetch prices per adult(=child) and baby,"
                        + " select Plus fare class, fetch its price, fetch total price and verify it's calculated properly.")

    public void totalPriceCalculationForAllPassengersRoundTripBookingIsCorrect() {
        getTestLogger().info("BookingPriceCalculation test has started.");

        flightService.navigateToWhereToGoWindow();
        flightService.searchRoundTripWithParameters(newBooking);
        bookingService.selectFirstInboundOutboundFlightsAndNextToFareSelection();

        Double actualPricePerAdult = bookingService.fetchPricePerAdult();
        Double actualPricePerBaby = bookingService.fetchPricePerBaby();
        Double actualPlusFarePrice = bookingService.selectAndFetchPlusFarePrice();

        Assert.assertEquals(bookingService.fetchTotalAmountPrice(),
                ((newBooking.getAdultsCount() + newBooking.getChildrenCount()) * (actualPricePerAdult + actualPlusFarePrice)
                 + (newBooking.getBabiesCount()) * actualPricePerBaby), "Total price calculated does not equal to the Total Amount price from page.");
    }

    //TODO: To be implemented.
    @Test(enabled = false)
    public void totalPriceCalculationMultiCityRoundTripIsCorrect() {

    }
}
