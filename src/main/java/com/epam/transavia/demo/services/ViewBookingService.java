package com.epam.transavia.demo.services;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import com.epam.transavia.demo.util.DateTimeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewBookingService {

    private HomePage homePage;
    private WebDriver driver;
    private ViewYourBookingPage viewYourBookingPage;
    private BookingDetailsPage bookingDetailsPage;

    private static Logger logger = LogManager.getLogger();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ViewBookingService(HomePage homePage) {
        this.homePage = homePage;
    }

    public ViewBookingService(WebDriver driver, HomePage homePage) {
        this.driver = driver;
        this.homePage = homePage;
    }

    public ViewBookingService(HomePage homePage, BookingInfo bookingInfo) {
        this.homePage = homePage;
        this.viewYourBookingPage = loginToViewBookingWithoutAccount(bookingInfo);
    }

    public ViewYourBookingPage loginToViewBookingWithoutAccount(BookingInfo bookingInfo) {
        return homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);

    }

    public void loginToViewBookingWithoutAccountTest(BookingInfo bookingInfo) {
         homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);

    }

    public BookingDetailsPage loginToViewBookingOpenBookingDetails() {
         return viewYourBookingPage.openBookingDetails();

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

        BookingInfo actualInfoBookingDetails = new BookingInfo();
        actualInfoBookingDetails.setTotalPaymentAmount(bookingDetailsPage.getTotalPaymentValue());
        actualInfoBookingDetails.setTotalPriceAmount(bookingDetailsPage.getTotalAmountValue());

        return actualInfoBookingDetails;
    }

    public String fetchViewBookingNumberAfterLogin() {
        return viewYourBookingPage.getLoadedBookingNumber();
    }

    public LocalDateTime fetchViewBookingArrivalTime() {

        return LocalDateTime.parse(viewYourBookingPage.getArrivalTime(), formatter);
    }

    public LocalDateTime fetchViewBookingDepartureTime() {

        return LocalDateTime.parse(viewYourBookingPage.getDepartureTime(), formatter);
    }

    public String fetchViewBookingFlyingFrom() {
        return (viewYourBookingPage.getFlyingFrom());
    }

    public String fetchViewBookingFlyingTo() {
        return (viewYourBookingPage.getFlyingTo());
    }

    public String fetchBookingDetailsTotalPaymentAmount() {
        if (bookingDetailsPage == null) {
            bookingDetailsPage = loginToViewBookingOpenBookingDetails();
        }
        return bookingDetailsPage != null ? bookingDetailsPage.getTotalPaymentValue() : null;
    }

    public String fetchBookingDetailsTotalPriceAmount() {
        if (bookingDetailsPage == null) {
            bookingDetailsPage = loginToViewBookingOpenBookingDetails();
        }
        return bookingDetailsPage != null ? bookingDetailsPage.getTotalAmountValue() : null;
    }

}
