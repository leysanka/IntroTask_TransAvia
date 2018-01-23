package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.business_objects.bo_factory.BookingCreator;
import com.epam.transavia.demo.business_objects.bo_factory.NewBookingBuilder;
import com.epam.transavia.demo.reporting.CustomTestNGListener;
import com.epam.transavia.demo.services.BookingService;
import com.epam.transavia.demo.services.SearchFlightsService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomTestNGListener.class)
public class BookingPriceCalculationTests extends BaseTestBeforeClass {

    private BookingService bookingService;
    private SearchFlightsService flightService;
    private NewBooking newBooking;

    @BeforeClass()
    public void setupForBookingTests() {
        flightService = new SearchFlightsService();
        bookingService = new BookingService();
        /*Create concrete test booking with hardcoded parameters from BookingCreator class*/
        newBooking = new BookingCreator().constructRoundTripWithAllPassengersTypes(new NewBookingBuilder());

        /**
         * Alternatively NewBookingBuilder with @Factory data provider parameters could be used:
         * newBooking = new NewBookingBuilder().withFromDestination("").withReturnDate("")...getBooking();
         */

    }

    @Test(description = "2 Adults, 1 Children and 1 Baby passengers round trip search, select 1st found inbound and outbound flights, fetch prices per adult(=child) and baby,"
                        + " select Plus fare class, fetch its price, fetch total price and verify it's calculated properly.")

    public void totalPriceCalculationForAllPassengersRoundTripBookingIsCorrect() {

        flightService.navigateToWhereToGoWindow();
        flightService.searchRoundTripWithParameters(newBooking);
        bookingService.selectFirstInboundOutboundFlightsAndNextToFareSelection();

        Double actualPricePerAdult = bookingService.getPricePerAdult();
        Double actualPricePerBaby = bookingService.getPricePerBaby();
        Double actualPlusFarePrice = bookingService.selectAndFetchPlusFarePrice();

        Assert.assertEquals(bookingService.fetchTotalAmountPrice(),
                ((newBooking.getAdultsCount() + newBooking.getChildrenCount()) * (actualPricePerAdult + actualPlusFarePrice)
                 + (newBooking.getBabiesCount()) * actualPricePerBaby), "Total price calculated does not equal to the Total Amount price from page.");
    }

    //TODO: TC has to be implemented.
    @Test(enabled = false)
    public void totalPriceCalculationMultiCityRoundTripIsCorrect() {

    }
}
