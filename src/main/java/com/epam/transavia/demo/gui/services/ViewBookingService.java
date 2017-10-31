package com.epam.transavia.demo.gui.services;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.CommonPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import com.epam.transavia.demo.tests.steps.BaseTest;
import org.openqa.selenium.WebDriver;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewBookingService{

    private WebDriver driver;
    private HomePage homePage;
    private ViewYourBookingPage viewYourBookingPage;
    private BookingDetailsPage bookingDetailsPage;

    public ViewBookingService(WebDriver driver, HomePage homePage) throws WrongPageException {
        this.driver = driver;
        this.homePage = homePage;
    }

    public boolean viewBookingWithoutAccountSuccess(BookingInfo bookingInfo) throws PageNotCreatedException, WrongPageException {

        return homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo)
                    .getShownBookingNumber().equals(bookingInfo.getBookingNumber());
    }

    public boolean isArrivalTimeLaterThanDepartureInBookingPage(BookingInfo bookingInfo) throws PageNotCreatedException, WrongPageException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        viewYourBookingPage = homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);
        LocalDateTime arrivalTime = LocalDateTime.parse(viewYourBookingPage.getArrivalTime(), formatter);
        LocalDateTime departureTime = LocalDateTime.parse(viewYourBookingPage.getDepartureTime(), formatter);

        return arrivalTime.isAfter(departureTime);
    }

    public boolean isMatchFlyingFrom(BookingInfo bookingInfo) throws PageNotCreatedException, WrongPageException {

        viewYourBookingPage = homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);
        return (viewYourBookingPage.getFlyingFrom().equals(bookingInfo.getFlyingFrom()));
    }

    public boolean isMatchFlyingTo(BookingInfo bookingInfo) throws PageNotCreatedException, WrongPageException {

        viewYourBookingPage = homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);
        return (viewYourBookingPage.getFlyingTo().equals(bookingInfo.getFlyingTo()));
    }

    public boolean isPaymentAmountEqualToTotalPrice(BookingInfo bookingInfo) throws PageNotCreatedException, WrongPageException {
        bookingDetailsPage = homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo).openBookingDetails();

        return bookingDetailsPage.getTotalPaymentValue().equals(bookingDetailsPage.getTotalAmountValue());
    }

}
