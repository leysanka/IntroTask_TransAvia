package com.epam.transavia.demo.services;

import com.epam.transavia.demo.business_objects.BookingDetailsInfo;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import com.epam.transavia.demo.util.DateTimeConverter;
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
        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        viewYourBookingPage.openBookingDetails();
    }

    public BookingDetailsInfo fetchBookingInfoFromViewBooking() {

        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        BookingDetailsInfo actualInfoViewBooking = new BookingDetailsInfo();
        actualInfoViewBooking.setBookingNumber(viewYourBookingPage.getLoadedBookingNumber());
        actualInfoViewBooking.setFlyingFrom(viewYourBookingPage.getFlyingFrom());
        actualInfoViewBooking.setFlyingTo(viewYourBookingPage.getFlyingTo());
        actualInfoViewBooking.setArrivalTime(DateTimeConverter.formatStringToLocalDateTime(viewYourBookingPage.getArrivalTime()));
        actualInfoViewBooking.setDepartureTime(DateTimeConverter.formatStringToLocalDateTime(viewYourBookingPage.getDepartureTime()));

        return actualInfoViewBooking;
    }

    public BookingDetailsInfo fetchBookingInfoFromBookingDetails() {
        BookingDetailsPage bookingDetailsPage = new BookingDetailsPage(driver);
        BookingDetailsInfo actualInfoBookingDetails = new BookingDetailsInfo();
        actualInfoBookingDetails.setTotalPaymentAmount(bookingDetailsPage.getTotalPaymentValue());
        actualInfoBookingDetails.setTotalPriceAmount(bookingDetailsPage.getTotalAmountValue());

        return actualInfoBookingDetails;
    }


}
