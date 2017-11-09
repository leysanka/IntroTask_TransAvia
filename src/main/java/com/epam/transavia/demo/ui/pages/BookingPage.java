package com.epam.transavia.demo.ui.pages;

import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BookingPage extends CommonPage {

    @FindBy(xpath = "//ol//div[(contains(@class, 'day'))]")
    private List<WebElement> allDatesIcons;
    @FindBy(xpath = "//ol//span[@class='price']")
    private List<WebElement> allDatesWithFlights;
    @FindBy(xpath = "//div[(contains(@class, 'notification-error'))]")
    private WebElement searchFlightError;
    //  @FindBy(xpath = "//input[contains(@value, 'OutboundFlight')]//ancestor::form//span[@class='price']")
    @FindBy(xpath = "//input[contains(@value, 'OutboundFlight')]//ancestor::form//div[contains(@class, 'day-with-availability')]")
    private List<WebElement> outboundFlights;
    //  @FindBy(xpath = "//input[contains(@value, 'InboundFlight')]//ancestor::form//span[@class='price']")
    @FindBy(xpath = "//input[contains(@value, 'InboundFlight')]//ancestor::form//div[contains(@class, 'day-with-availability')]")
    private List<WebElement> inboundFlights;
    @FindBy(xpath = "//section[@class='flight inbound']//div[@class='panel flight-result active']")
    private WebElement selectInboundFlightBtn;
    @FindBy(xpath = "//section[@class='flight outbound']//div[@class='panel flight-result active']")
    private WebElement selectOutboundFlightBtn;
    @FindBy(xpath = "//div[@class='panel panel--rounded-group']")
    private List<WebElement> selectedFlightsBluePanels;
    //_________Prices______________
    @FindBy(xpath = "//div[@class='back']")
    private WebElement totalPriceSection; //getTex() &742
    @FindBy(xpath = "//th/span[@class = 'name' and text()='Plus']//following-sibling::span")
    private WebElement pricePlusFareContainer;
    // ___________Adult passengers prices____________
    @FindBy(xpath = "//span[text()='Ticket price per person']//..//span[@class='price']")
    private List<WebElement> pricesPerAdultContainer;
    @FindBy(xpath = "//div[contains(@class, 'container--inbound')]//span[text()='Ticket price per person']//..//span[@class='price']")
    private WebElement priceInboundPerAdultContainer;
    @FindBy(xpath = "//div[contains(@class, 'container--outbound')]//span[text()='Ticket price per person']//..//span[@class='price']")
    private WebElement priceOutboundPerAdultContainer;
    //___________Baby passengers prices____________
    @FindBy(xpath = "//span[contains(., 'Price for a baby')]//..//span[@class='price']")
    private List<WebElement> pricesPerBabyContainer;
    @FindBy(xpath = "//div[contains(@class, 'container--inbound')]//span[contains(., 'Price for a baby')]//..//span[@class='price']")
    private WebElement priceInboundPerBabyContainer;
    @FindBy(xpath = "//div[contains(@class, 'container--outbound')]//span[contains(., 'Price for a baby')]//..//span[@class='price']")
    private WebElement priceOutboundPerBabyContainer;

    @FindBy(xpath = "//footer//*[@name='next_button']")
    private WebElement nextToFlightFareSelectionButton;
    @FindBy(xpath = "//tr//button[@value = 'B' and text()='Select']")
    private WebElement selectPlusFareBtn; //+20kg luggage


    public BookingPage(WebDriver driver) {
        super(driver);
        if (!driver.getCurrentUrl().contains("book-a-flight")) {
            throw new WrongPageException("Not a Booking page is opened: url " + driver.getCurrentUrl() + " does not contain \"book-a-flight\" part. ");
        }
    }

    public double getPlusFarePrice() {
        int euroSignCode = 8364;
        String text = pricePlusFareContainer.getText(); //here text like "+$48" comes
        int index = text.lastIndexOf(euroSignCode);
        if (index >= 0 && (index + 1) < text.length()) {
            logger.info("Plus fare price equals: " + text);
            return Double.valueOf(text.substring(index + 1).replaceAll(",", ""));
        } else {
            throw new IndexOutOfBoundsException("Unexpected index for PlusFare text with price.");
        }
    }

    public void pressNextButton() {
        waitForLoadingIsFinished();
        waitForElementIsClickable(nextToFlightFareSelectionButton);
        nextToFlightFareSelectionButton.click();

    }

    public void pressSelectPlusFareBtn() {
        waitForElementIsClickable(selectPlusFareBtn);
        if (selectPlusFareBtn.isEnabled()) {
            scrollToElement(selectPlusFareBtn);
            selectPlusFareBtn.click();
        }
    }

    public int getSelectedFlightsCount() {
        waitForLoadingIsFinished();
        logger.info("Selected flights blue spinners count: " + selectedFlightsBluePanels.size());
        return selectedFlightsBluePanels.size();
    }

    private void pressSelectFlightButton(WebElement button) {
        waitForLoadingIsFinished();
        waitForElementIsVisible(button);
        scrollToElement(button);
        waitForElementIsClickable(button);
        button.click();
    }

    public void pressSelectInboundFlight() {

        pressSelectFlightButton(selectInboundFlightBtn);
        logger.info("Inbound flight selected successfully.");
    }

    public void pressSelectOutboundFlight() {

        pressSelectFlightButton(selectOutboundFlightBtn);
        logger.info("Outbound flight selected successfully.");
    }

    public int getFoundFlightsCount() {
        return allDatesWithFlights.size();
    }

    public int getAllDatesShownCount() {
        waitForElementsAreVisible(allDatesIcons);
        return allDatesIcons.size();
    }

    public String getSearchFlightError() {
        if (isElementVisible(getBy("searchFlightError"))) {
            return searchFlightError.getText();
        } else {
            logger.error("The error message element is not visible.");
            return null;
        }
    }

    public double getTotalPricePerBaby() {
        waitForLoadingIsFinished();
        logger.info("Get Total price per baby started...");
        return this.getPriceFromInnerHTML(pricesPerBabyContainer);
    }

    public double getTotalPricePerAdultPassenger() {
        waitForLoadingIsFinished();
        logger.info("Get Total price per adult started...");
        return this.getPriceFromInnerHTML(pricesPerAdultContainer);
    }

    public double getPriceFromInnerHTML(List<WebElement> pricesContainer) {
        double totalPrice = 0;
        for (WebElement element :
                pricesContainer) {
            String text = element.getAttribute("innerHTML").trim();
            int index = text.lastIndexOf(">");
            if (index >= 0 && (index + 1) < text.length()) {
                String substring = text.substring(index + 1).replaceAll(",", "");
                totalPrice += Double.valueOf(substring);
            }
        }
        logger.info("Total price per passenger equals: " + totalPrice);
        return totalPrice;
    }

    public double getTotalAmountPrice() {
        waitForLoadingIsFinished();
        String text = totalPriceSection.getText();
        if (text.length() > 1) {
            logger.info("Total Amount shown in the BookingDetailsInfo page: " + text);
            return Double.valueOf(text.replaceAll(",", "").substring(1));
        } else
            throw new IndexOutOfBoundsException("Unexpected index for TotalAmount text with price.");
    }

    public List<WebElement> getOutboundFlightsFound() {
        if (!outboundFlights.isEmpty()) {
            return outboundFlights;
        } else {
            logger.error("Outbound flights are not found.");
            return null;
        }
    }

    public List<WebElement> getInboundFlightsFound() {
        if (!inboundFlights.isEmpty()) {
            return inboundFlights;
        } else {
            logger.error("Inbound flights are not found.");
            return null;
        }
    }


}
