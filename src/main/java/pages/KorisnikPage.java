package pages;

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

    /**
     * Priprema objekat za rad sa već otvorenom stranicom.
     * Prijava je prethodno potvrđena kroz opciju „Odjavi se“.
     */
    public KorisnikPage(ChromeDriver driver) {
        // Poziva konstruktor BasePage:
        // čuva driver i inicijalizuje PageFactory elemente.
        super(driver);
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
