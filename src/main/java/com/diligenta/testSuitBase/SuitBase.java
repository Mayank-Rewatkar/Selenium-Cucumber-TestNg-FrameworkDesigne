package com.diligenta.testSuitBase;

import com.diligenta.utility.Read_XLS;
//import com.diligenta.utility.Reports;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;

public class SuitBase
{

    public static Logger Add_Log=null;
    public static Properties Param=null;
    public static Read_XLS TestSuiteListExcel=null;

    public static WebDriverWait wait=null;

    public static ThreadLocal<WebDriver> driver=new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }


    public static  WebDriverWait waitHelper()
    {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.pollingEvery(Duration.ofMillis(50));
        return wait;
    }

    public void init() throws IOException {

        //To Initiate Logger Services
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir")+"/src/test/resources/logreporting/log4j2.xml");
        Add_Log= LogManager.getLogger();

        //Initialising Param.Properties
        Param=new Properties();
        FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\diligenta\\properties\\Param.properties");
        Param.load(fin);
        Add_Log.info("Properties File Loaded Successfully");

        //Initialising SuiteList
        TestSuiteListExcel=new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\diligenta\\excelfiles\\TestSuiteList.xlsx");
        System.out.println("Printing Excel file "+TestSuiteListExcel);
    }

    public void loadBrowser()
    {
        //Selecting browser driver for the Execution
        String browserName=Param.getProperty("browser");
        System.out.println("browser name "+browserName);
        if(browserName.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"browserDrivers");
            ChromeOptions chromeOptions=new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            setDriver(new ChromeDriver(chromeOptions));
            Add_Log.info("Chrome Browser Selected To Launch");
        }else if(browserName.equalsIgnoreCase("Edge")) {
            System.setProperty("webdriver.edge.driver","C:\\Users\\Hp\\Intelije\\SeleniumFrameworkDesigne\\browserDrivers\\msedgedriver.exe");
            setDriver(new EdgeDriver());
            Add_Log.info("Edge Browser Selected To Launch");
        }
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
    }
}
