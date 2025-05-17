package com.diligenta.deathClaim;
import com.diligenta.pages.LoginPage;
import com.diligenta.pages.PageFunctions;
import com.diligenta.utility.SuitUtility;
import listeners.RetryListener;
import org.apache.commons.math3.analysis.function.Add;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.diligenta.pages.PageFunctions.*;


public class TSL_DeathClaim extends deathClaimBase
{

    static int DataSet =-1;
    SoftAssert s_assert=null;
    static boolean Testfail= true; //intentionally All Test Cases Are Fail
    static boolean TestCasePass=true;
    public static boolean TestSkip=false;
    String TestCaseName = null;

    String  testCaseDocName=null;
    String TestDataToRun[]=null;
    String ToRunColumnNameTestCase = null;
    String ToRunColumnNameTestData = null;


    //-------------** Declare all Page Objects **-------------------
    LoginPage obj_loginPage;


    @BeforeTest
    public void checkCaseToRun() throws IOException {
       //Called init() function  from Suite Base class ti initialise .xls File
        init();

        FilePath= TestCaseListExcelOne;
        TestCaseName=this.getClass().getSimpleName();
        System.out.println("Test Case Name"+TestCaseName);
        //Sheet Name to Check CaseToRun Flag  against test case
        SheetName="TestCasesList";
        //Name of column In TestCaseSList Excel Data sheets
        ToRunColumnNameTestCase="CaseToRun";
        //Name of colum in Test Case Data Sheet
        ToRunColumnNameTestData="DataToRun";

        if(!SuitUtility.checkToRunUtility(FilePath,SheetName,ToRunColumnNameTestCase,TestCaseName))
        {
            Add_Log.info("Printing Logs");
            Add_Log.info(TestCaseName +" : CaseToRun = N for So Skipping Execution.");
            SuitUtility.writeResultUtility(FilePath,SheetName,"Pass/Fail/Skip",TestCaseName,"SKIP");
            throw  new SkipException(TestCaseName+" Case to run flag is N or Blank. So Skipping the execution");
        }
        else {
            Add_Log.info(TestCaseName +" : CaseToRun = Y Executing Test Excel.");
            TestDataToRun= SuitUtility.checkToRunUtilityOfData(FilePath,TestCaseName,ToRunColumnNameTestData);
        }
    }


    //RetryAnalyzer = RetryListener.class this attribute takes IRetryAnalyser to Re run fail Test
    @Test(dataProvider = "TSL_DeathClaimData")
//    @Test(dataProvider = "TSL_DeathClaimData", retryAnalyzer = RetryListener.class)
    public void TSL_DeathClaimTest(String SlNo,String caseName,String Email,String Password)
    {
        //Setting up TestCase name
        testCaseDocName=caseName;

        //Data Set
        DataSet=(Integer.parseInt(SlNo) -1);

        //Created Soft Assert Object of TestNg Class
        s_assert=new SoftAssert();

        // IF found  dataToRun = N for the data set then execution will be skipped for that data set.
        if(!TestDataToRun[DataSet].equalsIgnoreCase("Y"))
        {
            Add_Log.info(TestCaseName +" : DataToRun N for data set line "+(DataSet +1) +" so skipping its Execution.");
            TestSkip=true;
            throw new SkipException("DataToRun for row Number "+DataSet+" Is No of Blank so Skipping its Execution");
        }


        Add_Log.info("---------Test Death Claim Starts Here---------");
        Add_Log.info("caseName :- "+caseName);

        //-------------** Loading Browser **-----------------
        loadBrowser();
        Add_Log.info("Browser launched");

        //------------** Initialization of all Page Objects **----------------
        obj_loginPage=new LoginPage(getDriver());

        //-------------** Methods Calling and Actual Execution Starts Here **-----------------
        obj_loginPage.login();
        PageFunctions.takeScreenShotWithText(getDriver(),caseName,"Login Page");
        obj_loginPage.loginApplication(Email,Password);
        PageFunctions.takeScreenShotWithText(getDriver(),caseName,"Login Details Added");

        PageFunctions.takeScreenShotWithText(getDriver(),caseName,"Cart Page");

        //------------------** Test case Ends here **----------------
        Add_Log.info("Test case Ends here");

        try
        {
            if(Testfail) {
                //At Last,test data assertion will be reported in testNG reports and it will mark test data ,test cases and test suite as fail
                Add_Log.info("try Cathch Test Fail block");
                s_assert.assertAll();
                Add_Log.info("After Assert");
                if (true) Testfail=false;
                Add_Log.info("staic status "+Testfail);
                Reporter.log("Passed" + TestCaseName, true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Reporter.log("Failed: " + TestCaseName + " - " + e.getMessage(), true);
            throw e; //Re throw  to mark test case Fail
        }
    }


    //This will execute after every @Test Method Run
    @AfterMethod
    public void reporterDataResults() {
        if (TestSkip) {
            Add_Log.info("Inside Skip Block ");
            Add_Log.info(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " so Skip in Excel.");
            SuitUtility.writeResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet + 1, "SKIP");
        }else if(Testfail){
            Add_Log.info("Inside the testFail Block");
            Add_Log.info(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " so Skip in Excel.");
            s_assert=null;
            TestCasePass=false;
            SuitUtility.writeResultUtility(FilePath,TestCaseName,"Pass/Fail/Skip",DataSet+1,"FAIL");
            SuitUtility.writeResultUtility(FilePath,TestCaseName,"DataToRun",DataSet+1,"Y");
            createWorDocument("FAIL",testCaseDocName);
            getDriver().close();
            getDriver().quit();
        }else {
            Add_Log.info(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " as PASS in Excel.");
            SuitUtility.writeResultUtility(FilePath,TestCaseName,"Pass/Fail/Skip",DataSet+1,"PASSED");
            SuitUtility.writeResultUtility(FilePath,TestCaseName,"DataToRun",DataSet+1,"Executed");
            createWorDocument("PASS",testCaseDocName);
            getDriver().close();
            getDriver().quit();
        }
        TestSkip=false;
        Testfail=true;
    }

    @DataProvider
    public Object[][] TSL_DeathClaimData()
    {
        return  SuitUtility.GetTestDataUtility(FilePath,TestCaseName);
    }



}
