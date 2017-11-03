package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.business_objects.WelcomeLanguages;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.UnknownLanguageException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;

public class WelcomePage extends CommonPage {

    private static final String WELCOME_PAGE_TITLE = "Welcome to Transavia!";
    private static HashMap<String, WebElement> languages = new HashMap();

    @FindBy(xpath = "//div[@class = 'component_language-switch']//li//a")
    private List<WebElement> allLanguagesList;

    @FindBy(xpath = "//a[@href='/en-EU/home']")
    private WebElement welcomeOtherCountries;


    public WelcomePage(WebDriver driver) {
        super(driver);
        if (!WELCOME_PAGE_TITLE.equals(driver.getTitle())) {
            throw new WrongPageException("Welcome screen title does not match to the expected: " + WELCOME_PAGE_TITLE);
        } else {
            logger.info("Welcome page initialized successfully.");
        }
    }

    public HomePage selectLocaleAndOpenHomePage(WelcomeLanguages languageToSelect) {
        WebElement elementToClick = getLanguageWebElement(languageToSelect.toString());
       //is element present -> add method to CommonPage as FindElements(By)=null? not present: present;
        if (elementToClick.isDisplayed()){
           elementToClick.click();
           return new HomePage(driver);
        } else {
            throw new PageNotCreatedException("Home Page is not created after Language selection in Welcome screen.");
        }
    }

    private HashMap<String, WebElement> populateMapWithLanguagesFromWelcomePage() {
        if (languages != null) {
            languages.clear();
        }
        int i = 0;
        for (WebElement element: allLanguagesList) {
            String langName = allLanguagesList.get(i).getText().replaceAll("\\s", "_").toUpperCase();
            WebElement elementID = allLanguagesList.get(i);
            languages.put(langName, elementID);
            i++;
        }
        return languages;
    }

    private WebElement getLanguageWebElement(String name) {
        WebElement languageWebElement = null;
        languages = populateMapWithLanguagesFromWelcomePage();
        if(languages.containsKey(name)) {
            languageWebElement = languages.get(name);
        } else {
            logger.error("No such language is present in the HashMap.");
            throw new UnknownLanguageException("No such language is present in the HashMap.");
        }
        return languageWebElement;
    }

}
