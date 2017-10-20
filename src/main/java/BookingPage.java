import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BookingPage {

    private WebDriver driver;
    private final String bookingPageTitle = "Book a flight";

    static Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//ol//div[(contains(@class, 'day'))]")    private List<WebElement> allDatesIcons;
    @FindBy(xpath = "//ol//span[@class='price']")    private List<WebElement> allDatesWithFlights;
    @FindBy(xpath = "//div[(contains(@class, 'notification-error'))]")    private WebElement unsupportedFlightDestinationError;
    @FindBy(xpath = "//input[contains(@value, 'OutboundFlight')]//ancestor::form//span[@class='price']") private List<WebElement> outboundFlights;
    @FindBy(xpath = "//input[contains(@value, 'InboundFlight')]//ancestor::form//span[@class='price']") private List<WebElement> inboundFlights;
    @FindBy(xpath = "//section[@class='flight inbound']//div[@class='panel flight-result active']") private WebElement selectInboundFlightBtn;
    @FindBy(xpath = "//section[@class='flight outbound']//div[@class='panel flight-result active']") private WebElement selectOutboundFlightBtn;
    @FindBy(xpath = "//div[@class='panel panel--rounded-group']") private List<WebElement> selectedFlightsBluePanels;
//_________Prices______________
    @FindBy(xpath = "//div[@class='back']") private WebElement totalPriceSection; //getTex() &742
    @FindBy(xpath = "//th/span[@class = 'name' and text()='Plus']//following-sibling::span") private WebElement pricePlusFareContainer;
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

    @FindBy(xpath = "//footer//*[@name='next_button']") private WebElement nextToFlightFareSelectionButton;
    @FindBy(xpath = "//tr//button[@value = 'B' and text()='Select']") private WebElement selectPlusFareBtn; //+20kg luggage



    public BookingPage(WebDriver driver) throws WrongPageException {
        this.driver = driver;
        if (!bookingPageTitle.equals(driver.getTitle())) {
            throw new WrongPageException("Not a Booking page is opened: title " + driver.getTitle() + " does not meet to the expected " + bookingPageTitle);
        }
        PageFactory.initElements(driver, this);
    }

    public double getPlusFarePrice(){
        int euroSignCode = 8364;
        String text = pricePlusFareContainer.getText(); //here text like "+$48" comes
        int index = text.lastIndexOf(euroSignCode);
        if (index >= 0 && (index+1)<text.length()) {
            return Double.valueOf(text.substring(index + 1).replaceAll(",", ""));
        } else {
            throw new IndexOutOfBoundsException("Unexpected index for PlusFare text with price.");
        }
    }

    public void pressNextButton(){
        this.waitForElementIsClickable(nextToFlightFareSelectionButton);
        if(nextToFlightFareSelectionButton.isDisplayed()){
        nextToFlightFareSelectionButton.click();
        }
    }

    public void pressSelectPlusFareBtn(){
        this.waitForElementIsClickable(selectPlusFareBtn);
        if(selectPlusFareBtn.isEnabled()){
            this.scrollToElement(selectPlusFareBtn);
            selectPlusFareBtn.click();
        }
    }

    public int getSelectedFlightsCount(){
        this.waitForLoadingIsFinished();
        logger.info("Selected flights blue spinners count: " + selectedFlightsBluePanels.size());
        return selectedFlightsBluePanels.size();
    }

    public void selectOutboundFlight(int flightNumber){
        int index = flightNumber-1;

        if(flightNumber > 0){
            if(!outboundFlights.isEmpty() && outboundFlights.size()>= flightNumber){
                   outboundFlights.get(index).click();
                   logger.info(flightNumber + " Outbound flight chosen successfully.");
            }
            else logger.error("Cannot select the specified outbound flight by order: " + flightNumber);
        } else logger.error("Invalid negative index is specified for flights' webelement list.");
    }

       public void selectInboundFlight(int flightNumber){
           int index = flightNumber-1;

           if(flightNumber > 0){
               if(!inboundFlights.isEmpty() && inboundFlights.size() >= flightNumber){
                   inboundFlights.get(index).click();
                   logger.info(flightNumber+ " Inbound flight chosen successfully.");
                 } else logger.error("Cannot select the specified inbound flight by order: " +flightNumber );
           } else logger.error("Invalid negative index is specified for flights' webelement list.");
       }

    public void pressSelectOutboundFlight(){

        this.waitForLoadingIsFinished();
        this.scrollToElement(selectOutboundFlightBtn);
        this.waitForElementIsClickable(selectOutboundFlightBtn);
        selectOutboundFlightBtn.click();
        logger.info("Outbound flight SELECTED successfully.");
    }

    public void pressSelectInboundFlight(){

        this.waitForLoadingIsFinished();
        this.scrollToElement(selectInboundFlightBtn);
        this.waitForElementIsClickable(selectInboundFlightBtn);
        selectInboundFlightBtn.click();
        logger.info("Inbound flight SELECTED successfully.");
    }

    public void waitForLoadingIsFinished(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0;"));
    }

    public void waitForElementIsClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void scrollToElement(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public int getFoundFlightsCount(){
       return allDatesWithFlights.size();
    }

    public boolean isFlightsFounds(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElements(allDatesIcons));
        return !allDatesWithFlights.isEmpty();
    }

    public int getAllDatesShownCount(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElements(allDatesIcons));
        return allDatesIcons.size();
    }

    public boolean isUnsupportedFlightErrorShown (String expError){

        return unsupportedFlightDestinationError.isDisplayed() &&
                unsupportedFlightDestinationError.getText().equals(expError);
    }


    public double getTotalPricePerBaby() {
        this.waitForLoadingIsFinished();
        logger.info("Get Total price per baby started...");
        return this.getPriceFromInnerHTML(pricesPerBabyContainer);
    }

    public double getTotalPricePerAdultPassenger() {
        this.waitForLoadingIsFinished();
        logger.info("Get Total price per adult started...");
        return this.getPriceFromInnerHTML(pricesPerAdultContainer);
    }

    public double getPriceFromInnerHTML(List<WebElement> pricesContainer){
        double totalPrice = 0;
        for (WebElement element :
                pricesContainer) {
            String text = element.getAttribute("innerHTML").trim();
            int index = text.lastIndexOf(">");
            if (index >= 0 && (index+1)<text.length()){
            String substring = text.substring(index+1).replaceAll(",", "");
            totalPrice+= Double.valueOf(substring);
            }
        }
        logger.info("Total price per passenger equals: " + totalPrice);
        return totalPrice;
    }

    public double getTotalAmountPrice(){
        this.waitForLoadingIsFinished();
        String text = totalPriceSection.getText();
        if (text.length() > 1) {
            logger.info("Total Amount shown in the Booking page: ");
            return Double.valueOf(text.replaceAll(",", "").substring(1));
        } else
            throw new IndexOutOfBoundsException("Unexpected index for TotalAmount text with price.");
    }
}
