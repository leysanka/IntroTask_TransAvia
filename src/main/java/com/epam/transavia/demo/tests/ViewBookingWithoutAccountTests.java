package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.BookingDetailsInfo;
import com.epam.transavia.demo.services.ViewBookingService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

//@Listeners(CustomTestNGListener.class)
public class ViewBookingWithoutAccountTests extends BaseTestBeforeClass {

    private BookingDetailsInfo testBookingInfo;
    private BookingDetailsInfo actualViewBookingInfo;
    private BookingDetailsInfo actualBookingDetailsInfo;


    @Factory(dataProvider = "bookingAndFlightInfoProvider")
    public ViewBookingWithoutAccountTests(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo) {
        testBookingInfo = new BookingDetailsInfo();
        testBookingInfo.setBookingNumber(bookingNumber);
        testBookingInfo.setLastName(lastName);
        testBookingInfo.setFlightDate(flightDate);
        testBookingInfo.setFlyingFrom(flyingFrom);
        testBookingInfo.setFlyingTo(flyingTo);
    }


    @BeforeClass(description = "Open Manage Booking menu from HomePage, press View Booking and proceed with login without account in opened Login page.")
    public void getActualBookingInfoAfterLogin() {
        ViewBookingService viewBookingService = new ViewBookingService();
        viewBookingService.loginToViewBookingWithoutAccountTest(testBookingInfo); //bookingNumber,lastName and flightDate are used for login
        actualViewBookingInfo = viewBookingService.getBookingInfoFromViewBooking();
        viewBookingService.viewBookingOpenBookingDetails();
        actualBookingDetailsInfo = viewBookingService.getBookingInfoFromBookingDetails();
    }

    @Test(description = "Verify that the expected Booking ID is loaded in ViewBooking after login without account,ie. via BookingNumb, LastName and FlightDate.")
    public void viewBookingWithoutAccountBookingIsLoaded() {
        Assert.assertEquals(actualViewBookingInfo.getBookingNumber(), testBookingInfo.getBookingNumber(), "Loaded Booking ID does not equal to the expected: " + testBookingInfo.getBookingNumber());
    }

    @Test(description = "Verify Flying From/To fetched from ViewBooking are the same as expected for testing booking.")
    public void viewBookingWithoutAccountBookingRouteIsCorrect() {

        Assert.assertEquals(actualViewBookingInfo.getFlyingFrom(), testBookingInfo.getFlyingFrom(), "Flying From destination does not match to the expected: " + testBookingInfo.getFlyingFrom());
        Assert.assertEquals(actualViewBookingInfo.getFlyingTo(), testBookingInfo.getFlyingTo(), "Flying To destination does not match to the expected: " + testBookingInfo.getFlyingTo());
    }

    @Test(description = "Verify times fetched from ViewBooking page: Arrival time is later than Departure.")
    public void viewBookingWithoutAccountBookingArrivalTimeIsCorrect() {
        Assert.assertTrue(actualViewBookingInfo.getArrivalTime().isAfter(actualViewBookingInfo.getDepartureTime()), "Arrival time is not after the departure time.");
    }

    @Test(description = "Verify Total Price and Total Payment values fetched from ViewBooking -> BookingDetails are equal.")
    public void viewBookingWithoutAccountBookingDetailsPaymentIsCorrect() {
        Assert.assertEquals(actualBookingDetailsInfo.getTotalPaymentAmount(), actualBookingDetailsInfo.getTotalPriceAmount(),
                "Total payment amount does not equal to the Total price amount. Payment is " + actualBookingDetailsInfo.getTotalPaymentAmount()
                + ". Price is " + actualBookingDetailsInfo.getTotalPriceAmount() + ".");
    }

    @DataProvider(name = "bookingAndFlightInfoProvider")
    public static Object[][] bookingAndFlightInfoProvider() {
        return new Object[][]{
                {"MF8C9R", "kukharau", "09 June 2016", "Pisa", "Amsterdam (Schiphol)"},
        };
    }

}


