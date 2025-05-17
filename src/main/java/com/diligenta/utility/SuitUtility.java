package com.diligenta.utility;


import java.io.IOException;

public class SuitUtility
{
    public static boolean checkToRunUtility(Read_XLS xlsx, String sheetName, String ToRun, String testSuit) throws IOException {

        boolean Flag=false;
        if(xlsx.retrieveToRunFlag(sheetName,ToRun,testSuit).equalsIgnoreCase("Y"))
        {
            Flag=true;
            System.out.println("Flag Value "+Flag);
        }
        else
        {
            Flag=false;
            System.out.println("Flag  Value "+Flag);
        }
        return Flag;
    }

    public static boolean writeResultUtility(Read_XLS xlsx,String sheetName,String colName,String rowName,String result)
    {
         return xlsx.writeResult(sheetName,colName,rowName,result);
    }

    public static boolean writeResultUtility(Read_XLS xlsx,String sheetName,String colName,int rowNum,String result)
    {
        return xlsx.writeResult(sheetName,colName,rowNum,result);
    }

    public static String[] checkToRunUtilityOfData(Read_XLS xls,String sheetName,String colName)
    {
        return  xls.retrieveToRunFlagTestData(sheetName,colName);
    }


    public static Object[][] GetTestDataUtility(Read_XLS xls,String sheetName)
    {
        return xls.retriveTestData(sheetName);
    }

}
