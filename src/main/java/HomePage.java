import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;


public class HomePage {

    private WebDriver driver;

    static Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//a[@href='/en-EU/home']")
    private WebElement welcomeOtherCountries;

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

    @FindBy(xpath = "//label[@for = 'dateSelection_IsReturnFlight']")
    private WebElement returnOnCheckBox;

    @FindBy (id = "dateSelection_OutboundDate-datepicker")
    private WebElement departOnDateField;

    @FindBy (id = "dateSelection_IsReturnFlight-datepicker")
    private WebElement returnOnDateField;

    @FindBy (id = "booking-passengers-input")
    private WebElement passengersField;

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

    public void selectOtherCountriesLocale(){
        welcomeOtherCountries.click();
    }

    public boolean whereToGoWindowIsDisplayed(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(whereToGoWindow));
        return whereToGoWindow.isDisplayed();
    }

    public void fromFieldActivate() {       fromField.click();    }
    public void toFieldActivate(){        toField.click();    }
    public String getDestinationItemText(int item){
       return dropdownDestinationValuesFrom.get(item).getText();
    }

    public void setFromDestinationByKeys(String airport){
        fromField.sendKeys(airport);
    }
    public void setToDestinationByKeys(String airport){
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
    ///Not working because isSelected does not return rpoper result, always False.
    // Need to use ::after and ::before marks from verstka, don't know how to identify them
    public boolean returnOnCheckBoxState(){
        return returnOnCheckBox.isSelected();
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

    public void returnOnCheckBoxClick() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(returnOnCheckBox)).click();
        Actions action = new Actions(driver);
        action.moveToElement(returnOnCheckBox).click();
    }

    protected static String calculateStartDate(long startDateLag){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String startDate = date.plusDays(startDateLag).format(formatter);
        return startDate.toString();
    }

    public void setDepartOnDateField(long startDateLag){

        departOnDateField.clear();
        departOnDateField.sendKeys(calculateStartDate(startDateLag).toString());
    }

    public String getSelectedDepartOnDate(){
        return departOnDateField.getAttribute("value");
    }

    public boolean isCorrectPassengersCountShown(String passengersNumber){
        return passengersField.getAttribute("value").startsWith(passengersNumber);
    }

    public void searchBtnSubmit(){
        searchBtn.click();
    }

}
