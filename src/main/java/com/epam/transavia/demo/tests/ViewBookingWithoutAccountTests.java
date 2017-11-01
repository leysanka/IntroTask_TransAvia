package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.services.ViewBookingService;
import com.epam.transavia.demo.tests.steps.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.LocalDateTime;


public class ViewBookingWithoutAccountTests extends BaseTest {

    private BookingInfo bookingInfo;
    private ViewBookingService viewBookingService;

    @Factory(dataProvider = "bookingAndFlightInfoProvider")
    public ViewBookingWithoutAccountTests(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo) {

        bookingInfo = new BookingInfo();
        bookingInfo.setBookingNumber(bookingNumber);
        bookingInfo.setLastName(lastName);
        bookingInfo.setFlightDate(flightDate);
        bookingInfo.setFlyingFrom(flyingFrom);
        bookingInfo.setFlyingTo(flyingTo);
    }

    @BeforeMethod
    public void setUp() {
        viewBookingService = new ViewBookingService(homePage, bookingInfo);
    }

    @Test
    public void viewBookingWithoutAccountBookingIsLoaded() {

        Assert.assertTrue(viewBookingService.fetchViewBookingNumberAfterLogin().equals(bookingInfo.getBookingNumber()),
                "View booking without account did not load the expected booking: " + bookingInfo.getBookingNumber());
    }

    @Test
    public void viewBookingWithoutAccountBookingRouteIsCorrect() {

        Assert.assertTrue(viewBookingService.fetchViewBookingFlyingFrom().equals(bookingInfo.getFlyingFrom()), "Flying From destination does not match to the expected: " + bookingInfo.getFlyingFrom());
        Assert.assertTrue(viewBookingService.fetchViewBookingFlyingTo().equals(bookingInfo.getFlyingTo()), "Flying To destination does not match to the expected: " + bookingInfo.getFlyingTo());
    }

    @Test
    public void viewBookingWithoutAccountBookingArrivalTimeIsCorrect() {

        LocalDateTime arrivalTime = viewBookingService.fetchViewBookingArrivalTime();
        LocalDateTime departureTime = viewBookingService.fetchViewBookingDepartureTime();

        Assert.assertTrue(arrivalTime.isAfter(departureTime), "Arrival time is not after the departure time.");
    }

    @Test
    public void viewBookingWithoutAccountBookingDetailsPaymentIsCorrect() {

        String paymentAmount = viewBookingService.fetchBookingDetailsTotalPaymentAmount();
        String priceAmount = viewBookingService.fetchBookingDetailsTotalPriceAmount();

        Assert.assertTrue(paymentAmount.equals(priceAmount), "Total payment amount does not equal to the Total price amount.");
    }

    @DataProvider(name = "bookingAndFlightInfoProvider")
    public static Object[][] bookingAndFlightInfoProvider() {
        return new Object[][]{
                {"MF8C9R", "kukharau", "09 June 2016", "Pisa", "Amsterdam (Schiphol)"},
        };
    }


}
