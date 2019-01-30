package com.ag.framework.runner;



import com.ag.framework.core.DataManager;
import com.ag.framework.core.Global;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.ag.framework.utilities.LogsGenerator;
import com.ag.framework.utilities.Common;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.ag.framework.utilities.XMLUtility.autoRunXml;
import static com.ag.framework.utilities.XMLUtility.createXml;

public class Executor {
    public static String SheetName;
    public static String TestDataSheetRowNo;
    public static String CurrentRowNo;
    public static String AutoGenerate;
    public static String TestCaseDesc;
    public static String TestCaseID;
    public static String Function_Name;
    public static String RowNo;
    public static Hashtable<Integer, Hashtable<String, String>> DataRow = null;
    public static Hashtable<String, String> TestData1;
    public static Hashtable<String, String> EnvironmentDataTable;

    public static ExtentReports report;
    public static ExtentTest logger;
    public static Hashtable<String, String> TestData;
    static Hashtable<String, String> TestDataRow = null;
    static Hashtable<String, String> TestDatatemp;

    String screenshotPath = null;
   
    public static void InitializeTestScenarios(Hashtable<String, String> TestFunctionDataTable) throws FilloException {

        try {

            String _RowNO = "";
            //_RowNO = (Environment.GetEnvironmentVariable("qm_TestDataRowNo") == null ? "" : Environment.GetEnvironmentVariable("qm_TestDataRowNo"));
            Function_Name = TestFunctionDataTable.get("Function_Name".toUpperCase());
            SheetName = TestFunctionDataTable.get("TestDataSheetName".toUpperCase());
            TestDataSheetRowNo = TestFunctionDataTable.get("TestDataSheetRowNo".toUpperCase());
            TestCaseDesc = TestFunctionDataTable.get("TestCaseDescription".toUpperCase());
            //AutoGenerate = DataRow.get("Auto_Generate".toUpperCase());
            TestCaseID = TestFunctionDataTable.get("TestCaseID".toUpperCase());
            // TestSuite = DataRow.get("TestSuite".toUpperCase());
            CurrentRowNo = "";
            //TestData = DataManger.GetExcelDataTable("Select * from "+SheetName+" where RowID="+TestDataSheetRowNo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static Hashtable<Integer, Hashtable<String, String>> GetTestData(String Function) throws SQLException {
        String filePath = null;
        Recordset recordset = null;
        com.codoid.products.fillo.Connection connection = null;
        Hashtable<String, String> scenarios = null;
        scenarios = null;
        Hashtable<Integer, Hashtable<String, String>> DataTable = new Hashtable<Integer, Hashtable<String, String>>();

        LogsGenerator.info("Getting data with query:" + "Select * from TestScenarios where Function_Name='" + Function + "'");

        try
        {

            scenarios = DataManager.GetExcelDataTable("Select * from TestScenarios where Function_Name='" + Function + "'").get((1));

        } catch (Exception e)
        {
            LogsGenerator.error(e.getMessage());
        }





        if (scenarios != null) {
            try {
                InitializeTestScenarios(scenarios);
            } catch (Exception e) {
                LogsGenerator.error(e.getMessage());
            }

            List<String> RowsNo = Common.GetIterations(TestDataSheetRowNo, ",");
            if (RowsNo.size() > 0) {
                int rowNumber = 1;
                for (int i = 0; i < RowsNo.size(); i++) {
                    CurrentRowNo = RowsNo.get(i);
                    try {
                        LogsGenerator.info("Getting Total Test Data Rows to be executed:  " + "Select * from " + SheetName + " where RowID=" + CurrentRowNo);
                    } catch (Exception e) {
                        LogsGenerator.error(e.getMessage());
                    }


                    try {

                        // String filePath="D:\\My Docs\\QA Store\\Office\\Automation learning\\SeleniumFrameworkJava\\javadata.xlsx";
                        String dir = System.getProperty("user.dir");
                        filePath = dir + DataManager.getPropertyFile("fileName");
                    } catch (Exception e) {
                        LogsGenerator.error(e.getMessage());
                    }
                    ArrayList list = null;
                    int columns = 0;
                    //int rowNumber = 1;
                    try {
                        //Import Folio liberary
                        Fillo fillo = new Fillo();
                        connection = fillo.getConnection(filePath);
                        recordset = connection.executeQuery("Select * from " + SheetName + " where RowID=" + CurrentRowNo);


                        if (recordset.getCount() > 0) {
                            ArrayList<String> fnames = recordset.getFieldNames();
                            columns = fnames.size();
                            while (recordset.next()) {
                                Hashtable<String, String> DataRow = new Hashtable<String, String>();
                                for (int j = 0; j < columns; j++) {
                                    DataRow.put(recordset.getField(j).name(), recordset.getField(j).value());
                                }

                                DataTable.put(rowNumber, DataRow);
                                rowNumber++;
                            }
                        } else {
                            LogsGenerator.error("Invalid Test Sheet Name, QRY:  " + "Select * from " + SheetName + " where RowID=" + CurrentRowNo);
                        }
                    } catch (Exception e) {
                        LogsGenerator.error(e.getMessage());
                    }

                }
            }
        } else {
            LogsGenerator.error("No data found from with query: " + "Select * from TestScenarios where Function_Name='" + Function + "'");
        }
        return DataTable;
    }


    //initialize the excel data paramenters which will be filled by Recordset data


    public static void InitializeEnvironment(Hashtable<String, String> DataRow) throws FilloException {

        try {

            Global.RunEnvironment = DataRow.get("RunEnvironment");
            Global.RunType = DataRow.get("RunType");
            Global.ProjectName = DataRow.get("ProjectName");
            Global.Browser = DataRow.get("Browser");
            //AutoGenerate = DataRow.get("Auto_Generate".toUpperCase());
            Global.URL = DataRow.get("URL");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void BrowserLanuch(String sBrowser, String sURL) throws SQLException {
        try {
            if (sBrowser.equals("CHROME")) {
               //WebDriverManager.getInstance(ChromeDriver.class).setup();
                //ChromeDriverManager.getInstance().setup();
                System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver.exe");
                Global.browser = new ChromeDriver();
                Global.browser.manage().window().maximize();
                Global.browser.get(sURL);

            } else if (sBrowser.equals("IE")) {
                WebDriverManager.getInstance(InternetExplorerDriver.class).setup();
                Global.browser = new InternetExplorerDriver();
                Global.browser.manage().window().maximize();
                Global.browser.get(sURL);
            } else if (sBrowser.equals("FIREFOX")) {
                WebDriverManager.getInstance(FirefoxDriver.class).setup();
                Global.browser = new FirefoxDriver();
                Global.browser.manage().window().maximize();
                Global.browser.get(sURL);
            } else {
                System.out.println("Not Valid");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ExecuteSequence() throws Exception {

        LogsGenerator.info("Initializing the Test Environment Parameters wiht Query: " + "select Name,Value from Environment");

        try {
            EnvironmentDataTable = DataManager.GetExcelDictionary("select Name,Value from Environment");
        } catch (Exception e) {
            LogsGenerator.error("There is some error in data file: " + e + e.getMessage());
            System.out.println(e.getStackTrace() + e.getMessage());
        }

        if (EnvironmentDataTable != null) {
            InitializeEnvironment(EnvironmentDataTable);

            LogsGenerator.info("Getting TestScenarios Flag to be created in XML, Query: " + "Select * from TestScenarios where Execution_Flag= 'YES' ");
            try {
                DataRow = DataManager.GetExcelDataTable("Select * from TestScenarios where Execution_Flag= 'YES' ");
            } catch (Exception e) {
                LogsGenerator.error("There is some error in data file" + e.getMessage() + e.getStackTrace());

            }


            if (DataRow != null) {
                InitializeTestScenarios(DataRow.get(1));

                LogsGenerator.info("Creating XML with key set: " + DataRow.keySet());

                try {
                    createXml(DataRow);
                } catch (Exception e) {
                    LogsGenerator.error(e.getMessage());
                }

                //Thread.sleep(5000);
                LogsGenerator.info("Launching Browser with Browser: " + Global.Browser + " And URL: " + Global.URL);
                BrowserLanuch(Global.Browser, Global.URL);

                Thread.sleep(1000);

                LogsGenerator.info("Running Test Scenarios with Autorun.xml");

                autoRunXml();

            } else
                LogsGenerator.error("Unable to create XML, No Test Functions are selected with qry: " + "Select * from TestScenarios where Execution_Flag= 'YES' ");


        } else {
            LogsGenerator.error("Unable to launch browser, No data record found , Query: " + "select Name,Value from Environment");
        }

    }


    public static void ExecuteFunction(String sFunctionName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        try
        {
            Class testScenarios = Class.forName("test.application.scenarios.TestScenarios");
            Method method = testScenarios.getMethod(sFunctionName);
            method.invoke(testScenarios);
        }
        catch (Exception ex)
        {

        }


    }


}
