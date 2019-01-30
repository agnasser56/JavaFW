package com.ag.framework.core;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;

import java.util.Hashtable;

public class Global {

    public static String dir = System.getProperty("user.dir");
    public static String ReportPath = DataManager.getPropertyFile("ReportPath");
    public static String ExtentReportXMLPath = DataManager.getPropertyFile("ExtentReportXMLPath");
    public static String ScrhooShotPath = DataManager.getPropertyFile("ScreenShots");
    public static String RunEnvironment;
    public static String RunType;
    public static String ProjectName;
    public static String Browser;
    public static String URL;
    public static WebDriver browser;
    public static ExtentTest reporter;
    public static Hashtable<String, String> DataSet;
    public static long browserTimeOut;
    public static AppiumDriver MobileAPP;
}

