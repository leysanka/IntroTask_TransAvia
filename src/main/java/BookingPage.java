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

    static Logger logger = LogManager.getLogger();
    private final String bookingPageTitle = "Book a flight";

    @FindBy(xpath = "//ol[@class = 'HV-gc bulletless days']//div[(contains(@class, 'day'))]")
    private List<WebElement> allDatesIcons;

    @FindBy(xpath = "//ol[@class = 'HV-gc bulletless days']//span[@class='price']")
    private List<WebElement> datesWithFlights;

    @FindBy(xpath = "//div[(contains(@class, 'notification-error'))]")
    private WebElement unsupportedFlightDestinationError;


    public BookingPage(WebDriver driver) throws WrongPageException {
        this.driver = driver;
        if (!bookingPageTitle.equals(driver.getTitle())) {
            throw new WrongPageException("Not a Booking page is opened: title " + driver.getTitle() + " does not meet to the expected " + bookingPageTitle);
        }
        PageFactory.initElements(driver, this);
    }


    public int getFoundFlightsCount(){
       return datesWithFlights.size();
    }

    public boolean isFlightsFounds(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElements(allDatesIcons));
        return !datesWithFlights.isEmpty();
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
