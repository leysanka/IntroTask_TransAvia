package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.core.exceptions.InvalidTestDataException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class HomePage {

    private WebDriver driver;

    static Logger logger = LogManager.getLogger();
    //static Logger testLogger = LogManager.getLogger("test");

    private static final String HOME_PAGE_TITLE = "Welcome to Transavia!";
    private static final int MAX_PASSENGERS_TO_FILL = 10;

    //Better to use this for checking whether the Home, Booking etc. pages were loaded.
    //@FindBy(xpath = "//span[@class = 'icon-font icon-house']") WebElement houseBtn;

    @FindBy(xpath = "//a[@href='/en-EU/home']")    private WebElement welcomeOtherCountries;
    @FindBy (xpath = "//form[@id = 'desktop']/parent::section")    private WebElement whereToGoWindow;
    @FindBy(id = "routeSelection_DepartureStation-input")    private WebElement fromField;
    @FindBy(id="routeSelection_ArrivalStation-input")    private WebElement toField;
    /*If drop-down is not opened, the attribute "style="display: none;" is set; if it's opened -no such attribute is set.*/
    @FindBy(xpath = "//div[@class = 'autocomplete-results' and not(@style)]")  private WebElement destinationDropDown;
    @FindBy(xpath = "//ol[@class='results']/li") private List<WebElement> dropdownDestinationValuesFrom;
    @FindBy(xpath = "//ol[@class='results']//ol/li")  private List<WebElement> dropdownDestinationValuesTo;
    @FindBy(xpath = "//label[@for = 'dateSelection_IsReturnFlight']")  private WebElement returnOnCheckBox;
    @FindBy(css = "input#dateSelection_IsReturnFlight") private WebElement returnOnCheckBoxCssState;
    @FindBy (id = "dateSelection_OutboundDate-datepicker")    private WebElement departOnDateField;
    @FindBy (id = "dateSelection_IsReturnFlight-datepicker")    private WebElement returnOnDateField;

    @FindBy (id = "booking-passengers-input")    private WebElement passengersField;

    @FindBy(className = "passengers") private WebElement passengersPopUp;
    @FindBy (xpath = "//div[@class='selectfield adults']//button[contains(@class,'increase')]")    private WebElement adultsIncreaseBtn;
    @FindBy (xpath = "//div[@class='selectfield adults']//button[contains(@class,'decrease')]")    private WebElement adultsDecreaseBtn;
    @FindBy (xpath = "//div[@class='selectfield adults']//input")    private WebElement adultsCountBox;

    @FindBy (xpath = "//div[@class='selectfield children']//button[contains(@class,'increase')]")    private WebElement childrenIncreaseBtn;
    @FindBy (xpath = "//div[@class='selectfield children']//button[contains(@class,'decrease')]")    private WebElement childrenDecreaseBtn;
    @FindBy (xpath = "//div[@class='selectfield children']//input")    private WebElement childrenCountBox;

    @FindBy (xpath = "//div[@class='selectfield babies']//button[contains(@class,'increase')]")    private WebElement babiesIncreaseBtn;
    @FindBy (xpath = "//div[@class='selectfield babies']//button[contains(@class,'decrease')]")    private WebElement babiesDecreaseBtn;
    @FindBy (xpath = "//div[@class='selectfield babies']//input")    private WebElement babiesCountBox;

    @FindBy(xpath = "//button[contains(.,'Save')]") private WebElement savePassengersBtn;
    @FindBy (xpath = "//form[@id='desktop']//button[@class = 'button button-primary']")    private WebElement searchBtn;

    @FindBy(xpath = "//li[@class = 'primary-navigation_item']/a[contains(@href, 'booking-overview')]") private WebElement manageYourBookingBtn;
    @FindBy(xpath = "//div[@id='horizontal-sub-navigation-manageyourbooking']//div[contains(@class, 'togglepanel-content')]") private WebElement manageYourBookingPanel;
    @FindBy(xpath = "//li//span[contains(@class, 'icon-account')]") private WebElement viewYourBookingIcon;


    public HomePage(WebDriver driver) {
        this.driver = driver;

        if(HOME_PAGE_TITLE.equals(driver.getTitle())) {
            PageFactory.initElements(driver, this);
            logger.info("HomePage initialized successfully.");
        }
        else try {
            throw new WrongPageException("HomePage title does not meet to the expected \"" + HOME_PAGE_TITLE + "\". Or page is not loaded.");
        } catch (WrongPageException e) {
            logger.error(e.getMessage());
        }
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

    //State can be verified with css selector, xpath does not work for this:
    // attribute "checked" is "true" when checked and !!!NULL when not checked.
    public boolean returnOnIsChecked(){
        try {
            if(returnOnCheckBoxCssState.getAttribute("checked").equals("true")){
                return true;        }
        }
        catch (NullPointerException e) {
            logger.info("Return On checkbox 'Checked' attribute is null, ie checkbox is unchecked.");
            return false;
        }
        return false;
    }
    public void checkReturnOnCheckBox(){
        if (!returnOnIsChecked()) {
            returnOnCheckBox.click();
        }
    }
    public void uncheckReturnOnCheckBox(){
        if (returnOnIsChecked()) {
            returnOnCheckBox.click();
        }
    }

    public void returnOnCheckBoxClick() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(returnOnCheckBox)).click();
        Actions action = new Actions(driver);
        action.moveToElement(returnOnCheckBox).click();
    }

    public static String calculateDateNowPlusLag(long lagDays){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String startDate = date.plusDays(lagDays).format(formatter);
        return startDate.toString();
    }

    public void setDepartOnDateFieldPlusLag(long lagDays){

        departOnDateField.clear();
        departOnDateField.sendKeys(calculateDateNowPlusLag(lagDays).toString());
    }

    public void setReturnOnDateFieldPlusLag(long lagDays){

        returnOnDateField.clear();
        returnOnDateField.sendKeys(calculateDateNowPlusLag(lagDays).toString());
    }
    public boolean returnOnDateIsSet(){
        return !getSelectedReturnOnDate().isEmpty();
    }

    public String getSelectedDepartOnDate(){
        return departOnDateField.getAttribute("value");
    }

    public String getSelectedReturnOnDate(){
        return returnOnDateField.getAttribute("value");
    }

    public boolean isCorrectPassengersCountShown(String expPassengers){
        return passengersField.getAttribute("value").contains(expPassengers);
    }

    public void passengersFieldClick(){
        passengersField.click();
    }

    public boolean isVisiblePassengersPopup(){
        return passengersPopUp.isDisplayed();
    }
//TODO may be can be done as one method for any passenger add child, baby, adult-> need to think how
    public void addAdultPassengers(int adultsCount) throws InvalidTestDataException {
        if (adultsCount>=0 && adultsCount <= MAX_PASSENGERS_TO_FILL){
            for (int i = 0; i <adultsCount ; i++) {
                if(adultsIncreaseBtn.isEnabled()){
                    adultsIncreaseBtn.click();
                    logger.info((i+1) + " passenger added.");
                    passengersField.click();
                } else break;
            }
        } else {
            throw new InvalidTestDataException("Invalid input for adults count to add, or adultsIncrease button is not enabled.");
        }
    }

    public void addChildrenPassenger(){
        if (childrenIncreaseBtn.isEnabled()){
            childrenIncreaseBtn.click();
            passengersField.click();
        }
    }

    public void addBabyPassenger(){
        if(babiesIncreaseBtn.isEnabled()){
            babiesIncreaseBtn.click();
            passengersField.click();
        }
    }

    public String getPassengersCountBoxValue(String type)throws IllegalArgumentException {

        passengersTypes pt = passengersTypes.valueOf(type.toUpperCase()); //throws exc here

        switch (pt){
            case ADULTS:
                return adultsCountBox.getAttribute("value");
            case CHILDREN:
                return childrenCountBox.getAttribute("value");
            case BABIES:
                return babiesCountBox.getAttribute("value");
            default:
                throw new IllegalArgumentException(); //doesn't get there
                //throw new com.epam.transavia.demo.core.exceptions.InvalidTestDataException(pt + " is unknown type of passenger. Please specify one of the valid: " + passengersTypes.values());
        }
    }

    public enum passengersTypes{
        ADULTS,
        CHILDREN,
        BABIES
    }

    public void searchBtnSubmit(){
        searchBtn.click();
    }

    //ManageBookingSection

    public void openManageBookingToolbar(){
        if (manageYourBookingBtn.isDisplayed()) {
            manageYourBookingBtn.click();
        }
    }

    public boolean manageBookingPaneIsOpened(){
        return manageYourBookingPanel.isDisplayed();
    }

    public void goToViewBooking(){
       if(viewYourBookingIcon.isDisplayed()){
           viewYourBookingIcon.click();
       }
    }

}
