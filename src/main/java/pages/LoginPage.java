package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public static String VALID_USER="VALID_USER";


    //  Ulogujte se koristeci Vas email i lozinku.
    @FindBy(id = "hd-reg")
    WebElement prijavaField;

    // E-mail field
    @FindBy(id = "email")
    WebElement EmailField;

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

    ChromeDriver driver = null;
    public LoginPage (ChromeDriver driver) {
        this.driver.get("https://www.gigatron.rs/login");
        PageFactory.initElements(this.driver, this);
        this.driver = this.driver;
    }


    public static void enterEmailAddress(String validUser) {
    }

    public  LoginPage enterEmailAddress(String emailField, WebElement email) {
        assert email.isDisplayed();
        email.sendKeys(emailField);
        return this;
    }


    public LoginPage clickSubmitButtonSuccess() {
        loginSubmitButton.click();
        return new LoginPage(driver);
    }

    public static void enterPassword(String validPassword) {
    }

//    public String getErrorMessage() {
//        String errorMessage = errorMessageContainer.getText();
//        return errorMessage;
//    }
}