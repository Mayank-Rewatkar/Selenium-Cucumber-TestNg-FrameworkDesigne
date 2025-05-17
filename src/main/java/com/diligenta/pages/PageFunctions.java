package com.diligenta.pages;

import com.diligenta.testSuitBase.SuitBase;
import com.github.dockerjava.core.dockerfile.DockerfileStatement;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.math3.analysis.function.Add;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPageMargins;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PageFunctions extends SuitBase
{


    //This method is used to capture ScreenShot with text
    public static void takeScreenShotWithText(WebDriver driver,String testCaseName, String msg){

        String testCaseId = testCaseName.replaceAll("[^0-9]", "");
        Add_Log.info("Test Case is from sceen shotbmethod ="+testCaseId);
          try
          {
              String directory="";
              TakesScreenshot ts=((TakesScreenshot)driver);
              File srcFile = ts.getScreenshotAs(OutputType.FILE);

              //Setting the Screenshot name
              DateFormat dateFormat=new SimpleDateFormat("dd-MMM-YYYY_hh-mm-ssaa");
              String destFile=testCaseId+"-"+ dateFormat.format(new Date())+".png";
              Add_Log.info("Screen shot path and name "+destFile);
              Add_Log.info("Before Directory ");
              directory = System.getProperty("user.dir")+"\\screenshots\\"+testCaseId; //create directory if it does not exist
//              directory = System.getProperty("user.dir")+File.separator+ "screenshots"+File.separator+testCaseId; //create directory if it does not exist
                Add_Log.info("Directory "+directory);
              new File(directory).mkdirs();
              FileUtils.copyFile(srcFile,new File(directory+"/"+destFile));
//            FileUtils.copyFile(screenshot,destFile);
              Add_Log.info("After Directory-------------- ");
              //Write text on Screenshot
              final  BufferedImage image= ImageIO.read(new File(directory+"/"+destFile));//Reading captured File
              Graphics g = image.getGraphics();
              g.setFont(g.getFont().deriveFont(50f));
              g.setColor(Color.BLACK);
              g.drawString(msg,1200,800);
              g.dispose();
              ImageIO.write(image,"png",new File(directory+"/"+destFile));//Writing Captured File
          }
          catch (Exception e)
          {
              Add_Log.info("Inside CCatch");
              e.printStackTrace();
          }

    }


    public String takeScreenShotOnFail(String testCaseName) throws IOException {

        DateFormat dateFormat=new SimpleDateFormat("dd-MMM-YYYY_HH-mm-ssaa");
        String testCaseId = testCaseName.replaceAll("[^0-9]", "");
        TakesScreenshot ts=(TakesScreenshot)getDriver();
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        File file=new File(System.getProperty("user.dir")+"\\FailedScreenShots\\"+ testCaseId +dateFormat+".jpg");
        String  FilePath= file.getPath();
        FileUtils.copyFile(screenshot,file);
        return FilePath;
    }


    public static void createWorDocument(String status,String testCaseName)
    {
        //Removing all non digits symbol from testcaseID
        Add_Log.info("Status of Test in word "+status);
        String testCaseId = testCaseName.replaceAll("[^0-9]", "");
        Add_Log.info("Extracted Test Case No from word functions"+testCaseId);
        if(Objects.isNull(testCaseName) || testCaseName.isEmpty())  testCaseName="NoName";

        testCaseName =testCaseName.replaceAll("[^a-zA-Z\\d]"," ");
        Add_Log.info("Extracted Test Case Name from word function "+testCaseName);
        String oldDirectory= System.getProperty("user.dir")+"\\screenshots\\"+testCaseId;
        Add_Log.info("Old Directory "+oldDirectory);
        try
        {
            XWPFDocument docx=new XWPFDocument();
            XWPFRun run= docx.createParagraph().createRun();
            CTSectPr secPr= docx.getDocument().getBody().getSectPr();
            if(secPr==null) secPr=docx.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar= secPr.getPgMar();
            if(pageMar==null) pageMar=secPr.addNewPgMar();
            pageMar.setLeft((BigInteger.valueOf(720)));
            pageMar.setRight((BigInteger.valueOf(720)));
            pageMar.setTop((BigInteger.valueOf(720)));
            pageMar.setBottom((BigInteger.valueOf(720)));
            pageMar.setFooter((BigInteger.valueOf(720)));
            pageMar.setHeader((BigInteger.valueOf(720)));
            pageMar.setGutter((BigInteger.valueOf(0)));

            //Concatenating status to the Doc File
            String newOutputDocNameFormat=status+"-"+testCaseId;
            DateFormat dateFormat=new SimpleDateFormat("dd-MMM-YYYY_hh-mm-ssaa");
            String oldOutputDirectory= oldDirectory+"//"+testCaseId+"-"+dateFormat.format(new Date())+".docx";
            FileOutputStream out= new FileOutputStream(oldOutputDirectory);

            File file=new File(oldDirectory+"//");
            Add_Log.info("Empty Document File Created");
            File[] files=file.listFiles();
            List<String> flist= new ArrayList<>();
            if(file!=null)
            {
                for(File f:files)
                {
                    String extension=FilenameUtils.getExtension(f.getName());
                    if(extension.equalsIgnoreCase("png"))
                    {
                        flist.add(f.getName());//Adding Png File in List
                        InputStream pic=new FileInputStream(f);
                        run.addBreak();
                        run.addPicture(pic,XWPFDocument.PICTURE_TYPE_PNG,f.getName(), Units.toEMU(545),Units.toEMU(275));
                        pic.close();
                    }
                }
                docx.write(out);
                out.flush();
                out.close();
                docx.close();

                //Deleting all .png files
                flist.forEach(ele->{
                    File fi= new File(oldDirectory+"//"+ele);
                    fi.delete();
                });

                File srcFile= new File(oldOutputDirectory);
                Add_Log.info("Src File Path "+srcFile.getPath());
                File destFile=new File(System.getProperty("user.dir")+"//screenshots//"+testCaseName+"//"+newOutputDocNameFormat +"-"+dateFormat.format(new Date())+".docx");
                Add_Log.info("Destination Path "+destFile);
                //moving source file to new dest file
                FileUtils.moveFile(srcFile,destFile);
                File srcDir= new File(oldDirectory);

                //Deleting old src file and Directory
                srcFile.delete();
                srcDir.delete();
            }
        }
        catch (Exception e)
        {
            Add_Log.info(e);
        }
    }

}
