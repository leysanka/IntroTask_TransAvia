package com.epam.transavia.demo.gui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



  public class CommonPage {

      public void waitForLoadingIsFinished(WebDriver driver)
      {
          WebDriverWait wait = new WebDriverWait(driver, 10);
          wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0;"));
      }

      public void scrollToElement(WebDriver driver, WebElement element)
      {
          ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
      }

}
