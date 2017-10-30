package com.epam.transavia.demo.gui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



  public class CommonPage {

    protected WebDriver driver;

      public CommonPage(WebDriver driver) {
          this.driver = driver;
      }

      public void waitForLoadingIsFinished()
      {
          WebDriverWait wait = new WebDriverWait(driver, 10);
          wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0;"));
      }

      public void waitForElementIsClickable( WebElement element ){
          WebDriverWait wait = new WebDriverWait(driver, 10);
          wait.until(ExpectedConditions.elementToBeClickable(element));
      }

      public void scrollToElement( WebElement element)
      {
          ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
      }

}
