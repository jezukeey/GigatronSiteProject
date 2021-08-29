package pages;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class KorisnikPage extends BasePage {

    //Certificate of users data privacy
    @FindBy(className= "profile-card profileCardHeader")
    WebElement profileCardHeader;

    //Display user barcode
    @FindBy(className = "bqD")
    WebElement barcodesWrapper;

    //Get back to user data page
    @FindBy (className="active")
    WebElement pocetnaPage;

    // Korisnik dropdown menu
    @FindBy (className="user-identity-name")
    WebElement userDropDownMenu;

    //Log out button on login page
    @FindBy(className = "fa fa-sign-out")
    WebElement logOutButton;

    //Button for submitting login
    @FindBy(id="loginSubmit")
    WebElement logInButton;

    // Prijava click login button OVOVSI SINOC PREKOPIRAO IZ LOGINPAGE
    @FindBy(id = "loginSubmit")
    WebElement loginSubmitButton;

    // Method showing that if we are successfully logged in



    public KorisnikPage(ChromeDriver driver) {
        super(driver);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Došlo je do greške. Expected : "
                + Strings.LOGIN_URL + " Actual: " + currentUrl, currentUrl.equals(Strings.KORISNIK_URL));
    }

    public BasePage logOutButton() {
        logOutButton.click();
        return new BasePage(driver);
    }
    public BasePage clicklogInButton () {
        logInButton.click();
        return this;
    }
    public KorisnikPage clickSubmitButtonSuccess() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }

    public KorisnikPage clickLoginSubmitButton() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }

}
