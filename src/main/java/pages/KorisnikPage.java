package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

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
    @FindBy(xpath="//i[contains(@class, 'fa-sign-out' ) ]")
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
        Assert.assertFalse("Došlo je do greške. Expected : "
                + Strings.LOGIN_URL + " Actual: " + currentUrl, currentUrl.equals(Strings.KORISNIK_URL));
    }




//    public WebElement logOutButton() {
//        logOutButton.click();
//        return new BasePage(driver);
//    }

    public KorisnikPage clickSubmitButtonSuccess() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }

    public KorisnikPage clickLoginSubmitButton() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }


}
