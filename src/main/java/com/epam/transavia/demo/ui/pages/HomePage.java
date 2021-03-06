package com.epam.transavia.demo.ui.pages;

import com.epam.transavia.demo.business_objects.PassengersTypes;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.UnknownPassengerTypeException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class HomePage extends CommonPage {

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
            logger.error("HomePage url does not meet to the expected containing \"home\". " + "Actual url is: " + driver.getCurrentUrl());
            throw new WrongPageException("HomePage url does not meet to the expected containing \"home\". " + driver.getCurrentUrl());
        }
    }

    public boolean whereToGoWindowIsDisplayed() {

        return isElementPresent(getBy("whereToGoWindow"));
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

    public String getFromDestinationFieldText(String airportValue) {
        return fromField.getAttribute("value");
    }

    public String getToDestinationFieldText(String airportValue) {
        return toField.getAttribute("value");
    }

    public void selectDestinationFromDropDown(int item) {
        if (isElementPresent(getBy("destinationDropDown")) && !dropdownDestinationValuesFrom.isEmpty()) {
            if (item > 0 && dropdownDestinationValuesFrom.size() >= item) {
                dropdownDestinationValuesFrom.get(item).click();
            } else {
                logger.error("Invalid item number for the From destinations drop-down list.");
            }
        } else {
            logger.error("Destination drop-down is not opened or empty.");
        }
    }

    public boolean returnOnIsChecked() {
        return (returnOnCheckBoxCssState.getAttribute("checked") != null);
    }

    public void returnOnCheckBoxClick() {
        waitForElementIsClickable(returnOnCheckBox);
        returnOnCheckBox.click();
    }

    public void setDepartOnDateField(String date) {
        departOnDateField.clear();
        departOnDateField.sendKeys(date);
    }

    public void setReturnOnDateField(String date) {
        returnOnDateField.clear();
        returnOnDateField.sendKeys(date);
    }

    public String getSelectedDepartOnDate() {
        return departOnDateField.getAttribute("value");
    }

    public String getSelectedReturnOnDate() {
        return returnOnDateField.getAttribute("value");
    }

    public String getPassengersFieldValue() {
        return passengersField.getAttribute("value");
    }

    public void passengersPopUpActivate() {
        if (!isDisplayedPassengersPopup()) {
            passengersField.click();
        }
    }

    public boolean isDisplayedPassengersPopup() {
        return passengersPopUp.isDisplayed(); //isDisplayed works properly there
    }

    public void increaseAdultPassenger() {

        if (adultsIncreaseBtn.isEnabled()) {
            adultsIncreaseBtn.click();
        } else {
            logger.error("Increase Adults button is not enabled.");
        }
    }

    public void increaseChildPassenger() {

        if (childrenIncreaseBtn.isEnabled()) {
            childrenIncreaseBtn.click();
        } else {
            logger.error("Increase Children button is not enabled.");
        }
    }

    public void increaseBabyPassenger() {

        if (babiesIncreaseBtn.isEnabled()) {
            babiesIncreaseBtn.click();
        } else {
            logger.error("Increase Babies button is not enabled.");
        }
    }

    public String getPassengersCountBoxValue(String type) {

        switch (PassengersTypes.getPassengerOfType(type)) {
            case ADULTS:
                return adultsCountBox.getAttribute("value");
            case CHILDREN:
                return childrenCountBox.getAttribute("value");
            case BABIES:
                return babiesCountBox.getAttribute("value");
            default:
                throw new UnknownPassengerTypeException(type + " is undefined passenger type in PassengersTypes enum.");
        }
    }


    public void searchBtnSubmit() {
        searchBtn.click();
    }

    public ManageBookingPage openManageBookingToolbar() throws PageNotCreatedException {
        if (isElementPresent(getBy("manageYourBookingBtn"))) {
            manageYourBookingBtn.click();
            return new ManageBookingPage(driver);
        } else {
            throw new PageNotCreatedException("ManageBooking booking page is not opened due to not visible.");
        }
    }
}
