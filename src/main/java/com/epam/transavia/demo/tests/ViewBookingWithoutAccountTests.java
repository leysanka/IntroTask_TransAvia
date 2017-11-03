package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.services.ViewBookingService;
import com.epam.transavia.demo.tests.steps.BaseTestBeforeClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;


public class ViewBookingWithoutAccountTests extends BaseTestBeforeClass {

    private BookingInfo testBookingInfo;
    private BookingInfo actualViewBookingInfo;
    private BookingInfo actualBookingDetailsInfo;

    @Factory(dataProvider = "bookingAndFlightInfoProvider")
    public ViewBookingWithoutAccountTests(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo) {

        testBookingInfo = new BookingInfo();
        testBookingInfo.setBookingNumber(bookingNumber);
        testBookingInfo.setLastName(lastName);
        testBookingInfo.setFlightDate(flightDate);
        testBookingInfo.setFlyingFrom(flyingFrom);
        testBookingInfo.setFlyingTo(flyingTo);
    }

    @BeforeClass
    public void getActualBookingInfoAfterLogin() {

        ViewBookingService viewBookingService = new ViewBookingService(driver, homePage);
        viewBookingService.loginToViewBookingWithoutAccountTest(testBookingInfo); //bookingNumber,lastName and flightDate are used for login
        actualViewBookingInfo = viewBookingService.fetchBookingInfoFromViewBooking();
        viewBookingService.viewBookingOpenBookingDetails();
        actualBookingDetailsInfo = viewBookingService.fetchBookingInfoFromBookingDetails();
    }

    @Test(description = "Verify that the expected Booking ID is loaded in ViewBooking after login without account,ie. via BookingNumb, LastName and FlightDate.")
    public void viewBookingWithoutAccountBookingIsLoaded() {
               Assert.assertEquals(actualViewBookingInfo.getBookingNumber(), testBookingInfo.getBookingNumber(), "Not equal");
    }

    @Test(description = "Verify Flying From/To fetched from ViewBooking are the same as expected for testing booking.")
    public void viewBookingWithoutAccountBookingRouteIsCorrect() {

        Assert.assertEquals(actualViewBookingInfo.getFlyingFrom(),testBookingInfo.getFlyingFrom(), "Flying From destination does not match to the expected: " + testBookingInfo.getFlyingFrom());
        Assert.assertEquals(actualViewBookingInfo.getFlyingTo(),testBookingInfo.getFlyingTo(), "Flying To destination does not match to the expected: " + testBookingInfo.getFlyingTo());
    }

    @Test(description = "Verify times fetched from ViewBooking page: Arrival time is later than Departure.")
    public void viewBookingWithoutAccountBookingArrivalTimeIsCorrect() {
        Assert.assertTrue(actualViewBookingInfo.getArrivalTime().isAfter(actualViewBookingInfo.getDepartureTime()), "Arrival time is not after the departure time.");
    }

    @Test(description = "Verify Total Price and Total Payment values fetched from ViewBooking -> BookingDetails are equal.")
    public void viewBookingWithoutAccountBookingDetailsPaymentIsCorrect() {
        Assert.assertEquals(actualBookingDetailsInfo.getTotalPaymentAmount(),actualBookingDetailsInfo.getTotalPriceAmount(),
                "Total payment amount does not equal to the Total price amount. Payment is " +actualBookingDetailsInfo.getTotalPaymentAmount()
                + ". Price is " + actualBookingDetailsInfo.getTotalPriceAmount() + "." );
    }

    @DataProvider(name = "bookingAndFlightInfoProvider")
    public static Object[][] bookingAndFlightInfoProvider() {
        return new Object[][]{
                {"MF8C9R", "kukharau", "09 June 2016", "Pisa", "Amsterdam (Schiphol)"},
        };
    }

}
