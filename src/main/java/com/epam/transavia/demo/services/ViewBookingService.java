package com.epam.transavia.demo.services;

import com.epam.transavia.demo.business_objects.BookingDetailsInfo;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.ui.pages.HomePage;
import com.epam.transavia.demo.ui.pages.ViewBookingDetailsPage;
import com.epam.transavia.demo.ui.pages.ViewBookingPage;
import com.epam.transavia.demo.util.DateTimeHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class ViewBookingService {

    private static WebDriver driver = Driver.getDefaultDriver();
    private static Logger logger = LogManager.getLogger();

    public void loginToViewBookingWithoutAccountTest(BookingDetailsInfo bookingDetailsInfo) {
        HomePage homePage = new HomePage(driver);
        homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingDetailsInfo);
    }

    public void viewBookingOpenBookingDetails() {
        ViewBookingPage viewBookingPage = new ViewBookingPage(driver);
        viewBookingPage.openBookingDetails();
    }

    public BookingDetailsInfo fetchBookingInfoFromViewBooking() {

        ViewBookingPage viewBookingPage = new ViewBookingPage(driver);
        BookingDetailsInfo actualInfoViewBooking = new BookingDetailsInfo();
        actualInfoViewBooking.setBookingNumber(viewBookingPage.getLoadedBookingNumber());
        actualInfoViewBooking.setFlyingFrom(viewBookingPage.getFlyingFrom());
        actualInfoViewBooking.setFlyingTo(viewBookingPage.getFlyingTo());
        actualInfoViewBooking.setArrivalTime(DateTimeHelper.formatStringToLocalDateTime(viewBookingPage.getArrivalTime()));
        actualInfoViewBooking.setDepartureTime(DateTimeHelper.formatStringToLocalDateTime(viewBookingPage.getDepartureTime()));

        return actualInfoViewBooking;
    }

    public BookingDetailsInfo fetchBookingInfoFromBookingDetails() {
        ViewBookingDetailsPage viewBookingDetailsPage = new ViewBookingDetailsPage(driver);
        BookingDetailsInfo actualInfoBookingDetails = new BookingDetailsInfo();
        actualInfoBookingDetails.setTotalPaymentAmount(viewBookingDetailsPage.getTotalPaymentValue());
        actualInfoBookingDetails.setTotalPriceAmount(viewBookingDetailsPage.getTotalAmountValue());

        return actualInfoBookingDetails;
    }


}
