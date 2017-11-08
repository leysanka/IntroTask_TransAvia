package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.services.BookingService;
import com.epam.transavia.demo.services.SearchFlightsService;
import com.epam.transavia.demo.util.DateTimeHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingPriceCalculationTests extends BaseTestBeforeClass {

    //TODO Split to before etc. methods
    @Test
    public void testPriceCalculation() {
        getTestLogger().info("BookingPriceCalculation test has started.");
        NewBooking newBooking = new NewBooking();
        newBooking.setFromDestination("Edinburgh, United Kingdom");
        newBooking.setToDestination("Paris (Orly South), France");
        newBooking.setAdultsCount(2);
        newBooking.setChildrenCount(1);
        newBooking.setBabiesCount(1);
        newBooking.setDepartDate(DateTimeHelper.calculateDateNowPlusLag(2));
        newBooking.setReturnDate(DateTimeHelper.calculateDateNowPlusLag(10));


        SearchFlightsService flightService = new SearchFlightsService();
        flightService.navigateToWhereToGoWindow();
        flightService.searchRoundTripWithParameters(newBooking);
        BookingService bookingService = new BookingService();
        bookingService.selectFirstInboundOutboundFlightsAndNext();

        Double actualPricePerAdult = bookingService.fetchPricePerAdult();
        Double actualPricePerBaby = bookingService.fetchPricePerBaby();
        Double actualPlusFarePrice = bookingService.selectAndFetchPlusFarePrice();

        Assert.assertEquals(bookingService.fetchTotalAmountPrice(),
                ((newBooking.getAdultsCount() + newBooking.getChildrenCount()) * (actualPricePerAdult + actualPlusFarePrice)
                 + (newBooking.getBabiesCount()) * actualPricePerBaby), "Total price calculated does not equal to the Total Amount price from page.");
    }
}
