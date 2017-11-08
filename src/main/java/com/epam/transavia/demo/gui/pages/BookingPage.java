package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BookingPage extends CommonPage {

    private final String BOOKING_PAGE_TITLE = "Book a flight";

    @FindBy(xpath = "//ol//div[(contains(@class, 'day'))]")
    private List<WebElement> allDatesIcons;
    @FindBy(xpath = "//ol//span[@class='price']")
    private List<WebElement> allDatesWithFlights;
    @FindBy(xpath = "//div[(contains(@class, 'notification-error'))]")
    private WebElement searchFlightError;
    @FindBy(xpath = "//input[contains(@value, 'OutboundFlight')]//ancestor::form//span[@class='price']")
    private List<WebElement> outboundFlights;
    @FindBy(xpath = "//input[contains(@value, 'InboundFlight')]//ancestor::form//span[@class='price']")
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

    public BookingPage(WebDriver driver) throws WrongPageException {
        super(driver);
        if (!BOOKING_PAGE_TITLE.equals(driver.getTitle())) {
            throw new WrongPageException("Not a Booking page is opened: title " + driver.getTitle() + " does not meet to the expected " + BOOKING_PAGE_TITLE);
        }
    }

    public void selectOutboundFlight(int flightNumber) {
        int index = flightNumber - 1;

        if (flightNumber > 0) {
            if (!outboundFlights.isEmpty() && outboundFlights.size() >= flightNumber) {
                outboundFlights.get(index).click();
                logger.info(flightNumber + " Outbound flight chosen successfully.");
            } else logger.error("Cannot select the specified outbound flight by order: " + flightNumber);
        } else logger.error("Invalid negative index is specified for flights' webelement list.");
    }

    public void selectInboundFlight(int flightNumber) {
        int index = flightNumber - 1;

        if (flightNumber > 0) {
            if (!inboundFlights.isEmpty() && inboundFlights.size() >= flightNumber) {
                inboundFlights.get(index).click();
                logger.info(flightNumber + " Inbound flight chosen successfully.");
            } else logger.error("Cannot select the specified inbound flight by order: " + flightNumber);
        } else logger.error("Invalid negative index is specified for flights' webelement list.");
    }

    public void pressSelectOutboundFlight() {

        waitForLoadingIsFinished();
        scrollToElement(selectOutboundFlightBtn);
        waitForElementIsClickable(selectOutboundFlightBtn);
        selectOutboundFlightBtn.click();
        logger.info("Outbound flight selected successfully.");
    }

    public double getPlusFarePrice() {
        int euroSignCode = 8364;
        String text = pricePlusFareContainer.getText(); //here text like "+$48" comes
        int index = text.lastIndexOf(euroSignCode);
        if (index >= 0 && (index + 1) < text.length()) {
            return Double.valueOf(text.substring(index + 1).replaceAll(",", ""));
        } else {
            throw new IndexOutOfBoundsException("Unexpected index for PlusFare text with price.");
        }
    }

    public void pressNextButton() {
        waitForElementIsClickable(nextToFlightFareSelectionButton);
        if (nextToFlightFareSelectionButton.isDisplayed()) {
            nextToFlightFareSelectionButton.click();
        }
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

    public void pressSelectInboundFlight() {

        waitForLoadingIsFinished();
        scrollToElement(selectInboundFlightBtn);
        waitForElementIsClickable(selectInboundFlightBtn);
        selectInboundFlightBtn.click();
        logger.info("Inbound flight SELECTED successfully.");
    }

    public int getFoundFlightsCount() {
        return allDatesWithFlights.size();
    }

    public boolean isFlightsFounds() {
        waitForElementsAreVisible(allDatesIcons);
        return !allDatesWithFlights.isEmpty();
    }

    public int getAllDatesShownCount() {
        waitForElementsAreVisible(allDatesIcons);
        return allDatesIcons.size();
    }

    //TODO Should be removed after refactoring
    public boolean isUnsupportedFlightErrorShown(String expError) {

        return searchFlightError.isDisplayed() &&
                searchFlightError.getText().equals(expError);
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
}
