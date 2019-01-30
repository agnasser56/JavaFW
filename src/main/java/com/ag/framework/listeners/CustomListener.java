package com.ag.framework.listeners;


import com.ag.framework.core.DataManager;
import com.ag.framework.core.Global;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.model.ScreenCapture;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;

import static com.ag.framework.utilities.Common.*;
import static com.ag.framework.runner.Executor.*;



public class CustomListener implements IAnnotationTransformer, IInvokedMethodListener {
    //IReporter Variables
    //---------------------
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "_Test Summary Report.html";

    //InvokedMethodListener Variables
    //---------------------
    //---------------------
    public static Hashtable<String, String> data;
    public static com.relevantcodes.extentreports.ExtentTest test;
    //ITest Listener Variables
    //---------------------
    protected static com.relevantcodes.extentreports.ExtentReports reports;
    static String dir = System.getProperty("user.dir");
    static String ReportPath = DataManager.getPropertyFile("ReportPath");
    private static int itr = 1;
    String ExtentReportXMLPath = DataManager.getPropertyFile("ExtentReportXMLPath");
    ScreenCapture screenCapture;
    String ScrhooShotPath = DataManager.getPropertyFile("ScreenShots");
    private int InvokedMethodListenerCounter = 1;
    private Hashtable<Integer, Hashtable<String, String>> TestDataSet;

    //---------------------
    private String InvokedMethodListenerOldFunName = "";
    private ExtentReports extent;
    //-------------------
//IAnnotationTransformer Variables
    //---------------------
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hhmmss"); // add S if you need milliseconds
    private int ITestListenerCounter = 1;
    private String ITestListenerOldFunName = "";
    //-----------------------


    //IAnnotationTransformer Methods Start
   // @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {

        for (int i = 1; i <= DataRow.size(); i++) {

            String Fname = DataRow.get(i).get("Function_Name".toUpperCase());
            String TestScenariosRow = DataRow.get(i).get("TestDataSheetRowNo".toUpperCase());
            try {
                //InitializeTestScenarios(DataRow.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Fname.equals(method.getName())) {

                List<String> RowsNo = GetIterations(TestScenariosRow, ",");
                iTestAnnotation.setInvocationCount(RowsNo.size());
                iTestAnnotation.setDescription(DataRow.get(i).get("TestCaseDescription".toUpperCase()));
            }
        }

    }
//IAnnotationTransformer Methods End


    //InvokedMethodListener Methods Start
    //@Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        String Func = method.getTestMethod().getMethodName();
        try {

            if (InvokedMethodListenerOldFunName.equals(Func)) {
                InvokedMethodListenerCounter++;
            } else {
                TestDataSet=GetTestData(Func);
                InvokedMethodListenerCounter = 1;
            }
            //System.out.println("Get Test Data for method: " + method.getTestMethod().getMethodName() + "With iteration: " + InvokedMethodListenerCounter);
            Global.DataSet = TestDataSet.get(InvokedMethodListenerCounter);
            InvokedMethodListenerOldFunName = Func;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //@Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        //System.out.println("after invocation : " + method.getTestMethod().getMethodName() + InvokedMethodListenerCounter);

    }

    //InvokedMethodListener Methods End


    //IReporter Methods Start
/*    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {


        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            init();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
                buildTestNodes(context.getPassedTests(), Status.PASS);

            }
        }

        for (String s : Reporter.getOutput()) {
            extent.setTestRunnerOutput(s);
        }

        extent.flush();
    }

    private void init() {

        // String filename = FILE_PATH + df.format(new Date()) + "." + FILE_EXTENSION;
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(dir + ReportPath + "/" + df.format(new Date()) + "_" + ProjectName + FILE_NAME);

        htmlReporter.config().setDocumentTitle("Test Summary Report: " + ProjectName);
        htmlReporter.config().setReportName("Test Summary Report: " + ProjectName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setTheme(Theme.STANDARD);

        // htmlReporter.onScreenCaptureAdded();


        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);


    }

    private void buildTestNodes(IResultMap tests, Status status) {
        ExtentTest test;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.createTest(result.getMethod().getMethodName());

                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                } else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }*/
//IReporter Methods End


//ITestListener Methods Start
/*
public void onTestStart(ITestResult result) {
    System.out.println("on test start");

    String Func = result.getMethod().getMethodName();

    if (ITestListenerOldFunName .equals(Func)) {
        ITestListenerCounter++;
        test = reports.startTest(result.getMethod().getMethodName()+"_Iteration_"+ITestListenerCounter,result.getMethod().getDescription());
    } else {
        ITestListenerCounter = 1;
        test = reports.startTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
    }
    // test = reports.startTest(result.getMethod().getMethodName()+"_Iteration_"+j,result.getMethod().getDescription());
    ITestListenerOldFunName  = Func;





    test.log(LogStatus.INFO, result.getMethod().getMethodName() + "test is started");
}
    public void onTestSuccess(ITestResult result) {
        System.out.println("on test success");
        test.log(LogStatus.PASS, result.getMethod().getMethodName() + "test is passed");

        TakesScreenshot ts = (TakesScreenshot) browser;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(ScrhooShotPath + result.getMethod().getMethodName()+"Passed" + ".png"));
            String file = test.addScreenCapture(ScrhooShotPath + result.getMethod().getMethodName()+"Passed" + ".png");
            test.log(LogStatus.PASS, result.getMethod().getMethodName() + "test is passed", file);
            // test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable().getMessage()+"/n"+result.getThrowable());
        } catch (IOException e) {
            e.printStackTrace();
        }


        test.getTest().setEndedTime(getTime(result.getStartMillis()));
        //test.setEndedTime(getTime(result.getEndMillis()));
        test.getTest().setEndedTime(getTime(result.getEndMillis()));
    }
    public void onTestFailure(ITestResult result) {
        System.out.println("on test failure");
        test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed");
        TakesScreenshot ts = (TakesScreenshot) browser;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(ScrhooShotPath + result.getMethod().getMethodName()+"Failed" + ".png"));
            String file = test.addScreenCapture(ScrhooShotPath + result.getMethod().getMethodName()+"Failed"  + ".png");
            test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", file);
            test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable().getMessage()+"/n"+result.getThrowable());
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.getTest().setEndedTime(getTime(result.getStartMillis()));
        //test.setEndedTime(getTime(result.getEndMillis()));
        test.getTest().setEndedTime(getTime(result.getEndMillis()));
    }
    public void onTestSkipped(ITestResult result) {
        System.out.println("on test skipped");
        test.log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped");
    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("on test sucess within percentage");
    }
    public void onStart(ITestContext context) {
        System.out.println("on start");
        // browser = new ChromeDriver(); // Set the drivers path in environment variables to avoid code(System.setProperty())
        reports = new com.relevantcodes.extentreports.ExtentReports(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-ms").format(new Date()) + "reports.html");
        reports.loadConfig(new File(dir+ExtentReportXMLPath));


    }
    public void onFinish(ITestContext context) {
        System.out.println("on finish");
        browser.close();
        reports.endTest(test);
        reports.flush();
    }


    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
*/

//ITestListener Methods End

}
