package com.epam.transavia.demo.ui.pages;

import com.epam.transavia.demo.business_objects.WelcomeScreenLanguages;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;

public class WelcomePage extends CommonPage {

    private static final String WELCOME_PAGE_TITLE = "Welcome to Transavia!";
    private static HashMap<String, WebElement> languages = new HashMap<String, WebElement>();

    @FindBy(xpath = "//div[@class = 'component_language-switch']//li//a")
    private List<WebElement> allLanguagesList;

    @FindBy(xpath = "//a[@href='/en-EU/home']")
    private WebElement welcomeOtherCountries;


    public WelcomePage(WebDriver driver) {
        super(driver);
        if (!WELCOME_PAGE_TITLE.equals(driver.getTitle())) {
            throw new WrongPageException("Welcome screen title does not match to the expected: " + WELCOME_PAGE_TITLE + "\n");
        } else {
            logger.info("Welcome page initialized successfully.");
        }
    }

    public HomePage selectLocaleAndOpenHomePage(WelcomeScreenLanguages languageToSelect) {
        WebElement elementToSelect = getLanguageWebElement(languageToSelect);
        if (elementToSelect != null) {
            elementToSelect.click();
            return new HomePage(driver);
        } else {
            throw new PageNotCreatedException("Home Page is not created after Language selection in Welcome screen.");
        }
    }

    private HashMap<String, WebElement> populateMapWithLanguagesFromWelcomePage() {
        if (languages != null) {
            languages.clear();
        }
        if (!allLanguagesList.isEmpty()) {
            int i = 0;
            for (WebElement element : allLanguagesList) {
                /*Fetch each language's text from all languages list on Welcome page and convert it to enum-like format.*/
                String langName = allLanguagesList.get(i).getText().replaceAll("\\s", "_").toUpperCase();
                WebElement webElement = allLanguagesList.get(i);
                languages.put(langName, webElement);
                i++;
            }
        }
        return languages;
    }

    //TODO Verify working after transavia is recovered!
    private WebElement getLanguageWebElement(WelcomeScreenLanguages enumLang) {
        WebElement languageWebElement = null;
        languages = populateMapWithLanguagesFromWelcomePage();
        if (WelcomeScreenLanguages.isEnumLangPresentInMap(enumLang, languages)) {
            languageWebElement = languages.get(enumLang.toString());
        }
        return languageWebElement;
    }

}
