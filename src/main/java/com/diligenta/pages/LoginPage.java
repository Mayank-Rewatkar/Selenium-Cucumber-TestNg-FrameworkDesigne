package com.diligenta.pages;


import com.diligenta.testSuitBase.SuitBase;
import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage  extends SuitBase
{

    WebDriverWait wait;

    public LoginPage(WebDriver driver)
    {
//          this.driver=driver;
          PageFactory.initElements(driver,this);
          this.wait= waitHelper();
    }

    @FindBy(id="userEmail")
    private WebElement txt_uerEmail;

    @FindBy(id="userPassword")
    private WebElement txt_userPassword;

    @FindBy(id="login")
    private WebElement btn_login;

    @FindBy(xpath = "//div[@role='alert']")
    private WebElement alert_msg;
    
    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    private WebElement cartlink;

    public void login()
    {
        getDriver().get("https://rahulshettyacademy.com/client");
    }

    public void loginApplication(String userName,String Password)
    {
        txt_uerEmail.sendKeys(userName);
        txt_userPassword.sendKeys(Password);
        btn_login.click();
        Add_Log.info("Done with Git Changes");
    }


    public String checkAlert()
    {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='alert']"))).getText();
    }

    public void  checkCart()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@routerlink='/dashboard/cart']"))).click();
    }
}
