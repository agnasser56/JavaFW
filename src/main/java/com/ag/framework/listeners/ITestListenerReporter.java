package com.ag.framework.listeners;


import com.ag.framework.core.Global;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ITestListenerReporter implements ITestListener {

    private static final String FILE_NAME = "_Test Summary Report";
    //protected static WebDriver driver;
    protected static ExtentReports reports;
    int j = 1;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hhmmss"); // add S if you need milliseconds
    private String OldFunName = "";


    public void onTestStart(ITestResult result) {

        String Func = result.getMethod().getMethodName();

        if (OldFunName.equals(Func)) {
            j++;
            Global.reporter = reports.startTest(result.getMethod().getMethodName() + "_Iteration_" + j, result.getMethod().getDescription());
        } else {
            j = 1;
            Global.reporter = reports.startTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        }
        // test = reports.startTest(result.getMethod().getMethodName()+"_Iteration_"+j,result.getMethod().getDescription());
        OldFunName = Func;


        Global.reporter.log(LogStatus.INFO, result.getMethod().getMethodName() + "test execution is started");
    }

    public void onTestSuccess(ITestResult result) {
        //  System.out.println("on test success");
        //reporter.log(LogStatus.PASS, result.getMethod().getMethodName() + "test is passed");

        TakesScreenshot ts = (TakesScreenshot) Global.browser;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(Global.dir+ Global.ScrhooShotPath + result.getMethod().getMethodName() + "Passed" + ".png"));
            String file = Global.reporter.addScreenCapture(Global.dir+ Global.ScrhooShotPath + result.getMethod().getMethodName() + "Passed" + ".png");
            Global.reporter.log(LogStatus.PASS, result.getMethod().getMethodName() + "test is passed", file);
            // test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable().getMessage()+"/n"+result.getThrowable());
        } catch (IOException e) {
            e.printStackTrace();
        }


        Global.reporter.getTest().setEndedTime(getTime(result.getStartMillis()));
        //test.setEndedTime(getTime(result.getEndMillis()));
        Global.reporter.getTest().setEndedTime(getTime(result.getEndMillis()));
    }

    public void onTestFailure(ITestResult result) {
        // System.out.println("on test failure");
       // reporter.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed");
        TakesScreenshot ts = (TakesScreenshot) Global.browser;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(Global.dir+ Global.ScrhooShotPath + result.getMethod().getMethodName() + ".png"));
            String file = Global.reporter.addScreenCapture(Global.dir+ Global.ScrhooShotPath + result.getMethod().getMethodName() + ".png");
            //reporter.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", );
            Global.reporter.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed",  "/n" + result.getThrowable()+file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Global.reporter.getTest().setEndedTime(getTime(result.getStartMillis()));
        //test.setEndedTime(getTime(result.getEndMillis()));
        Global.reporter.getTest().setEndedTime(getTime(result.getEndMillis()));
    }

    public void onTestSkipped(ITestResult result) {
        // System.out.println("on test skipped");
        Global.reporter.log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // System.out.println("on test sucess within percentage");
    }

    public void onStart(ITestContext context) {
        //System.out.println("on start");
        // browser = new ChromeDriver(); // Set the drivers path in environment variables to avoid code(System.setProperty())
        reports = new ExtentReports(Global.dir + Global.ReportPath + "/" + Global.ProjectName + FILE_NAME + "_" + df.format(new Date()) + ".html");
        reports.loadConfig(new File(Global.dir + Global.ExtentReportXMLPath));
        reports.addSystemInfo("Project Name", Global.ProjectName);
        reports.addSystemInfo("Browser", Global.Browser);



    }

    public void onFinish(ITestContext context) {
        //  System.out.println("on finish");
        Global.browser.close();
        reports.endTest(Global.reporter);
        reports.flush();
    }


    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}
