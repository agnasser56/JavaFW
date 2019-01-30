package application.pages;


import com.ag.framework.core.ActionX;
import com.ag.framework.utilities.ReporterX;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Hashtable;

import static com.ag.framework.core.Global.browser;

public class LoginPage
{

    @FindBy(name = "userName")
    WebElement txtUserNam;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(name = "login")
    WebElement btnLogin;


    @FindBy(xpath = " //a[contains(text(),'SIGN-OFF')]")
    WebElement btnLoggedin;

    public LoginPage()
    {
        PageFactory.initElements(browser, this);
    }

    public void LoginUser(Hashtable<String, String> TestData)
    {
        try
        {
            ActionX.SetValue(txtUserNam,TestData.get("User".toUpperCase()));
            ActionX.SetValue(txtPassword,TestData.get("Password".toUpperCase()));
            btnLogin.click();
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        ReporterX.VerifyCheckPoint(btnLoggedin,"Login To System");
    }

    public void LogoutUser()
    {
        try
        {
            btnLoggedin.click();
            if(txtUserNam.isDisplayed())
                Assert.assertTrue(true);
            else
                Assert.assertTrue(false);

        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }

}
