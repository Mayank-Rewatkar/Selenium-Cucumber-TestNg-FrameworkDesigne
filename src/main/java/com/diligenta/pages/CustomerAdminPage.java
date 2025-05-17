package com.diligenta.pages;

import com.diligenta.testSuitBase.SuitBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.diligenta.testSuitBase.SuitBase.waitHelper;

public class CustomerAdminPage extends BasePage
{

    WebDriverWait wait;

    public CustomerAdminPage(WebDriver driver)
    {
//          this.driver=driver;
        PageFactory.initElements(driver,this);
        this.wait= waitHelper();
    }


    @FindBy(id = "name")
    private WebElement Name;

    @FindBy(id = "eil")
    private WebElement Email;



    public void enterName(String name)
    {

        if(name.equalsIgnoreCase("Name"))
        {
            Name.sendKeys("Mayank");
        }
        else if(name.equalsIgnoreCase("email"))
        {
            Email.sendKeys("Mayank@Gmail.com");
        }

    }

}
