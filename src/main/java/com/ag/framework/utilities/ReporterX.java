package com.ag.framework.utilities;

import com.ag.framework.core.Global;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;

public  class ReporterX {

    private static TakesScreenshot ts;
    private static File src;
    private static SoftAssert assertion=new SoftAssert();
    private static int ScreenCaptureCount;
    public static ExtentTest extentTestReporter ;

    public static void VerifyCheckPoint(WebElement elem, String details)
    {
        Status status ;
        try {

            try {
                if(elem.isDisplayed())
                {
                    status = Status.PASS;
                }
                else
                    status = Status.FAIL;
            }
            catch (Exception ex){status = Status.FAIL;}

            log(status,details);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void log(Status status, String details) {


        try {

            System.out.println("\t\t["+status.toString().toUpperCase()+"]  " + details);

            if(status.toString().trim().toUpperCase().equals("PASS"))
                assertion.assertTrue(true,details);
            else
                assertion.assertTrue(false,details);

            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.log(status, (String)details,MediaEntityBuilder.createScreenCaptureFromPath( System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void info(String details)
    {
        assertion.assertTrue(true, details);
        extentTestReporter.info((String)details, (MediaEntityModelProvider)null);
        System.out.println("\t\t["+Status.INFO.toString().toUpperCase()+"]  " + details);
    }

    public static void pass(String details) {

        try {
            System.out.println("\t\t["+Status.PASS.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(true, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.pass((String) details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fail(String details)
    {
        try {
            System.out.println("\t\t["+Status.FAIL.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(false, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.fail((String) details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fatal(String details) {
        try {
            System.out.println("\t\t["+Status.FATAL.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(false, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.log(Status.FATAL, (String)details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void warning(String details) {

        try {
            System.out.println("\t\t["+Status.WARNING.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(false, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.warning((String)details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void error(String details) {
        try {
            System.out.println("\t\t["+Status.ERROR.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(false, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.error((String)details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void skip(String details) {
        try {
            System.out.println("\t\t["+Status.SKIP.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(false, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.skip((String)details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void debug(String details) {
        try {
            System.out.println("\t\t["+Status.DEBUG.toString().toUpperCase()+"]  " + details);
            assertion.assertTrue(false, details);
            ScreenCaptureCount++;
            ts = (TakesScreenshot) Global.browser;
            src = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png"));
            extentTestReporter.debug((String)details,
                    MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screen" + ScreenCaptureCount + ".png").build());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void AssertALL() {
        try{ assertion.assertAll();}catch (Exception ex){fail("Failed to assert all.!");}
    }

}