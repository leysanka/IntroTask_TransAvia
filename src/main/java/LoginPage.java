import org.apache.logging.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {

    private WebDriver driver;
    static Logger logger = LogManager.getLogger();

    private final String LOGIN_PAGE_TITLE = "Log in";

    //View your booking without an account
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_RecordLocator']") private WebElement bookingNumberField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_LastName']") private WebElement lastNameField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_FlightDate-datepicker']") private WebElement flightDateField;
    @FindBy(xpath = "//button [text()='View booking']") private WebElement viewBookingBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isMatchPageTitle(){
        return driver.getTitle().equals(LOGIN_PAGE_TITLE);
    }

    public void setBookingNumberField(String bookingNumber){
        bookingNumberField.sendKeys(bookingNumber);
    }

    public boolean isMatchBookingNumberField(String bookingNumber){
        return bookingNumberField.getAttribute("value").equals(bookingNumber);
    }

    public void setLastNameFieldField(String lastName){
        lastNameField.sendKeys(lastName);
    }

    public boolean isMatchLastNameField(String lastName){
        return lastNameField.getAttribute("value").equals(lastName);
    }


    public void setFlightDateFieldField(String flightDate){
        flightDateField.sendKeys(flightDate);
    }

    public boolean isMatchFlightDateField(String flightDate){
        return flightDateField.getAttribute("value").equals(flightDate);
    }

    public void submitFlightDateAndGoToViewBooking(){
          flightDateField.sendKeys(Keys.ENTER);
    }

    public void pressViewBookingBtn(){
        viewBookingBtn.click();
    }





}
