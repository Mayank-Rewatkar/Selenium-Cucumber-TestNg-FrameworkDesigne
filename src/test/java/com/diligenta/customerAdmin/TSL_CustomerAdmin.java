package com.diligenta.customerAdmin;
import com.diligenta.pages.CustomerAdminPage;
import com.diligenta.pages.LoginPage;
import com.diligenta.pages.PageFunctions;
import com.diligenta.utility.SuitUtility;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.diligenta.pages.PageFunctions.createWorDocument;


public class TSL_CustomerAdmin extends customerAdminBase {

    static int DataSet = -1;
    SoftAssert s_assert=null;
    static boolean Testfail = true; //intentionally All Test Cases Are Fail
    public static boolean TestSkip = false;
    String TestCaseName = null;
    static boolean TestCasePass=true;
    String  testCaseDocName=null;
    String TestDataToRun[] = null;
    String ToRunColumnNameTestCase = null;
    String ToRunColumnNameTestData = null;


    //-------------** Declare all Page Objects **-------------------
    LoginPage obj_loginPage;
    CustomerAdminPage obj_CustomerAdminPage;


    @BeforeTest
    public void checkCaseToRun() throws IOException {
        //Called init() function  from Suite Base class to initialise .xls File
        init();

        FilePath = TestCaseListExcelOne;
        TestCaseName = this.getClass().getSimpleName();
        System.out.println("Test Case Name" + TestCaseName);
        //Sheet Name to Check CaseToRun Flag  against test case
        SheetName = "TestCasesList";
        //Name of column In TestCaseSList Excel Data sheets
        ToRunColumnNameTestCase = "CaseToRun";
        //Name of colum in Test Case Data Sheet
        ToRunColumnNameTestData = "DataToRun";

        if (!SuitUtility.checkToRunUtility(FilePath, SheetName, ToRunColumnNameTestCase, TestCaseName)) {
            Add_Log.info("Printing Logs");
            System.out.println(TestCaseName + " : CaseToRun = N for So Skipping Execution.");
            Add_Log.info(TestCaseName + " : CaseToRun = N for So Skipping Execution.");
            SuitUtility.writeResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "SKIP");
            throw new SkipException(TestCaseName + " Case to run flag is N or Blank. So Skipping the execution");
        } else {
            Add_Log.info(TestCaseName + " : CaseToRun = Y Executing Test Excel.");
            System.out.println(TestCaseName + " : CaseToRun = Y Executing Test Excel.");
            TestDataToRun = SuitUtility.checkToRunUtilityOfData(FilePath, TestCaseName, ToRunColumnNameTestData);
            System.out.println("Test Data To run " + Arrays.toString(TestDataToRun));
        }
    }


    @Test(dataProvider = "TSL_CustomerAdminData")
    public void TSL_CustomerAdminTest(String SlNo, String caseName, String Name) throws IOException {

        //Data Set
        DataSet = (Integer.parseInt(SlNo) - 1);

        //Created Soft Assert Object of TestNg Class
        s_assert = new SoftAssert();

        // IF found  dataToRun = N for the data set then execution will be skipped for that data set.
        if (!TestDataToRun[DataSet].equalsIgnoreCase("Y")) {
            Add_Log.info(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " so skipping its Execution.");
            System.out.println(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " so skipping its Execution.");
            TestSkip = true;
            throw new SkipException("DataToRun for row Number " + DataSet + " Is No of Blank so Skipping its Execution");
        }
        Add_Log.info("---------Test Customer Admin Starts Here---------");
        Add_Log.info("caseName :- " + caseName);

        //-------------** Loading Browser **-----------------
        loadBrowser();
        Add_Log.info("Browser launched");

        //------------** Initialization of all Page Objects **--------------------
        obj_loginPage = new LoginPage(getDriver());
        obj_CustomerAdminPage = new CustomerAdminPage(getDriver());

        getDriver().get("https://testautomationpractice.blogspot.com/");
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        obj_CustomerAdminPage.enterName(Name);
        PageFunctions.takeScreenShotWithText(getDriver(), caseName, "");

        //------------------** Test case Ends here **----------------------------
        Add_Log.info("Test case Ends here");

        try {
            if (Testfail) {
                //At Last,test data assertion will be reported in testNG reports and it will mark test data ,test cases and test suite as fail
                s_assert.assertAll();
                Add_Log.info("After Assert");
                if (true) Testfail = false;
                Reporter.log("Passed" + TestCaseName, true);
            }
        } catch (Exception e) {
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
        } else if (Testfail) {
            Add_Log.info("Inside the testFail Block");
            Add_Log.info(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " so Skip in Excel.");
            s_assert = null;
            TestCasePass = false;
            SuitUtility.writeResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet + 1, "FAIL");
            SuitUtility.writeResultUtility(FilePath, TestCaseName, "DataToRun", DataSet + 1, "Y");
            createWorDocument("FAIL", testCaseDocName);
            getDriver().close();
            getDriver().quit();
        } else {
            Add_Log.info(TestCaseName + " : DataToRun N for data set line " + (DataSet + 1) + " as PASS in Excel.");
            SuitUtility.writeResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet + 1, "PASSED");
            SuitUtility.writeResultUtility(FilePath, TestCaseName, "DataToRun", DataSet + 1, "Executed");
            createWorDocument("PASS", testCaseDocName);
            getDriver().close();
            getDriver().quit();
        }
        TestSkip = false;
        Testfail = true;
    }

    @DataProvider
    public Object[][] TSL_DeathClaimData() {
        return SuitUtility.GetTestDataUtility(FilePath, TestCaseName);
    }
}