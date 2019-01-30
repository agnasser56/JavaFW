package application.pages;


import com.aventstack.extentreports.Status;
import com.ag.framework.core.ActionX;
import com.ag.framework.core.Global;
import com.ag.framework.utilities.ReporterX;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Hashtable;

public class RegistrationPage
{
    @FindBy(xpath = "//a[text()='REGISTER']")
    WebElement lnkRegister;

    @FindBy(name = "firstName")
    WebElement txtFirstName;

    @FindBy(name = "lastName")
    WebElement txtLastName;

    @FindBy(name = "phone")
    WebElement txtPhone;

    @FindBy(name = "userName")
    WebElement txtEmail;

    @FindBy(name = "address1")
    WebElement txtAddress;

    @FindBy(name = "city")
    WebElement txtCity;

    @FindBy(name = "state")
    WebElement txtState;

    @FindBy(name = "postalCode")
    WebElement txtpostalCode;

    @FindBy(name = "country")
    WebElement ddlCountry;

    @FindBy(name = "email")
    WebElement txtUserName;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(name = "confirmPassword")
    WebElement txtConfirmPwd;

    @FindBy(xpath = "//input[@name='register']")
    WebElement btnRegister;

    @FindBy(xpath = " //b[contains(text(),'Your user name')]")
    WebElement msgSuccess;

    public RegistrationPage()
    {
        PageFactory.initElements(browser, this);
    }

    public void RegisterUser(Hashtable<String, String> TestData)
    {
        try
        {
            lnkRegister.click();
            txtFirstName.sendKeys(TestData.get("FirstName".toUpperCase()));
            txtLastName.sendKeys(TestData.get("LastName".toUpperCase()));
            txtPhone.sendKeys(TestData.get("Phone".toUpperCase()));
            txtEmail.sendKeys(TestData.get("Email".toUpperCase()));
            txtAddress.sendKeys(TestData.get("Address".toUpperCase()));
            txtCity.sendKeys(TestData.get("City".toUpperCase()));
            txtState.sendKeys(TestData.get("State".toUpperCase()));
            txtpostalCode.sendKeys(TestData.get("PostalCode".toUpperCase()));

            Select countrySelector = new Select(ddlCountry);
            countrySelector.selectByVisibleText(TestData.get("Country".toUpperCase()));

            txtUserName.sendKeys(TestData.get("UserName".toUpperCase()));
            txtPassword.sendKeys(TestData.get("Password".toUpperCase()));
            txtConfirmPwd.sendKeys(TestData.get("Password".toUpperCase()));
            btnRegister.click();

            if(msgSuccess.isDisplayed())
                Assert.assertTrue(true);
            else
                Assert.assertTrue(false);

        }
        catch (Exception e){System.out.println(e.getStackTrace());}
    }


    public void RegisterUserAdvanced()
    {
        try
        {
            FillRegistrationPage(Global.DataSet);

            if(msgSuccess.isDisplayed())
                ReporterX.log(Status.PASS,"Registration Successful");
            else
                ReporterX.log(Status.FAIL,"Registration Failed");

        }
        catch (Exception e){System.out.println(e.getStackTrace());}
    }
    public void FillRegistrationPage(Hashtable<String, String> TestData)
    {
        lnkRegister.click();
        ActionX.SetValue(txtFirstName,TestData.get("FirstName".toUpperCase()));
        ActionX.SetValue(txtLastName, TestData.get("LastName".toUpperCase()));
        ActionX.SetValue(txtPhone,TestData.get("Phone".toUpperCase()));
        ActionX.SetValue(txtEmail,TestData.get("Email".toUpperCase()));
        ActionX.SetValue(txtAddress,TestData.get("Address".toUpperCase()));
        ActionX.SetValue(txtCity,TestData.get("City".toUpperCase()));
        ActionX.SetValue(txtState,TestData.get("State".toUpperCase()));
        ActionX.SetValue(txtpostalCode,TestData.get("PostalCode".toUpperCase()));
        ActionX.Select(ddlCountry,TestData.get("Country".toUpperCase()));
        ActionX.SetValue(txtUserName,TestData.get("UserName".toUpperCase()));
        ActionX.SetValue(txtPassword,TestData.get("Password".toUpperCase()));
        ActionX.SetValue(txtConfirmPwd,TestData.get("Password".toUpperCase()));
        btnRegister.click();
    }
}
