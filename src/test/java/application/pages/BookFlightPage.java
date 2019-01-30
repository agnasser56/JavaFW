package application.pages;


import com.ag.framework.core.ActionX;
import com.ag.framework.utilities.ReporterX;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.Hashtable;
import static com.ag.framework.core.Global.browser;

public class BookFlightPage
{
    @FindBy(xpath = "//a[contains(@href,'mercuryreservation')]")
    WebElement lnkBookFlight;

    @FindBy(xpath = "//input[@name='tripType'][1]")
    WebElement rdbRoundTripType;

    @FindBy(xpath = "//input[@name='tripType'][2]")
    WebElement rdbOneWayTripType;

    @FindBy(name = "passCount")
    WebElement ddlNoOfPassengers;

    @FindBy(name = "fromPort")
    WebElement ddlDepartingCity;

    @FindBy(name = "fromMonth")
    WebElement ddlTravelDateMonth;

    @FindBy(name = "fromDay")
    WebElement ddlTravelDateDay;

    @FindBy(name = "toPort")
    WebElement ddlArrivingCity;

    @FindBy(name = "toMonth")
    WebElement ddlReturningDateMonth;

    @FindBy(name = "toDay")
    WebElement ddlReturningDateDay;

    @FindBy(name = "findFlights")
    WebElement btnContinue;

    @FindBy(xpath = "//img[contains(@src,'selectflight')]")
    WebElement imgSelectFlight;

    @FindBy(xpath = "//input[contains(@value,'Blue Skies Airlines$360') and @type='radio']")
    WebElement rdbOutFlight;

    @FindBy(xpath = "//input[contains(@value,'Blue Skies Airlines$631') and @type='radio']")
    WebElement rdbInFlight;

    @FindBy(name = "reserveFlights")
    WebElement btnReserveFlight;

    @FindBy(name = "passFirst0")
    WebElement txtFirstName;

    @FindBy(name = "passLast0")
    WebElement txtLastName;

    @FindBy(name = "creditnumber")
    WebElement txtCreditNumber;

    @FindBy(name = "buyFlights")
    WebElement btnBuyFlights;

    @FindBy(xpath = " //font[contains(text(),'itinerary has been booked!')]")
    WebElement msgSuccess;

    public BookFlightPage()
    {
        PageFactory.initElements(browser, this);
    }



    public void NavigateToBookFlightPage()
    {
        try
        {
            lnkBookFlight.click();
            if(rdbRoundTripType.isDisplayed())
                Assert.assertTrue(true);
            else
                Assert.assertTrue(false);
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }


    public void BookFlight(Hashtable<String, String> TestData)
    {
        try
        {
            NavigateToBookFlightPage();
            FillFindFlightPage(TestData);
            FillFlightDetailsPage(TestData);
            FillPassengerDetailsPage(TestData);

        }
        catch (Exception e){System.out.println(e.getStackTrace());}
    }


    public void FillFindFlightPage(Hashtable<String, String> TestData)
    {
        try
        {
            if(TestData.get("Type".toUpperCase()).equals("RoundTrip"))
                rdbRoundTripType.click();
            else
                rdbOneWayTripType.click();

            ActionX.Select(ddlDepartingCity,TestData.get("DepartingFrom".toUpperCase()));
            ActionX.Select(ddlArrivingCity,TestData.get("ArrivingIn".toUpperCase()));
            ActionX.Select(ddlNoOfPassengers,TestData.get("Passengers".toUpperCase()));
            ActionX.Select(ddlTravelDateMonth,TestData.get("FromMonth".toUpperCase()));
            ActionX.Select(ddlTravelDateDay,TestData.get("FromDay".toUpperCase()));
            ActionX.Select(ddlReturningDateMonth,TestData.get("ToMonth".toUpperCase()));
            ActionX.Select(ddlReturningDateDay,TestData.get("ToDay".toUpperCase()));
            btnContinue.click();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
        ReporterX.VerifyCheckPoint(imgSelectFlight,"Fill Find Flight Page");

    }

    public void FillFlightDetailsPage(Hashtable<String, String> TestData)
    {
        try
        {
            rdbOutFlight.click();
            rdbInFlight.click();
            btnReserveFlight.click();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
        ReporterX.VerifyCheckPoint(txtFirstName,"Fill Filght Details Page");
    }

    public void FillPassengerDetailsPage(Hashtable<String, String> TestData)
    {
        try
        {
            ActionX.SetValue(txtFirstName,TestData.get("FirstName".toUpperCase()));
            ActionX.SetValue(txtLastName,TestData.get("LastName".toUpperCase()));
            ActionX.SetValue(txtCreditNumber,TestData.get("CreditNumber".toUpperCase()));
            btnBuyFlights.click();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
        ReporterX.VerifyCheckPoint(msgSuccess,"Book Fligh Results");
    }


}
