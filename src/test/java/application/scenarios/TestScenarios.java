package application.scenarios;

import application.pages.BookFlightPage;
import application.pages.LoginPage;
import com.ag.framework.core.DataManager;
import com.ag.framework.core.Global;
import com.ag.framework.utilities.LogsGenerator;
import org.testng.annotations.Test;
import application.pages.RegistrationPage;

import java.util.Hashtable;

public class TestScenarios
{
    private static Hashtable<Integer, Hashtable<String, String>> data;

    @Test
    public static void VerifyUserRegistration() throws  Exception
    {
        RegistrationPage regPage = new RegistrationPage();
        try
        {
            regPage.RegisterUser(Global.DataSet);

        }
        catch (Exception e)
        {
            LogsGenerator.error(e.getMessage());
        }
    }

    @Test
    public static void VerifyUserLogin() throws  Exception {
        LoginPage logPage = new LoginPage();
        try
        {
            logPage.LoginUser(Global.DataSet);

        } catch (Exception e)
        {
            LogsGenerator.error(e.getMessage());
        }
    }

    @Test
    public  static void VerifyFlightBooking() throws Exception
    {
        try
        {
            RegistrationPage regPage  = new RegistrationPage();
            LoginPage loginPage  = new LoginPage();
            BookFlightPage bookFlightPage = new BookFlightPage();

            //Register User
            //Hashtable<String, String> registrationTestData = new Hashtable<String, String>();;
            //registrationTestData = DataManager.GetExcelDataTable("select * from Registration where RowID="+Global.DataSet.get("RegistrationRow".toUpperCase())).get(1);

            //regPage.RegisterUser(registrationTestData);

            //After registration, by default, the user is logged in
            //loginPage.LogoutUser();

            ////Login User
            Hashtable<String, String> loginTestData;
            loginTestData = DataManager.GetExcelDataTable("select * from Login where RowID=" + Global.DataSet.get("LoginRow".toUpperCase())).get(1);
            loginPage.LoginUser(loginTestData);

            //Book Flight
            bookFlightPage.BookFlight(Global.DataSet);

        }
        catch (Exception e)
        {
            LogsGenerator.error(e.getMessage());
        }
    }
}