package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.business_objects.PassengersTypes;
import com.epam.transavia.demo.core.exceptions.InvalidTestDataException;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import com.epam.transavia.demo.util.DateTimeHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;


public class HomePage extends CommonPage {

    private static final int MAX_PASSENGERS_TO_FILL = 10;
    private static final String homePageEnEuUrl = "https://www.transavia.com/en-EU/home/";

    @FindBy(xpath = "//a[@href='/en-EU/home']")
    private WebElement welcomeOtherCountries;
    @FindBy(xpath = "//form[@id = 'desktop']/parent::section")
    private WebElement whereToGoWindow;
    @FindBy(id = "routeSelection_DepartureStation-input")
    private WebElement fromField;
    @FindBy(id = "routeSelection_ArrivalStation-input")
    private WebElement toField;
    @FindBy(xpath = "//div[@class = 'autocomplete-results' and not(@style)]")
    private WebElement destinationDropDown;
    @FindBy(xpath = "//ol[@class='results']/li")
    private List<WebElement> dropdownDestinationValuesFrom;
    @FindBy(xpath = "//ol[@class='results']//ol/li")
    private List<WebElement> dropdownDestinationValuesTo;
    @FindBy(xpath = "//label[@for = 'dateSelection_IsReturnFlight']")
    private WebElement returnOnCheckBox;
    @FindBy(css = "input#dateSelection_IsReturnFlight")
    private WebElement returnOnCheckBoxCssState;
    @FindBy(id = "dateSelection_OutboundDate-datepicker")
    private WebElement departOnDateField;
    @FindBy(id = "dateSelection_IsReturnFlight-datepicker")
    private WebElement returnOnDateField;
    @FindBy(id = "booking-passengers-input")
    private WebElement passengersField;

    @FindBy(className = "passengers")
    private WebElement passengersPopUp;
    @FindBy(xpath = "//div[@class='selectfield adults']//button[contains(@class,'increase')]")
    private WebElement adultsIncreaseBtn;
    @FindBy(xpath = "//div[@class='selectfield adults']//button[contains(@class,'decrease')]")
    private WebElement adultsDecreaseBtn;
    @FindBy(xpath = "//div[@class='selectfield adults']//input")
    private WebElement adultsCountBox;

    @FindBy(xpath = "//div[@class='selectfield children']//button[contains(@class,'increase')]")
    private WebElement childrenIncreaseBtn;
    @FindBy(xpath = "//div[@class='selectfield children']//button[contains(@class,'decrease')]")
    private WebElement childrenDecreaseBtn;
    @FindBy(xpath = "//div[@class='selectfield children']//input")
    private WebElement childrenCountBox;

    @FindBy(xpath = "//div[@class='selectfield babies']//button[contains(@class,'increase')]")
    private WebElement babiesIncreaseBtn;
    @FindBy(xpath = "//div[@class='selectfield babies']//button[contains(@class,'decrease')]")
    private WebElement babiesDecreaseBtn;
    @FindBy(xpath = "//div[@class='selectfield babies']//input")
    private WebElement babiesCountBox;

    @FindBy(xpath = "//button[contains(.,'Save')]")
    private WebElement savePassengersBtn;
    @FindBy(xpath = "//form[@id='desktop']//button[@class = 'button button-primary']")
    private WebElement searchBtn;

    @FindBy(xpath = "//li[@class = 'primary-navigation_item']/a[contains(@href, 'booking-overview')]")
    private WebElement manageYourBookingBtn;

    public HomePage(WebDriver driver) throws WrongPageException {
        super(driver);
        if (!driver.getCurrentUrl().contains("home")) {
            throw new WrongPageException("HomePage url does not meet to the expected containing \"home\". " + driver.getCurrentUrl());
        } else {
            logger.info("HomePage initialized successfully.");
        }
    }

    public static String getHomePageEnEuUrl() {
        return homePageEnEuUrl;
    }

    public boolean whereToGoWindowIsDisplayed() {

        return isElementVisible(getBy("whereToGoWindow"));
    }

    public void fromFieldActivate() {
        fromField.click();
    }

    public void toFieldActivate() {
        toField.click();
    }

    public String getDestinationItemText(int item) {
        return dropdownDestinationValuesFrom.get(item).getText();
    }

    public void setFromDestinationByKeys(String airport) {
        fromField.clear();
        fromField.sendKeys(airport);
    }

    public void setToDestinationByKeys(String airport) {
        toField.clear();
        toField.sendKeys(airport);

    }

    public boolean isMatchFromDestinationFieldText(String airportValue) {
        return fromField.getAttribute("value").equals(airportValue);
    }

    public boolean isMatchToDestinationFieldText(String airportValue) {
        return toField.getAttribute("value").equals(airportValue);
    }

    public void selectDestinationFromDropDown(int item) {
        if (destinationDropDown.isDisplayed()) {
            try {
                dropdownDestinationValuesFrom.get(item).click();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                logger.error("Invalid index specified for the destination airports drop-down values");
            }
        }
    }

    public int getFromDestinationsCount() {
        return dropdownDestinationValuesFrom.size();
    }

    public boolean returnOnIsChecked() {
        return (returnOnCheckBoxCssState.getAttribute("checked") != null);
    }

    public void returnOnCheckBoxClick() {
        waitForElementIsClickable(returnOnCheckBox);
        returnOnCheckBox.click();
    }

    //TODO Should be placed somewhere in TestData layer -> DONE moved to DateTimeConverter
    public static String calculateDateNowPlusLag(long lagDays) {
        return DateTimeHelper.convertLocalDateToSourceStringFormat(LocalDate.now().plusDays(lagDays));
    }

    public void setDepartOnDateField(String date) {
        departOnDateField.clear();
        departOnDateField.sendKeys(date);
    }

    public void setReturnOnDateField(String date) {
        returnOnDateField.clear();
        returnOnDateField.sendKeys(date);
    }

    //TODO Remove after refactoring
    public void setDepartOnDateFieldPlusLag(long lagDays) {

        departOnDateField.clear();
        departOnDateField.sendKeys(calculateDateNowPlusLag(lagDays));
    }

    //TODO Remove after refactoring
    public void setReturnOnDateFieldPlusLag(long lagDays) {

        returnOnDateField.clear();
        returnOnDateField.sendKeys(calculateDateNowPlusLag(lagDays));
    }

    public boolean returnOnDateIsSet() {
        return !getSelectedReturnOnDate().isEmpty();
    }

    public String getSelectedDepartOnDate() {
        return departOnDateField.getAttribute("value");
    }

    public String getSelectedReturnOnDate() {
        return returnOnDateField.getAttribute("value");
    }

    public boolean isCorrectPassengersCountShown(String expPassengers) {
        return passengersField.getAttribute("value").contains(expPassengers);
    }

    public void passengersFieldActivate() {
        passengersField.click();
    }

    public boolean isVisiblePassengersPopup() {
        return passengersPopUp.isDisplayed();
    }

    //DONE may be can be done as one method for any passenger add child, baby, adult-> need to think how
    //TODO Remove after tests are refactored
    public void addAdultPassengers(int adultsCount) throws InvalidTestDataException {
        if (adultsCount >= 0 && adultsCount <= MAX_PASSENGERS_TO_FILL) {
            passengersField.click();
            //    adultsCountBox.clear();
            for (int i = 0; i < adultsCount; i++) {
                if (adultsIncreaseBtn.isEnabled()) {
                    adultsIncreaseBtn.click();
                    logger.info((i + 1) + " passenger added.");
                    passengersField.click();
                } else break;
            }
        } else {
            throw new InvalidTestDataException("Invalid input for adults count to add, or adultsIncrease button is not enabled.");
        }
    }

    public void addAdultPassenger() {

        if (adultsIncreaseBtn.isEnabled()) {
            adultsIncreaseBtn.click();
        } else {
            logger.error("Increase Adults button is not enabled.");
        }
    }

    public void addChildPassenger() {

        if (childrenIncreaseBtn.isEnabled()) {
            childrenIncreaseBtn.click();
        } else {
            logger.error("Increase Children button is not enabled.");
        }
    }

    public void addBabyPassenger() {

        if (babiesIncreaseBtn.isEnabled()) {
            babiesIncreaseBtn.click();
        } else {
            logger.error("Increase Babies button is not enabled.");
        }
    }

    public String getPassengersCountBoxValue(String type) throws IllegalArgumentException {

        PassengersTypes pt = PassengersTypes.valueOf(type.toUpperCase()); //throws exc here

        switch (pt) {
            case ADULTS:
                return adultsCountBox.getAttribute("value");
            case CHILDREN:
                return childrenCountBox.getAttribute("value");
            case BABIES:
                return babiesCountBox.getAttribute("value");
            default:
                throw new IllegalArgumentException(); //doesn't get there
        }
    }


    public void searchBtnSubmit() {
        searchBtn.click();
    }

    public ManageBookingPage openManageBookingToolbar() throws PageNotCreatedException {
        if (manageYourBookingBtn.isDisplayed()) {
            manageYourBookingBtn.click();
            return new ManageBookingPage(driver);
        } else {
            throw new PageNotCreatedException("ManageBooking booking page is not opened.");
        }
    }
}
