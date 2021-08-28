package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    public static String VALID_USER="VALID_USER";
    public static String VALID_PASSWORD="VALID_PASSWORD";


    //  Ulogujte se koristeci Vas email i lozinku.
    @FindBy(id = "hd-reg")
    WebElement prijavaField;

    // E-mail field
    @FindBy(id = "email")
    WebElement emailField;

    //  Password field
    @FindBy(id = "password")
    WebElement passwordField;

    // Prijava click login button
    @FindBy(id = "loginSubmit")
    WebElement loginSubmitButton;

    @FindBy(tagName = "Ulogujte se koristeći Vaš email i lozinku.")
    WebElement loginMessageContainer;

    @FindBy(className = "fa fa-sign-out")
    WebElement logOutButton;

    // Massage after successfully logged in
    @FindBy(className = "profile-card")
    WebElement loggedInUser;

    @FindBy(linkText = "class=\"page-message message-error\" = 'Doslo je do greske. Korisnik nije pronaden ili je neispravna lozinka.']")
    WebElement errorMessageContainer;

    @FindBy(className = "form-control-info")
    WebElement zaboravljenaLozinkaerrorMessage;

    ChromeDriver driver=null;
    public LoginPage (ChromeDriver driver) {
        driver.get("https://gigatron.rs/login/");
        PageFactory.initElements(driver, this);
        this.driver=driver;
    }

    public LoginPage enterEmailAddress (String emailAdresse) {
        //Check first if webelement is displayed on a web page
        assert emailField.isDisplayed();
        emailField.sendKeys(emailAdresse);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }


    public KorisnikPage clickSubmitButtonSuccess() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }
    public LoginPage clickSubmitButtonFailure() {
        loginSubmitButton.click();
        return this;
    }
    public String getErrorMessage() {
        String errorMessage = errorMessageContainer.getText();
        return errorMessage;
    }

//    public String getErrorMessage() {
//        String errorMessage = errorMessageContainer.getText();
//        return errorMessage;
//    }
}