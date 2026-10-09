package pages;

import org.openqa.selenium.By;
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

    // Poruka kojom login forma prijavljuje grešku.
    @FindBy(css = ".vtex-login-2-x-formError[role='alert']")
    private WebElement loginError;

    // Enter userName in filed
    @FindBy(css = ".vtex-login-2-x-inputContainerEmail input")
    WebElement emailField;

    //  Enter password in field
    @FindBy(css = "input[type='password']")
    WebElement passwordField;

    //Log out button on login page
    @FindBy(xpath="//i[contains(@class, 'fa-sign-out' ) ]")
    WebElement logOutButton;

    // Prijava click login button
    @FindBy(css = ".vtex-login-2-x-contentFormVisible button[type='submit']")
    WebElement loginSubmitButton;

    // @FindBy opisuje kako Selenium pronalazi element.
    // XPath traži span sa tekstom "Petar" koji nema unutrašnje elemente.
    // Ime prijavljenog test naloga koje otvara meni.
    @FindBy(xpath = "//span[normalize-space()='Petar' and not(*)]")
    private WebElement accountMenu;

    // private znači da se polju pristupa samo unutar ove klase.
    // WebElement predstavlja element na stranici.
    // logoutOption je naziv promenljive koja predstavlja dugme „Odjavi se“.
    @FindBy(xpath = "//button[normalize-space()='Odjavi se']")
    private WebElement logoutOption;

    // Fill up form container showing that you need to enter email and password
    @FindBy(tagName = "Ulogujte se koristeći Vaš email i lozinku.")
    WebElement loginMessageContainer;

    // Massage after successfully logged in
    @FindBy(className = "profile-card")
    WebElement loggedInUser;

    // Log in page ERROR message
    @FindBy(xpath = ".//*[ contains (text(), 'Došlo je do greške') ]")
    WebElement errorMessageContainer;

    // Zaboravljena lozinka
    @FindBy(className = "form-control-info")
    WebElement zaboravljenaLozinkaerrorMessage;

    // Tekst koji se prikazuje umesto imena naloga nakon odjave.
    @FindBy(xpath = "//span[normalize-space()='Prijava' and not(*)]")
    private WebElement signInLabel;

    ChromeDriver driver = null;
    private WebDriverWait wait;
    /**
     * Sa već otvorene početne stranice prelazi na login formu.
     */
    public LoginPage(ChromeDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Čeka link za prijavu na početnoj stranici i klikće ga.
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[aria-label='Prijavi se']")
                )
        ).click();

        // Priprema elemente login stranice.
        PageFactory.initElements(driver, this);
    }

    // boolean znači da metoda vraća true ili false.
    public boolean isLogoutVisible() {

        // Čeka da opcija „Odjavi se“ bude vidljiva.
        // isDisplayed() zatim vraća true ako je prikazana.
        // Ako se ne pojavi u zadatom roku, čekanje baca TimeoutException.
        return wait.until(
                ExpectedConditions.visibilityOf(logoutOption)
        ).isDisplayed();
    }

    /**
     * Čeka da tekst „Prijava“ postane vidljiv nakon odjave.
     */
    public boolean isSignInVisible() {
        return wait.until(
                ExpectedConditions.visibilityOf(signInLabel)
        ).isDisplayed();
    }

    public LoginPage enterEmailAddress(String emailAddress) {
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(emailField)
        );
        field.clear();
        field.sendKeys(emailAddress);
        return this;
    } // Zatvara metodu za email.

    public LoginPage enterPassword(String password) {
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(passwordField)
        );
        field.clear();
        field.sendKeys(password);
        return this;
    } // Zatvara metodu za lozinku.

    /**
     * Šalje login formu sa prethodno unetim podacima.
     *
     * Steps:
     * 1. Wait until the Sign In button is visible and enabled.
     * 2. Click the button to submit the form.
     *
     * Result:
     * The form is submitted. Login success or an error message
     * must be checked separately.
     */
    public LoginPage submitLogin() {
        // Čeka da dugme bude vidljivo i omogućeno,
        // pa klikom šalje podatke iz login forme.
        wait.until(
                ExpectedConditions.elementToBeClickable(loginSubmitButton)
        ).click();

        // Vraća isti LoginPage objekat za naredne pozive metoda.
        return this;
    }

    public KorisnikPage clickSubmitButtonSuccess() {
        loginSubmitButton.click();
        return new KorisnikPage(driver);
    }

    /**
     * Otvara meni prijavljenog naloga.
     *
     * Steps:
     * 1. Wait until the account name is visible and enabled.
     * 2. Click the account name to open its menu.
     *
     * Result:
     * The account menu is opened.
     * The logout option is checked separately.
     *
     * Precondition:
     * The user must already be logged in.
     */
    public LoginPage openAccountMenu() {
        // Čeka da element accountMenu bude vidljiv i omogućen.
        // Trenutni lokator traži ime test naloga „Petar“.
        // Kada je element spreman, klikom otvara meni.
        wait.until(
                ExpectedConditions.elementToBeClickable(accountMenu)
        ).click();

        // Vraća isti LoginPage objekat za naredne pozive metoda.
        return this;
    }

    /**
     * Čeka da opcija „Odjavi se“ bude spremna i klikće je.
     * Meni naloga mora prethodno da bude otvoren.
     */
    public void logout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutOption)
        ).click();
    }


    //PREBACIO SI NA BASE PAGE
    public BasePage clickLogOutButton () {
        logOutButton.click();
        return new BasePage(driver);
    }

    /**
     * Čeka da poruka greške bude vidljiva i vraća njen tekst.
     */
    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(loginError)
        ).getText().trim();
    }


    //    // Ovaj ces da koristis za side bar burger button
    //    public LoginPage clickLogout() {
    //        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    //        wait.until(ExpectedConditions.visibilityOf(logOutSideBarLink));
    //        logOutSideBarLink.click();
    //        return new LoginPage(driver);
    //    }

//        public LoginPage clickLogOut() {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//            wait.until(ExpectedConditions.visibilityOf(logOutSideBarLink));
//            logOutSideBarLink.click();
//            return new LoginPage(driver);
//        }

    /**
     * Čeka da poruka greške bude vidljiva.
     */
    public boolean isLoginErrorVisible() {
        return wait.until(
                ExpectedConditions.visibilityOf(loginError)
        ).isDisplayed();
    }

}