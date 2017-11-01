package com.epam.transavia.demo.gui.services;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewBookingService {

    private HomePage homePage;
    private ViewYourBookingPage viewYourBookingPage;
    private BookingDetailsPage bookingDetailsPage;

    private static Logger logger = LogManager.getLogger();

    public ViewBookingService(HomePage homePage) {
        this.homePage = homePage;
    }

    public ViewBookingService(HomePage homePage, BookingInfo bookingInfo) {
        this.homePage = homePage;
        this.viewYourBookingPage = loginToViewBookingWithoutAccount(bookingInfo);
    }

    private ViewYourBookingPage loginToViewBookingWithoutAccount(BookingInfo bookingInfo) {
        try {
            return homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);
        } catch (PageNotCreatedException | WrongPageException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private BookingDetailsPage loginToViewBookingOpenBookingDetails() {
        try {
            return viewYourBookingPage.openBookingDetails();
        } catch (WrongPageException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String fetchViewBookingNumberAfterLogin() {
        return viewYourBookingPage.getLoadedBookingNumber();
    }

    public LocalDateTime fetchViewBookingArrivalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(viewYourBookingPage.getArrivalTime(), formatter);
    }

    public LocalDateTime fetchViewBookingDepartureTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
            bookingDetailsPage = this.loginToViewBookingOpenBookingDetails();
        }
        return bookingDetailsPage != null ? bookingDetailsPage.getTotalPaymentValue() : null;
    }

    public String fetchBookingDetailsTotalPriceAmount() {
        if (bookingDetailsPage == null) {
            bookingDetailsPage = this.loginToViewBookingOpenBookingDetails();
        }
        return bookingDetailsPage != null ? bookingDetailsPage.getTotalAmountValue() : null;
    }

}
