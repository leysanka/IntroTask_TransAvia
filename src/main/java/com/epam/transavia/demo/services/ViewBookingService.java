package com.epam.transavia.demo.services;

import com.epam.transavia.demo.business_objects.BookingInfo;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import com.epam.transavia.demo.util.DateTimeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class ViewBookingService {

    private WebDriver driver;
    private static Logger logger = LogManager.getLogger();

    public ViewBookingService(WebDriver driver) {
        this.driver = driver;
    }

    //public void

    public void loginToViewBookingWithoutAccountTest(BookingInfo bookingInfo) {
        HomePage homePage = new HomePage(driver);
        homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);
    }

    public void viewBookingOpenBookingDetails() {
        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        viewYourBookingPage.openBookingDetails();
    }

    public BookingInfo fetchBookingInfoFromViewBooking() {

        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        BookingInfo actualInfoViewBooking = new BookingInfo();
        actualInfoViewBooking.setBookingNumber(viewYourBookingPage.getLoadedBookingNumber());
        actualInfoViewBooking.setFlyingFrom(viewYourBookingPage.getFlyingFrom());
        actualInfoViewBooking.setFlyingTo(viewYourBookingPage.getFlyingTo());
        actualInfoViewBooking.setArrivalTime(DateTimeConverter.formatStringToLocalDateTime(viewYourBookingPage.getArrivalTime()));
        actualInfoViewBooking.setDepartureTime(DateTimeConverter.formatStringToLocalDateTime(viewYourBookingPage.getDepartureTime()));

        return actualInfoViewBooking;
    }

    public BookingInfo fetchBookingInfoFromBookingDetails() {
        BookingDetailsPage bookingDetailsPage = new BookingDetailsPage(driver);
        BookingInfo actualInfoBookingDetails = new BookingInfo();
        actualInfoBookingDetails.setTotalPaymentAmount(bookingDetailsPage.getTotalPaymentValue());
        actualInfoBookingDetails.setTotalPriceAmount(bookingDetailsPage.getTotalAmountValue());

        return actualInfoBookingDetails;
    }


}
