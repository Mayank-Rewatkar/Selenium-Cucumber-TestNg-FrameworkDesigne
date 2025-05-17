package com.diligenta.customerAdmin;

import com.diligenta.testSuitBase.SuitBase;
import com.diligenta.utility.Read_XLS;
import com.diligenta.utility.SuitUtility;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class customerAdminBase extends SuitBase
{
      public static Read_XLS TestCaseListExcelOne=null;
      public static Properties death_life_claim=null;
      Read_XLS FilePath=null;
      String SheetName;
      String SuiteName;
      String ToRunColumnName;

      @BeforeSuite
      public void checkSuiteToRun() throws IOException {

            //Called Init() from SuiteBase class to Initialise .xls Files
            init();

            //Initialising Excel File of Particular Module
            TestCaseListExcelOne = new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\diligenta\\excelfiles\\CustomerAdmin.xlsx");

            //initialising SQL properties File
            death_life_claim = new Properties();
            FileInputStream fip = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\diligenta\\property_sqldata\\deathLife.properties");
            death_life_claim.load(fip);

            FilePath=TestSuiteListExcel;
            SheetName="SuitesList";
            SuiteName="CustomerAdmin";
            ToRunColumnName="SuiteToRun";

            if(!SuitUtility.checkToRunUtility(FilePath,SheetName,ToRunColumnName,SuiteName))
            {
                  System.out.println("Suite to run =N  hence skipping the execution");
                  //To Write Output of case in colum
                  SuitUtility.writeResultUtility(FilePath,SheetName,"Skipped/Executed",SuiteName,"Skipped");
                  throw  new SkipException(SuiteName+" Suite to run flag is N or Blank. So Skipping the execution");
            }
            else {
                  SuitUtility.writeResultUtility(FilePath,SheetName,"Skipped/Executed",SuiteName,"Executed");
            }

      }



}
