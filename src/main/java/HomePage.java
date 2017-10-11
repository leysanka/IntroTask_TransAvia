import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class HomePage {

    WebDriver driver;

    public static Logger logger = LogManager.getLogger();

    @FindBy (xpath = "//form[@id = 'desktop']/parent::section")
    private WebElement whereToGoWindow;

    @FindBy(id = "routeSelection_DepartureStation-input")
    private WebElement fromField;

    @FindBy(id="routeSelection_ArrivalStation-input")
    private WebElement toField;

    /*If drop-down is not opened, the attribute "style="display: none;" is set; if it's opened -no such attribute is set.*/
    @FindBy(xpath = "//div[@class = 'autocomplete-results' and not(@style)]")
    private WebElement destinationDropDown;

    @FindBy(xpath = "//ol[@class='results']/li")
    private List<WebElement> dropdownDestinationValuesFrom;

    @FindBy(xpath = "//ol[@class='results']//ol/li")
    private List<WebElement> dropdownDestinationValuesTo;

    @FindBy(xpath = "//div[@class='checkfield-wrapper']/label[@for = 'dateSelection_IsReturnFlight']")
    private WebElement returnOnCheckBox;

    @FindBy (id = "dateSelection_OutboundDate-datepicker")
    protected WebElement departOnDateField;

    @FindBy (id = "dateSelection_IsReturnFlight-datepicker")
    private WebElement returnOnDateField;

    @FindBy (id = "booking-passengers-input")
    private WebElement passengersField;   //<div style="position: absolute; z-index: -1; visibility: hidden;">3 Adults</div>

    @FindBy (xpath = "//div[@class='selectfield adults']//button[@class='button button-secondary increase']")
    private WebElement adultsPlusBtn;

    @FindBy(className = "button button-secondary close")
    private WebElement savePassengersBtn;

    @FindBy (xpath = "//form[@id='desktop']//button[@class = 'button button-primary']")
    private WebElement searchBtn;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean whereToGoWindowIsDisplayed(){
      return whereToGoWindow.isDisplayed();
    }

    public void fromFieldActivate(){
        fromField.click();
    }
    public void toFieldActivate(){
        toField.click();
    }

 /*   public String getDestinationItemText(int item){
       return dropdownDestinationValues.get(item).getText();
    }*/

    public void setFromDestinationByText (String airport){
        fromField.sendKeys(airport);
    }
    public void setToDestinationByText (String airport){
       toField.sendKeys(airport);
    }

    public boolean isMatchFromDestinationFieldText(String airportValue){
        return fromField.getAttribute("value").equals(airportValue);
    }
    public boolean isMatchToDestinationFieldText(String airportValue){
        return toField.getAttribute("value").equals(airportValue);
    }

    public void selectDestinationFromDropDown(int item){
        if (destinationDropDown.isDisplayed()) {
            try {
            dropdownDestinationValuesFrom.get(item).click();}
            catch  (IndexOutOfBoundsException e) {
                e.printStackTrace();
                logger.error("Invalid index specified for the destination airports drop-down values");
            }
        }
    }

    public int getFromDestinationsCount(){
       return dropdownDestinationValuesFrom.size();
    }

    public void checkReturnOnCheckBox(){
        if (!returnOnCheckBox.isSelected()) {
            returnOnCheckBox.click();
        }
    }
    public void uncheckReturnOnCheckBox(){
        if (returnOnCheckBox.isSelected()) {
            returnOnCheckBox.click();
        }
    }

    public void submitSearchBtn(){
        searchBtn.click();
    }

}
