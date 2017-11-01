package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.services.ViewBookingService;
import com.epam.transavia.demo.tests.steps.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.time.LocalDateTime;


public class ViewBookingWithoutAccountTests extends BaseTest {

    private BookingInfo testBookingInfo;
    private BookingInfo actualBookingInfo;
    private ViewBookingService viewBookingService;

    @Factory(dataProvider = "bookingAndFlightInfoProvider")
    public ViewBookingWithoutAccountTests(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo) {

        testBookingInfo = new BookingInfo();
        testBookingInfo.setBookingNumber(bookingNumber);
        testBookingInfo.setLastName(lastName);
        testBookingInfo.setFlightDate(flightDate);
        testBookingInfo.setFlyingFrom(flyingFrom);
        testBookingInfo.setFlyingTo(flyingTo);
    }

    @BeforeMethod
    public BookingInfo getActualBookingInfoAfterLogin() {
        //Working
        viewBookingService = new ViewBookingService(driver, homePage);
        viewBookingService.loginToViewBookingWithoutAccountTest(testBookingInfo);
        return actualBookingInfo = viewBookingService.fetchBookingInfoFromViewBooking();



    }

  /*  @BeforeMethod
    public void setUp() {
       // viewBookingService = new ViewBookingService(homePage, testBookingInfo);
        ViewYourBookingPage viewYourBookingPage = viewBookingService.loginToViewBookingWithoutAccount(testBookingInfo);
    }
*/

    @Test
    public void viewBookingWithoutAccountBookingIsLoadedTest() {

        Assert.assertEquals(actualBookingInfo.getBookingNumber(), testBookingInfo.getBookingNumber(), "Not equal");

        /*Assert.assertTrue(viewBookingService.fetchViewBookingNumberAfterLogin().equals(testBookingInfo.getBookingNumber()),
                "View booking without account did not load the expected booking: " + testBookingInfo.getBookingNumber());*/
    }

    @Test
    public void viewBookingWithoutAccountBookingIsLoaded() {

        Assert.assertTrue(viewBookingService.fetchViewBookingNumberAfterLogin().equals(testBookingInfo.getBookingNumber()),
                "View booking without account did not load the expected booking: " + testBookingInfo.getBookingNumber());
    }

    @Test
    public void viewBookingWithoutAccountBookingRouteIsCorrect() {

        Assert.assertTrue(viewBookingService.fetchViewBookingFlyingFrom().equals(testBookingInfo.getFlyingFrom()), "Flying From destination does not match to the expected: " + testBookingInfo.getFlyingFrom());
        Assert.assertTrue(viewBookingService.fetchViewBookingFlyingTo().equals(testBookingInfo.getFlyingTo()), "Flying To destination does not match to the expected: " + testBookingInfo.getFlyingTo());
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
