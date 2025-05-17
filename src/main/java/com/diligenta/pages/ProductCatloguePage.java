package com.diligenta.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatloguePage extends BasePage
{

    WebDriver driver;
        public ProductCatloguePage(WebDriver driver)
        {
    //        this.driver=driver;
//              super(driver);
              PageFactory.initElements(driver,this);
        }

        public void checkAlert()
        {
            //ng-animating
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
//            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
            driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
        }

        public void getProductList()
        {
            List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
            WebElement prod= products.stream().filter(product ->
            product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase("Banarsi Saree")).findFirst().orElse(null);
            prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
        }

}
