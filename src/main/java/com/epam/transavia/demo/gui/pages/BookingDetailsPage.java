package com.epam.transavia.demo.gui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingDetailsPage extends CommonPage{

    private WebDriver driver;
    private static final String BOOKING_DETAILS_PAGE_TITLE = "BookingInfo details";
    static Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//div[contains(@class, 'section--green')]//div[@class='front']") private WebElement totalAmountContainer;
    @FindBy(xpath = "//h2[contains(text(), 'Transaction')]//..//div[@class='amount']") private WebElement totalPaymentContainer;

    public BookingDetailsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

     public String getTotalAmountValue(){

      scrollToElement( totalAmountContainer);
      logger.info("TotalAmount fetching...");
       return totalAmountContainer.getText();

    }

    public String getTotalPaymentValue(){

        scrollToElement(totalPaymentContainer);
        logger.info("TotalPayment fetching...");
        return totalPaymentContainer.getText();

    }

    public boolean isPaymentAmountEqualToTotalPrice(){
        return this.getTotalPaymentValue().equals(this.getTotalAmountValue());
    }

    public boolean isMatchPageTitle() {
        return driver.getTitle().equals(BOOKING_DETAILS_PAGE_TITLE);
    }
}
