package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    // Postojeći browser i čekanje za elemente stranice.
    private final ChromeDriver driver;
    private final WebDriverWait wait;

    // Lokatori elemenata trenutno prikazane login forme.
    private final By signInLink =
            By.cssSelector("a[aria-label='Prijavi se']");

    private final By emailField = By.cssSelector(
            ".vtex-login-2-x-contentFormVisible "
                    + ".vtex-login-2-x-inputContainerEmail input"
    );

    // Traži password polja na stranici.
// findReadyField() bira ono koje je vidljivo i omogućeno.
    private final By passwordField =
            By.cssSelector("input[type='password']");

    private final By submitButton = By.cssSelector(
            ".vtex-login-2-x-contentFormVisible button[type='submit']"
    );

    private final By loginError =
            By.cssSelector(".vtex-login-2-x-formError[role='alert']");

    // Trenutni test nalog prikazuje ime „Petar“.
    private final By accountMenu =
            By.xpath("//span[normalize-space()='Petar' and not(*)]");

    private final By logoutOption =
            By.xpath("//button[normalize-space()='Odjavi se']");

    private final By signInLabel =
            By.xpath("//span[normalize-space()='Prijava' and not(*)]");

    /**
     * Priprema objekat za postojeći browser.
     * Ne otvara stranicu i ne šalje formu.
     */
    public LoginPage(ChromeDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                    "Browser nije otvoren. Proveri @Before metodu."
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Klikće prijavu sa već otvorene početne stranice.
     * Zatim čeka vidljivo i omogućeno email polje.
     */
    public LoginPage openFromHomePage() {
        wait.until(
                ExpectedConditions.elementToBeClickable(signInLink)
        ).click();

        findReadyField(emailField);
        return this;
    }

    /**
     * Pronalazi vidljivo i omogućeno polje.
     * Preskače skrivene elemente koji odgovaraju istom lokatoru.
     */
    private WebElement findReadyField(By locator) {
        return wait.until(browser -> {
            for (WebElement field : browser.findElements(locator)) {
                try {
                    if (field.isDisplayed() && field.isEnabled()) {
                        return field;
                    }
                } catch (StaleElementReferenceException e) {
                    // Element je zamenjen; sledeća provera traži novi.
                }
            }

            return null;
        });
    }

    /**
     * Briše prethodni sadržaj i unosi prosleđeni email.
     * Prazan string je dozvoljen za negativne testove.
     */
    public LoginPage enterEmailAddress(String emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException(
                    "Email nije podešen. Prosledi email ili prazan string."
            );
        }

        replaceFieldValue(emailField, emailAddress);
        return this;
    }

    /**
     * Briše prethodni sadržaj i unosi prosleđenu lozinku.
     * Lozinka se ne ispisuje.
     */
    public LoginPage enterPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException(
                    "Lozinka nije podešena. Proveri GIGATRON_TEST_PASSWORD "
                            + "u konfiguraciji testa."
            );
        }

        replaceFieldValue(passwordField, password);
        return this;
    }

    /**
     * Zamenjuje sadržaj polja i proverava unetu vrednost.
     * Ne ispisuje email ni lozinku.
     */
    private void replaceFieldValue(By locator, String value) {
        // Pronalazi vidljivo i omogućeno polje.
        WebElement field = findReadyField(locator);

        // Označava sadržaj, otpušta Ctrl, briše i unosi novu vrednost.
        new Actions(driver)
                .click(field)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.BACK_SPACE)
                .sendKeys(value)
                .perform();

        // Proverava sadržaj bez ponavljanja unosa.
        WebDriverWait valueWait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        valueWait.withMessage(
                "Unos nije sačuvan u polju: " + locator
        );

        valueWait.until(browser -> {
            for (WebElement current : browser.findElements(locator)) {
                try {
                    if (current.isDisplayed()
                            && value.equals(
                            current.getDomProperty("value")
                    )) {
                        return true;
                    }
                } catch (StaleElementReferenceException e) {
                    // Sledeća provera pronalazi novi element.
                }
            }

            return false;
        });
    }

    /**
     * Vraća trenutni sadržaj vidljivog email polja.
     */
    public String getEnteredEmail() {
        return findReadyField(emailField).getDomProperty("value");
    }

    /**
     * Proverava lozinku bez vraćanja njenog sadržaja testu.
     */
    public boolean isPasswordValueCorrect(String expectedPassword) {
        if (expectedPassword == null) {
            return false;
        }

        return expectedPassword.equals(
                findReadyField(passwordField).getDomProperty("value")
        );
    }

    /**
     * Čeka dugme za prijavu i šalje formu jednom.
     */
    public LoginPage submitLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        ).click();

        return this;
    }

    /**
     * Čeka vidljivo ime naloga ili vidljivu poruku greške.
     * Grešku prijave prijavljuje sa tekstom koji prikazuje sajt.
     */
    public LoginPage waitForSuccessfulLogin() {
        wait.until(browser -> {
            // Najpre proverava da li je prijavljeni nalog prikazan.
            for (WebElement account : browser.findElements(accountMenu)) {
                try {
                    if (account.isDisplayed()) {
                        return true;
                    }
                } catch (StaleElementReferenceException e) {
                    // Ponovna provera će pronaći novi element.
                }
            }

            // Ako nalog nije prikazan, proverava grešku forme.
            for (WebElement error : browser.findElements(loginError)) {
                try {
                    if (error.isDisplayed()) {
                        throw new IllegalStateException(
                                "Prijava je odbijena: "
                                        + error.getText().trim()
                        );
                    }
                } catch (StaleElementReferenceException e) {
                    // Ponovna provera će pronaći novi element.
                }
            }

            return false;
        });

        return this;
    }

    /**
     * Čeka uspešnu prijavu i otvara meni naloga.
     */
    public LoginPage openAccountMenu() {
        waitForSuccessfulLogin();

        findReadyField(accountMenu).click();
        return this;
    }

    /**
     * Čeka vidljivu opciju „Odjavi se“.
     * Meni naloga mora prethodno biti otvoren.
     */
    public boolean isLogoutVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutOption)
        ).isDisplayed();
    }

    /**
     * Klikće opciju „Odjavi se“ u otvorenom meniju.
     */
    public void logout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutOption)
        ).click();
    }

    /**
     * Čeka vidljivi tekst „Prijava“ nakon odjave.
     * Prozor mora biti dovoljno širok da prikaže taj tekst.
     */
    public boolean isSignInVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(signInLabel)
        ).isDisplayed();
    }

    /**
     * Čeka vidljivu poruku greške.
     */
    public boolean isLoginErrorVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginError)
        ).isDisplayed();
    }

    /**
     * Čita tekst vidljive poruke greške.
     */
    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginError)
        ).getText().trim();
    }

    /**
     * Zadržava poziv koji koriste pomoćne klase projekta.
     * Šalje formu i čeka uspešnu prijavu.
     */
    public KorisnikPage clickSubmitButtonSuccess() {
        submitLogin();
        waitForSuccessfulLogin();
        return new KorisnikPage(driver);
    }

    /**
     * Zadržava drugi postojeći naziv metode za prijavu.
     */
    public KorisnikPage clickLoginSubmitButton() {
        return clickSubmitButtonSuccess();
    }

    /**
     * Zadržava postojeći naziv metode za odjavu.
     * Meni naloga mora prethodno biti otvoren.
     */
    public BasePage clickLogOutButton() {
        logout();
        return new BasePage(driver);
    }
}