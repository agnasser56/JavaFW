package com.ag.framework.core;


import com.aventstack.extentreports.Status;
import com.ag.framework.utilities.ReporterX;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ActionX {

    public static void VerifyCheckPoint(Boolean IsPositiveScenario, List<WebElement> successMsg, List<WebElement> errorMessage) {

        SoftAssert softAssert = new SoftAssert();
        Assertion hardAssert = new Assertion();

        String newLine = "||"; //System.getProperty("line.separator");
        if (IsPositiveScenario == true) {


            if (successMsg.size() != 0) {

                ReporterX.log(Status.INFO, newLine+"Expected Result: Success Message Found" + newLine + "Actual Result: Success Message Found: "+successMsg.get(1) + "\n");
                softAssert.assertTrue(true, newLine+"Expected Result: Success Message Found" + "\n" + "Actual Result: Success Message Found" + newLine);

                softAssert.assertAll();
            } else {
                softAssert.assertTrue(false, newLine+"Expected Result: Success Message Found" + newLine + "Actual Result: Success Message not Found" + newLine);

                softAssert.assertAll();
            }
            //softAssert.assertEquals(successMsg,displayMessage);
        } else {
            if (errorMessage.size() != 0) {
                ReporterX.log(Status.INFO, newLine+"Expected Result: Error Message Found" + "\n" + "Actual Result: Error Message Found: "+errorMessage.get(1) + newLine);
                softAssert.assertTrue(true, newLine+"Expected Result: Error Message Found" + "\n" + "Actual Result: Error Message Found" + newLine);
                softAssert.assertAll();
            } else {
                softAssert.assertTrue(false, newLine+"Expected Result: Error Message Found" + "\n" + "Actual Result: Error Message not Found" + newLine);
                softAssert.assertAll();


            }
            //softAssert.assertEquals(errorMessage,displayMessage);
        }

    }

    public static boolean WaitUntilEnabled(WebElement el) {
        boolean isExist = false;
        final WebElement elemnt = el;
        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, 1);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.isEnabled();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilEnabled(WebElement el, long timeout) {
        boolean isExist = false;
        final WebElement elemnt = el;
        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, timeout);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.isEnabled();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static int ChildItemsCount(WebElement el, By locator) {
        int tempRes = 0;
        try
        {

            if (Exists(el))
            {

                tempRes =  el.findElements(locator).size();
            }
            else
            { tempRes = 0; }

            return tempRes;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static boolean WaitChildItemsCountGreaterThan(WebElement el, By locator, int comparValue, long timeout) {
        boolean tempRes = false;
        final WebElement elemnt = el;
        final By locat =  locator;
        final int comparVal = comparValue;
        try
        {

            if (Exists(el))
            {

                Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                WebDriverWait wait = new WebDriverWait(Global.browser, timeout);
                wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
                tempRes =wait.until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        try{return elemnt.findElements(locat).size() > comparVal;}catch(Exception e){return false;}
                    }
                });
            }
            else
            { tempRes = false; }

            return tempRes;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilTextPresent(WebElement el, String sText) {
        boolean isExist = false;
        final WebElement elemnt = el;
        final String vText = sText;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, 1);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.getText().trim().toUpperCase().contains(vText.trim().toUpperCase());}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilTextHasValue(WebElement el, long timeout) {
        boolean isExist = false;
        final WebElement elemnt = el;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, timeout);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.getAttribute("value").length() > 0;}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilTextPresent(WebElement el, String sText, long timeout) {
        boolean isExist = false;
        final WebElement elemnt = el;
        final String vText = sText;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, timeout);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.getText().trim().toUpperCase().contains(vText.trim().toUpperCase());}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilHidden(WebElement el) {
        boolean isExist = false;
        final WebElement elemnt = el;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, 1);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return ! elemnt.isDisplayed();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilHidden(WebElement el, long timeout) {
        boolean isExist = false;
        final WebElement elemnt = el;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, timeout);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return ! elemnt.isDisplayed();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean WaitUntilHiddenFromAPP(WebElement el, long timeout) {
        boolean isExist = false;
        final WebElement elemnt = el;

        try
        {
            Global.MobileAPP.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.MobileAPP, timeout);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return ! elemnt.isDisplayed();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.MobileAPP.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean Exists(WebElement element, long timeOut) {
        boolean isExist = false;
        final WebElement elemnt = element;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, timeOut);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.isDisplayed();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean ExistsInAPP(WebElement element, long timeOut) {
        boolean isExist = false;
        final WebElement elemnt = element;

        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, timeOut);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.isDisplayed();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean Exists(WebElement element) {
        boolean isExist = false;
        final WebElement elemnt = element;
        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, 1);
            wait.ignoring(StaleElementReferenceException.class,org.openqa.selenium.NoSuchElementException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try{return elemnt.isDisplayed();}catch(Exception e){return false;}
                }
            });

            return isExist;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    public static boolean ExistsInDOM(WebElement element) {
        boolean isExist = false;
        final WebElement el = element;

        // check that element is not null;
        if (el == null)
        {
            return false;
        }
        //check the element existance.
        try
        {
            Global.browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(Global.browser, 2);
            wait.ignoring(TimeoutException.class);
            isExist = wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    boolean returnVal = false;
                    try{returnVal = el.isDisplayed(); returnVal = true;}
                    catch(Exception EX)
                    {
                        if (EX.getClass().equals(org.openqa.selenium.NoSuchElementException.class)) returnVal = false;
                        else if (EX.getClass().equals(org.openqa.selenium.StaleElementReferenceException.class)) returnVal = false;
                        else if (EX.getMessage().toLowerCase().contains("could not find")) returnVal = false;
                    }
                    return returnVal;
                }
            });

            return isExist;
        }
        catch (Exception ex)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }
    }

    public static boolean JSClick(WebElement element) {
        boolean res = false;
        try
        {
            JavascriptExecutor  jsExec;
            jsExec = (JavascriptExecutor )Global.browser;
            jsExec.executeScript("arguments[0].click();", element);
            res = true;
        }
        catch (Exception ex) { }
        return res;
    }

    public static boolean ScrollToElement(WebElement element) {
        boolean res = false;
        try
        {
            JavascriptExecutor  jsExec;
            jsExec = (JavascriptExecutor )Global.browser;
            jsExec.executeScript("arguments[0].scrollIntoView(true);", element);
            res = true;
        }
        catch (Exception ex) { }
        return res;
    }

    public static WebElement ScrollToFindAppElementByID(String ID){
        WebElement element = null;
        try {
            By locatorRegisterViolation = new MobileBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(" + "new UiSelector().resourceId(\"" + ID + "\"));");
            element = Global.MobileAPP.findElement(locatorRegisterViolation);
        }catch (Exception ex){}
        return element;
    }

    public static void ScrollApp(WebElement element)
    {
        TouchActions action = new TouchActions(Global.MobileAPP);
        action.scroll(element, 10, 100);
        action.perform();
    }

    private static void scroll(int fromX, int fromY, int toX, int toY) {
        TouchAction touchAction = new TouchAction(Global.MobileAPP);
        touchAction.longPress(fromX, fromY).moveTo(toX, toY).release().perform();
    }

    public static void scrollDown() {
        try {
            //if pressX was zero it didn't work for me
            int pressX = Global.MobileAPP.manage().window().getSize().width / 2;
            // 4/5 of the screen as the bottom finger-press point
            int bottomY = Global.MobileAPP.manage().window().getSize().height * 4 / 5;
            // just non zero point, as it didn't scroll to zero normally
            int topY = Global.MobileAPP.manage().window().getSize().height / 9;
            //scroll with TouchAction by itself
            scroll(pressX, bottomY, pressX, topY);
        }catch (Exception ex){}
    }

    public static void scrollDown(int topY) {
        try {
            //if pressX was zero it didn't work for me
            int pressX = Global.MobileAPP.manage().window().getSize().width / 2;
            // 4/5 of the screen as the bottom finger-press point
            int bottomY = Global.MobileAPP.manage().window().getSize().height * 4 / 5;
            // just non zero point, as it didn't scroll to zero normally
            //int topY = Global.MobileAPP.manage().window().getSize().height / 9;
            //scroll with TouchAction by itself
            scroll(pressX, bottomY, pressX, topY);
        }catch (Exception ex){}
    }

    public static void dragDrop(WebElement dragElment,WebElement dragToElement) {
        try{
            //if pressX was zero it didn't work for me
            int toX = dragElment.getLocation().getX();
            // just non zero point, as it didn't scroll to zero normally
            int toY = dragToElement.getSize().height / 2;

            TouchAction touchAction = new TouchAction(Global.MobileAPP);
            touchAction.longPress(dragElment).moveTo(toX, toY).release().perform();

        }catch (Exception ex){}

    }

    public static void dragDrop(WebElement dragElment,int toX,int toY) {
        try{
            TouchAction touchAction = new TouchAction(Global.MobileAPP);
            touchAction.longPress(dragElment).moveTo(toX, toY).release().perform();
        }catch (Exception ex){}

    }


    public static void Select(WebElement element, int index)
    {
        // check that element is not null;
        if (element == null)
        {
            return;
        }

        try
        {

            if (Exists(element, 1))
            {
                Select selector = new Select(element);
                selector.selectByIndex(index);
            }
            else
            { return; }

        }
        catch (Exception e)
        {
            return;
        }

    }

    public static void Select(WebElement element, String text)
    {
        // check that element is not null;
        if (element == null)
        {
            return;
        }

        try
        {

            if (Exists(element, 1))
            {
                Select selector = new Select(element);
                selector.selectByVisibleText(text);
            }
            else
            { return; }

        }
        catch (Exception e)
        {
            return;
        }

    }

    public static void SelectOption(WebElement element, String sText)
    {

        // check that element is not null;
        if (element == null)
        {
            return;
        }

        try
        {

            List<WebElement> options = element.findElements(By.xpath("//option[contains(text(),'" + sText.trim() + "')]"));
            if (options.size() > 0)
                options.get(0).click();

        }
        catch (Exception ex)
        {

            return;
        }

    }
    /// <summary>
    /// Right click an elemnet
    /// </summary>
    /// <param name="element"></param>
    public static void RightClick(WebElement element)
    {
        Actions builder = new Actions(Global.browser);
        builder.contextClick(element);
        builder.perform();
    }

    /// <summary>
    /// MouseHover
    /// </summary>
    /// <param name="element"></param>
    public static void MouseHover(WebElement element)
    {
        Actions builder = new Actions(Global.browser);
        builder.moveToElement(element);
        builder.perform();
    }

    /// <summary>
    /// This function is waiting for any of 2 objects to be presented.
    /// </summary>
    /// <param name="driver"></param>
    /// <param name="Ele1"></param>
    /// <param name="Ele2"></param>
    /// <param name="iTimeOut"></param>
    /// <returns></returns>

    public static boolean Wait2ObjectExist(WebElement Ele1, WebElement Ele2, int iTimeOut)
    {
        boolean isExist = true;
        int x = 0;
        // check that element is not null;
        if (Ele1 == null || Ele2 == null)
        {
            return false;
        }

        // check the element existance.
        try
        {
            Global.browser.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            while (!(Exists(Ele1) || Exists(Ele2)))
            {
                Thread.sleep(1000);
                x = x + 1;
                if (x > iTimeOut)
                {
                    isExist = false;
                    break;
                }
            }
            if (!(Exists(Ele1) || Exists(Ele2)))
                isExist = false;

            return isExist;
        }
        catch (Exception ex)
        {
            return false;
        }
        finally
        {
            Global.browser.manage().timeouts().implicitlyWait(Global.browserTimeOut, TimeUnit.SECONDS);
        }

    }

    /// <summary>
    /// this function will be used to remove attribute of any element by taking the attribute name.
    /// </summary>
    /// <param name="element"></param>
    /// <param name="AttributeName"></param>

    public static void RemoveAttribute(WebElement element , String AttributeName)
    {
        JavascriptExecutor jsExec;
        String className = "";
        String ID = "";
        String name = "";
        String tagName = "";
        String getElementFunc = "";

        try
        {
            jsExec = (JavascriptExecutor)Global.browser;
            ID = element.getAttribute("id");
            className  = element.getAttribute("class");
            name = element.getAttribute("name");
            tagName = element.getTagName();

            if (ID.length() > 0)
            {
                getElementFunc = "getElementById('"+ ID + "')";
            }
            else if (name.length() > 0)
            {
                getElementFunc = "getElementsByName('" + name + "')";
            }
            else if (className.length() > 0)
            {
                getElementFunc = "getElementsByClassName('" + className + "')";
            }
            else if (tagName.length() > 0)
            {
                getElementFunc = "getElementsByTagName('" + tagName + "')";
            }

            try
            {
                if(getElementFunc.contains("getElementById"))
                {
                    jsExec.executeScript("document." + getElementFunc + ".removeAttribute('" + AttributeName + "');");
                }
                else
                    jsExec.executeScript("for (i = 0; i < document."+ getElementFunc + ".length; i++) { document."+ getElementFunc + "[i].removeAttribute('"+ AttributeName + "');}");
            }
            catch (Exception ex) { return; }

        }
        catch (Exception ex)
        { return; }
    }

    /// <summary>
    /// will be used to set an attribute value.
    /// </summary>
    /// <param name="element"></param>
    /// <param name="AttributeName"></param>
    /// <param name="AttributeValue"></param>

    public static void SetAttribute(WebElement element, String AttributeName, String AttributeValue)
    {
        JavascriptExecutor jsExec;
        String className = "";
        String ID = "";
        String name = "";
        String tagName = "";
        String getElementFunc = "";

        try
        {

            jsExec = (JavascriptExecutor) Global.browser;

            ID = element.getAttribute("id");
            className = element.getAttribute("class");
            name = element.getAttribute("name");
            tagName = element.getTagName();

            if (ID.length() > 0)
            {
                getElementFunc = "getElementById('" + ID + "')";
            }
            else if (name.length() > 0)
            {
                getElementFunc = "getElementsByName('" + name + "')";
            }
            else if (className.length() > 0)
            {
                getElementFunc = "getElementsByClassName('" + className + "')";
            }
            else if (tagName.length() > 0)
            {
                getElementFunc = "getElementsByTagName('" + tagName + "')";
            }

            try
            {
                if (getElementFunc.contains("getElementById"))
                {
                    jsExec.executeScript("document." + getElementFunc + ".setAttribute('" + AttributeName + "', '"+ AttributeValue + "');");
                }
                else
                    jsExec.executeScript("for (i = 0; i < document." + getElementFunc + ".length; i++) { document." + getElementFunc + "[i].setAttribute('" + AttributeName + "', '" + AttributeValue + "');}");
            }
            catch (Exception ex) { return; }

        }
        catch (Exception ex)
        { return; }
    }


    /// <summary>
    /// This function will be used to clear data and reset new data.
    /// </summary>
    /// <param name="element"></param>
    /// <param name="sValue"></param>

    public static void SetValue(WebElement element, String sValue)
    {

        // check that element is not null;
        if (element == null)
        {
            return;
        }

        try
        {

//            if (Exists(element))
//            {
//               try{ RemoveAttribute(element,"readonly");
//                RemoveAttribute(element,"disabled");}catch (Exception ex){}
            element.clear();
            element.sendKeys(sValue.trim());
//            }
//            else
//            { return; }

        }
        catch (Exception e)
        {
            return;
        }

    }

    /// <summary>
    /// this function is returning the text value of a table [Row][Column]
    /// </summary>
    /// <param name="element"></param>
    /// <param name="iRow"></param>
    /// <param name="iCell"></param>
    /// <returns></returns>

    public static String CellValue(WebElement element,int iRow,int iCell)
    {
        String CellValue = "";
        WebElement objCell = null;
        WebElement objRow = null;

        // check that element is not null;
        if (element == null)
        {
            return "";
        }

        if (element.getTagName().toUpperCase() != "TABLE")
        {
            return "";
        }

        try
        {

            if (Exists(element, 1))
            {
                objRow = element.findElements(By.tagName("tr")).get(iRow);
                objCell = objRow.findElements(By.tagName("td")).get(iCell);
                CellValue = objCell.getText().trim();
            }
            else
            { CellValue = ""; }

            return CellValue;
        }
        catch (Exception ex)
        {
            return "";
        }

    }

    /// <summary>
    /// this function will return the number of rows in a table
    /// </summary>
    /// <param name="element"></param>
    /// <returns></returns>

    public static int RowCount(WebElement element)
    {
        int iRowCount = 0;

        // check that element is not null;
        if (element == null)
        {
            return iRowCount;
        }

        if (element.getTagName().toUpperCase() != "TABLE")
        {
            return iRowCount;
        }

        try
        {

            if (Exists(element, 1))
            {
                iRowCount = element.findElements(By.tagName("tr")).size();

            }
            else
            { iRowCount = 0; }

            return iRowCount;
        }
        catch (Exception e)
        {
            return iRowCount;
        }

    }


    /// <summary>
    /// This function retrun the columns count for a specific row
    /// </summary>
    /// <param name="element"></param>
    /// <param name="iRow"></param>
    /// <returns></returns>

    public static int ColumnsCount(WebElement element , int iRow)
    {
        int iCount = 0;
        WebElement objRow = null;
        // check that element is not null;
        if (element == null)
        {
            return iCount;
        }

        if (element.getTagName().toUpperCase() != "TABLE")
        {
            return iCount;
        }

        try
        {

            if (Exists(element, 1))
            {
                // int count = element.findElements(By.tagName("tr")).size();
                objRow = element.findElements(By.tagName("tr")).get(iRow);
                iCount = objRow.findElements(By.tagName("td")).size();

            }
            else
            { iCount = 0; }

            return iCount;
        }
        catch (Exception e)
        {
            return iCount;
        }

    }


    /// <summary>
    /// This ufnction will return the child item of a table cell and tking locator and index
    /// </summary>
    /// <param name="element"></param>
    /// <param name="iRow"></param>
    /// <param name="iCell"></param>
    /// <param name="Locator"></param>
    /// <param name="iIndex"></param>
    /// <returns></returns>

    public static WebElement CellChildElement(WebElement element, int iRow, int iCell, By Locator, int iIndex)
    {

        WebElement objCell = null;
        WebElement objRow = null;
        WebElement objChild = null;

        // check that element is not null;
        if (element == null)
        {
            return null;
        }

        if (element.getTagName().toUpperCase() != "TABLE")
        {
            return null;
        }

        try
        {

            if (Exists(element, 1))
            {
                objRow = element.findElements(By.tagName("tr")).get(iRow);
                objCell = objRow.findElements(By.tagName("td")).get(iCell);
                objChild = objCell.findElements(Locator).get(iIndex);
            }
            else
            { objChild = null; }

            return objChild;
        }
        catch (Exception e)
        {
            return null;
        }

    }





}