import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BookingPage {

    private WebDriver driver;
   // private WebDriverWait wait = new WebDriverWait(driver, 5);

    static Logger logger = LogManager.getLogger();
    private final String bookingPageTitle = "Book a flight";

    @FindBy(xpath = "//ol//div[(contains(@class, 'day'))]")    private List<WebElement> allDatesIcons;
    @FindBy(xpath = "//ol//span[@class='price']")    private List<WebElement> allDatesWithFlights;
    @FindBy(xpath = "//div[(contains(@class, 'notification-error'))]")    private WebElement unsupportedFlightDestinationError;

    @FindBy(xpath = "//input[contains(@value, 'OutboundFlight')]//ancestor::form//span[@class='price']") private List<WebElement> outboundFlights;
    @FindBy(xpath = "//input[contains(@value, 'InboundFlight')]//ancestor::form//span[@class='price']") private List<WebElement> inboundFlights;

    @FindBy(xpath = "//section[@class='flight inbound']//button[@class='flight-result-button']") private WebElement selectInboundFlightBtn;
    @FindBy(xpath = "//section[@class='flight outbound']//button[@class='flight-result-button']") private WebElement selectOutboundFlightBtn;
    @FindBy(xpath = "//div[@class='panel panel--rounded-group']") private List<WebElement> selectedFlightsBluePanels;

    @FindBy(xpath = "//div[@class='back']") private WebElement totalPriceSection; //getTex() &742

    @FindBy(xpath = "//div[contains(@class, 'container--inbound')]//span[text()='Ticket price per person']//..//span[@class='price']")
    private WebElement priceInboundPerAdultContainer; //getAttribute("innerHTML") then trim(), lastindexof(">") and substring(index)

    @FindBy(xpath = "//div[contains(@class, 'container--outbound')]//span[text()='Ticket price per person']//..//span[@class='price']")
    private WebElement priceOutboundPerAdultContainer;

    @FindBy(xpath = "//div[contains(@class, 'container--inbound')]//span[contains(., 'Price for a baby')]//..//span[@class='price']")
    private WebElement priceInboundPerBabyContainer;
    @FindBy(xpath = "//div[contains(@class, 'container--outbound')]//span[contains(., 'Price for a baby')]//..//span[@class='price']")
    private WebElement priceOutboundPerBabyContainer;

    @FindBy(xpath = "//footer//*[@name='next_button']") private WebElement nextToClassSelectionButton;
    @FindBy(xpath = "//tr//button[@value = 'B' and text()='Select']") private WebElement selectBClassBtn; //20kg luggage
    @FindBy(xpath = "//th/span[@class = 'name' and text()='Plus']//following-sibling::span") private WebElement additionalPriceForBClassContainer; //getText() "+$48" euro sign char code 8364



    public BookingPage(WebDriver driver) throws WrongPageException {
        this.driver = driver;
        if (!bookingPageTitle.equals(driver.getTitle())) {
            throw new WrongPageException("Not a Booking page is opened: title " + driver.getTitle() + " does not meet to the expected " + bookingPageTitle);
        }
        PageFactory.initElements(driver, this);
    }

    public int getSelectedFlightsCount(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElements(selectedFlightsBluePanels));
        return selectedFlightsBluePanels.size();
    }
    public void selectOutboundFlight(int flightNumber){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        if(flightNumber-1>=0){
            if(!outboundFlights.isEmpty() && outboundFlights.size()>= (flightNumber-1)){
                outboundFlights.get(flightNumber-1).click();
                wait.until(ExpectedConditions.elementToBeClickable(selectOutboundFlightBtn)).click();
            }
            else logger.error("Cannot select the specified $s outbound flight.", flightNumber-1);
        } else logger.error("Invalid negative index is specified flights webelement list.");
    }
       public void selectInboundFlight(int flightNumber){
           WebDriverWait wait = new WebDriverWait(driver, 5);
           if(flightNumber-1>=0){
               if(!inboundFlights.isEmpty() && inboundFlights.size()>= (flightNumber-1)){
                   inboundFlights.get(flightNumber-1).click();
                   wait.until(ExpectedConditions.elementToBeClickable(selectInboundFlightBtn)).click();
               } else logger.error("Cannot select the specified $s inbound flight.", flightNumber-1);
           } else logger.error("Invalid negative index is specified for flights webelement list.");
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




}
