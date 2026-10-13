package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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

    // Kontrola profila postoji i kada ime naloga nije vidljivo.
    private final By accountMenu =
            By.cssSelector("[aria-label='Moj profil']");

    private final By logoutOption =
            By.xpath("//*[self::button or self::a][normalize-space()='Odjavi se']");

    private final By signInLabel =
            By.cssSelector("a[aria-label='Prijavi se']");

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
        boolean completed = false;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                WebElement field = findReadyField(locator);
                field.click();
                field = findReadyField(locator);
                field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE, value);
                completed = true;
                break;
            } catch (StaleElementReferenceException ignored) {
                // Novi pokušaj prvo briše eventualni delimični unos.
            }
        }
        if (!completed) {
            throw new IllegalStateException("Polje je zamenjeno tokom tri pokušaja: " + locator);
        }
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
        WebDriverWait profileWait = new WebDriverWait(driver, Duration.ofSeconds(45));
        profileWait.withMessage(() -> "Prijava nije potvrđena. URL: " + driver.getCurrentUrl()
                + "; kontrole: " + driver.findElements(By.cssSelector("header a, header button, [role=alert]"))
                .stream().filter(e -> e.isDisplayed()).map(e -> e.getText() + " / " + e.getDomAttribute("aria-label"))
                .collect(java.util.stream.Collectors.joining(" | ")));
        profileWait.until(browser -> {
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
        wait.until(browser -> {
            for (WebElement option : browser.findElements(logoutOption)) {
                try {
                    if (option.isDisplayed() && option.isEnabled()) {
                        option.click();
                        return true;
                    }
                } catch (StaleElementReferenceException
                         | org.openqa.selenium.ElementClickInterceptedException ignored) {
                    // Vidljiv meni još može biti u animaciji; ponovo pokušava normalan klik.
                }
            }
            return false;
        });
    }

    /**
     * Čeka vidljivi tekst „Prijava“ nakon odjave.
     * Koristi aria-label koji postoji na svim širinama prozora.
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