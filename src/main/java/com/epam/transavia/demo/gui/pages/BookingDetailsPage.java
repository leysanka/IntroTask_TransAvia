package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BookingDetailsPage extends CommonPage {

    private static final String BOOKING_DETAILS_PAGE_TITLE = "Booking details";

    @FindBy(xpath = "//div[contains(@class, 'section--green')]//div[@class='front']") private WebElement totalAmountContainer;
    @FindBy(xpath = "//h2[contains(text(), 'Transaction')]//..//div[@class='amount']") private WebElement totalPaymentContainer;

    public BookingDetailsPage(WebDriver driver) throws WrongPageException {
        super(driver);
        if (!driver.getTitle().equals(BOOKING_DETAILS_PAGE_TITLE)) {
            throw new WrongPageException("Not a BookingDetailsInfo details page is opened: title " + driver.getTitle() + " does not meet to the expected " + BOOKING_DETAILS_PAGE_TITLE);
        }
    }

    public String getTotalAmountValue() {

        scrollToElement(totalAmountContainer);
        logger.info("TotalAmount fetching...");
        return totalAmountContainer.getText();

    }

    public String getTotalPaymentValue() {

        scrollToElement(totalPaymentContainer);
        logger.info("TotalPayment fetching...");
        return totalPaymentContainer.getText();

    }

}
