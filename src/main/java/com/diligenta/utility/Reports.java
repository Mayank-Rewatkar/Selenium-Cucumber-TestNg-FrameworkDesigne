package com.diligenta.utility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reports
{
    public static  ExtentReports extent=null;

    public static ExtentReports reportConfiguration(String reportName)
    {
          //To setup  Report Properties
//        String timeStamp=new SimpleDateFormat("d MMMM yyyy").format(new Date());
          String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
//        String path=  System.getProperty("user.dir")+"\\test-output\\ExtentReport-"+ timeStamp +".html";
          String baseDir = System.getProperty("user.dir") + "/test-output";
          String destDir = baseDir + "/ExtentReports";

          File directory = new File(destDir);
          if (!directory.exists()) {
            directory.mkdirs(); // creates all intermediate dirs
          }

//        String path = System.getProperty("user.dir") + "\\test-output\\ExtentReport-" + reportName + "-" + timeStamp + ".html";
          String path = destDir + "/" +"ExtentReport"+ reportName + "-" + timeStamp + ".html";

          ExtentSparkReporter reporter=new ExtentSparkReporter(path);
          reporter.config().setDocumentTitle("Test Result -"+reportName);
          reporter.config().setReportName("Execution Result");

          //To generate reports
          extent=new ExtentReports();
          extent.attachReporter(reporter);
          extent.setSystemInfo("Tester","Mayank");
          return extent;
    }
}
