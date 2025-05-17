package listeners;


import com.diligenta.utility.Reports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import com.diligenta.testSuitBase.SuitBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.diligenta.testSuitBase.SuitBase.Add_Log;
import static com.diligenta.testSuitBase.SuitBase.getDriver;


public class ScreenshotUtility implements ITestListener, ISuiteListener
{

    ExtentReports extent = null;

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void setTest(ExtentTest testInstance ) {
        test.set(testInstance);
    }


    @Override
    public void onTestStart(ITestResult result) {
        //To capture Test Class and Test Case Name
        String TestClass = result.getMethod().getMethodName(); // fallback
        String testName="";
        Object[] params = result.getParameters();
        if (params[1] != null) {
             testName = (String) params[1];
        }
        //To Set the test Name in Extent Report
        setTest(extent.createTest(TestClass+" -- "+testName));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //To Attach Test which is passed
        test.get().log(Status.PASS,"Test Passed");
        if(SuitBase.Param.getProperty("ScreenShotOnPass").equalsIgnoreCase("yes"))
        {
            String screenshotPath=captureScreenShot(result,"PASS");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().log(Status.FAIL,"Test Fail");
        //returns the exception or error that caused the test to fail
        result.getThrowable();
        test.get().fail(result.getThrowable());

        //to Capture Screenshot on failure only if it is set as yes in Properties File
        if(SuitBase.Param.getProperty("ScreenShotOnFail").equalsIgnoreCase("yes"))
        {
            String screenshotPath=captureScreenShot(result,"FAIL");
            Add_Log.info("Screen shot pat "+screenshotPath);
            //This will attach  ScreenShot TO Extet Report
            test.get().addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onStart(ISuite suite) {
        String suiteName = suite.getName();
        extent = Reports.reportConfiguration(suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        ISuiteListener.super.onFinish(suite);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    //This will execute before mail Test start (@Test)
    @Override
    public void onStart(ITestContext context) {
        extent = Reports.reportConfiguration(context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        //This will Generate Report
        extent.flush();
    }

    public String captureScreenShot(ITestResult result,String status){
        String destDir="";
        String TestClass = result.getMethod().getMethodName(); // fallback
        String testName="";
        Object[] params = result.getParameters();
        if (params[1] != null) {
            testName = (String) params[1];
        }
        String passfailMethod=TestClass+" -- "+testName;
        String timeStamp=new SimpleDateFormat("d-MMMM-yyyy-HH-mm-ss-ms").format(new Date());
        TakesScreenshot ts=(TakesScreenshot)getDriver();
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        String baseDir = System.getProperty("user.dir") + "/test-output";
        if(status.equalsIgnoreCase("Fail")) {
            destDir=baseDir+"/FailuresScreenshots";
        }

        //This will create the entire path output/screenshots/failure if it doesn't already exist.
        File directory = new File(destDir);
        if (!directory.exists()) {
            directory.mkdirs(); // creates all intermediate dirs
        }


        //Set the file name as Test Class Name and test Case and Time Stamp
        String destFile=passfailMethod +" - "+ timeStamp+".jpg";
        String fullPath = destDir + "/" + destFile;
        try {
            //store file at destination folder
            File dest = new File(fullPath);
            FileUtils.copyFile(screenshot, dest);
            return "../FailuresScreenshots/" + destFile;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


