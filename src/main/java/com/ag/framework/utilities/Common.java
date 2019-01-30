package com.ag.framework.utilities;

import com.ag.framework.core.DataManager;
import com.ag.framework.core.Global;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Common {

    private static SoftAssert softAssert = new SoftAssert();
    private Assertion hardAssert = new Assertion();

    private static void SwapArgs(String min, String max) {
        String temp;
        temp = min;
        min = max;
        max = temp;
    }

    public static List<String> GetIterations(String sIterations, String Separator) {
        List<String> arrIterations = new ArrayList<String>();
        try {
            String min, max;
            String[] swap;
            int i, j;
            String[] arrRange;
            String[] arrTmp = sIterations.split(Separator);
            for (i = 0; i < arrTmp.length; i++) {
                arrRange = arrTmp[i].split("-");
                if (arrRange.length > 1) {
                    min = arrRange[0];
                    max = arrRange[1];
                    if (Integer.parseInt(min) > Integer.parseInt(max)) {
                        String temp;
                        temp = min;
                        min = max;
                        max = temp;
                        // SwapArgs(min, max);
                    }
                    for (j = Integer.parseInt(min); j <= Integer.parseInt(max); j++) {
                        arrIterations.add(String.valueOf(j));
                    }
                } else
                    arrIterations.add(arrTmp[i]);
            }
            return arrIterations;
        } catch (Exception ex) {
            return arrIterations;
        }
    }


    public static boolean IsExist(List<WebElement> webElement) {
        if (webElement.size() != 0)
            return true;
        else
            return false;

    }


    public static void VerifyCheckPoint(Boolean IsPositiveScenario, List<WebElement> successMsg, List<WebElement> errorMessage) {
        String newLine = System.getProperty("line.separator");
        if (IsPositiveScenario == true) {


            if (successMsg.size() != 0) {

                Global.reporter.log(LogStatus.INFO, newLine+"Expected Result: Success Message Found" + newLine + "Actual Result: Success Message Found: "+successMsg.get(1) + "\n");
                softAssert.assertTrue(true, newLine+"Expected Result: Success Message Found" + "\n" + "Actual Result: Success Message Found" + newLine);

                softAssert.assertAll();
            } else {
                softAssert.assertTrue(false, newLine+"Expected Result: Success Message Found" + newLine + "Actual Result: Success Message not Found" + newLine);

                softAssert.assertAll();
            }
            //softAssert.assertEquals(successMsg,displayMessage);
        } else {
            if (errorMessage.size() != 0) {
                Global.reporter.log(LogStatus.INFO, newLine+"Expected Result: Error Message Found" + "\n" + "Actual Result: Error Message Found: "+errorMessage.get(1) + newLine);
                softAssert.assertTrue(true, newLine+"Expected Result: Error Message Found" + "\n" + "Actual Result: Error Message Found" + newLine);
                softAssert.assertAll();
            } else {
                softAssert.assertTrue(false, newLine+"Expected Result: Error Message Found" + "\n" + "Actual Result: Error Message not Found" + newLine);
                softAssert.assertAll();
            }
            //softAssert.assertEquals(errorMessage,displayMessage);
        }

    }

    public static String GetTestEnvironment(String Name) throws SQLException {
        String GetName = null;
        String GetValue = null;
        try {
            Hashtable<Integer, Hashtable<String, String>> data = null;
            data = DataManager.GetExcelDataTable("Select * from Environment");
            for (int i = 1; i < data.size(); i++) {
                GetName = data.get(i).get("Name".toUpperCase());
                GetValue = data.get(i).get("Value".toUpperCase());
                if (GetName.toUpperCase().equals(Name.toUpperCase())) {
                    return GetValue;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return GetValue + new IllegalArgumentException("Invalid record: " + Name);
    }


    public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/ScreenShots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }


}
