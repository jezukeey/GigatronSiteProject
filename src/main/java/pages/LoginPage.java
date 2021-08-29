package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    //Page Prijava
    @FindBy(id = "Login")
    WebElement loginForm;


    // Enter userName in filed
    @FindBy(id = "email")
    WebElement emailField;

    //  Enter password in field
    @FindBy(id = "password")
    WebElement passwordField;

    //Log out button on login page
    @FindBy(className = "fa fa-sign-out")
    WebElement logOutButton;

    // Prijava click login button
    @FindBy(id = "loginSubmit")
    WebElement loginSubmitButton;



    // Fill up form container showing that you need to enter email and password
    @FindBy(tagName = "Ulogujte se koristeći Vaš email i lozinku.")
    WebElement loginMessageContainer;

    // Massage after successfully logged in
    @FindBy(className = "profile-card")
    WebElement loggedInUser;

    // Log in page ERROR message
    @FindBy(linkText = "class=lass=\"page-message message-error\"")
    WebElement errorMessageContainer;

    @FindBy(className = "form-control-info")
    WebElement zaboravljenaLozinkaerrorMessage;

    ChromeDriver driver = null;
    public LoginPage (ChromeDriver driver) {
        driver.get("https://gigatron.rs/login/");
        PageFactory.initElements(driver, this);
        this.driver=driver;
    }

    public LoginPage enterEmailAddress(String emailAddress) {
        //Check first if webelement is displayed on a web page
        assert emailField.isDisplayed();
        emailField.sendKeys(emailAddress);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.sendKeys( password );
        return this;
    }

    public KorisnikPage clickSubmitButtonSuccess() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }
    public BasePage logOutButton () {
        logOutButton.click();
        return new BasePage(driver);
    }

    public KorisnikPage clickLoginSubmitButton() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }


    public String getErrorMessage() {
        return errorMessageContainer.getText();
    }

    //    // Ovaj ces da koristis za side bar burger button
//    public LoginPage clickLogout() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//        wait.until(ExpectedConditions.visibilityOf(logOutSideBarLink));
//        logOutSideBarLink.click();
//        return new LoginPage(driver);
//    }

//    public LoginPage clickLogOut() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//        wait.until(ExpectedConditions.visibilityOf(logOutSideBarLink));
//        logOutSideBarLink.click();
//        return new LoginPage(driver);
//    }

//    public String getErrorMessage() {
//        String errorMessage = errorMessageContainer.getText();
//        return errorMessage;
//    }
}