package com.epam.transavia.demo.services;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import com.epam.transavia.demo.util.DateTimeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.format.DateTimeFormatter;

public class ViewBookingService {

    private HomePage homePage;
    private WebDriver driver;

    private static Logger logger = LogManager.getLogger();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ViewBookingService(WebDriver driver) {
        this.driver = driver;
    }

    public ViewBookingService(WebDriver driver, HomePage homePage) {
        this.driver = driver;
        this.homePage = homePage;
    }

    public void loginToViewBookingWithoutAccountTest(BookingInfo bookingInfo) {
       // HomePage homePage = new HomePage(driver);
        homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);

    }

    public void loginToViewBookingOpenBookingDetails() {
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
