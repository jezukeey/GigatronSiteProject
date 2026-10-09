import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.Strings;

import java.time.Duration;

public class LoginTests extends BaseTest {

    /**
     * Pre svakog testa otvara novi Chrome i početnu stranicu.
     */
    @Before
    public void setUp() {
        driver = openChromeDriver();
        // Otvara početnu stranicu pre svakog testa.
        driver.get("https://gigatron.rs/");
    }


    /**
     * Zatvara Chrome nakon svakog testa, čak i kada test padne.
     */
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


    /**
     * Test - Successful login with valid credentials
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Enter a valid email address.
     * 3. Enter a valid password.
     * 4. Click the Sign In button.
     * 5. Open the account menu.
     * 6. Verify that the logout option is visible.
     *
     * Expected result:
     * The "Odjavi se" option is visible, confirming login.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testSuccessfulLogIn() {

        // Kreira objekat LoginPage koristeći driver iz @Before metode.
        // Konstruktor klikće „Prijava“ na početnoj stranici i priprema PageFactory elemente.
        LoginPage loginPage = new LoginPage(driver);

        // Poziva metodu koja čeka email polje,
        // briše njegov sadržaj i unosi Strings.VALID_EMAIL.
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);

        // Poziva metodu koja čeka polje za lozinku,
        // briše njegov sadržaj i unosi Strings.VALID_PASSWORD.
        loginPage.enterPassword(Strings.VALID_PASSWORD);

        // Čeka da dugme za prijavu bude vidljivo i omogućeno,
        // pa klikom šalje login formu.
        loginPage.submitLogin();

        // Čeka da ime „Petar“ bude vidljivo i omogućeno,
        // pa klikom otvara meni naloga.
        loginPage.openAccountMenu();

        // isLogoutVisible() čeka da „Odjavi se“ bude vidljivo.
        // Ako se ne pojavi u roku, čekanje baca TimeoutException.
        // Assert.assertTrue proverava da je vraćena vrednost true.
        Assert.assertTrue(
                "Opcija 'Odjavi se' nije prikazana nakon prijave.",
                loginPage.isLogoutVisible()
        );

        // Browser zatvara @After metoda, pa driver.quit() nije potreban ovde.
    }


    /**
     * Test - Accept all cookies
     *
     * Steps:
     * 1. Open the home page.
     * 2. Wait for the cookie banner to become visible.
     * 3. Click "Prihvati sve".
     * 4. Wait for the cookie banner to disappear.
     *
     * Expected result:
     * The cookie banner is no longer visible after acceptance.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void CookieTestAccept() {
        // Koristi browser koji je otvorila @Before metoda.
        // Priprema čekanje od najviše 15 sekundi.
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Definiše lokator obaveštenja o kolačićima.
        By cookieBanner =
                By.cssSelector(".cky-consent-bar[data-cky-tag='notice']");

        // Čeka da obaveštenje bude vidljivo pre prihvatanja.
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cookieBanner)
        );

        // Čeka da dugme „Prihvati sve“ bude vidljivo i omogućeno.
        WebElement acceptCookie = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                ".cky-consent-bar button[data-cky-tag='accept-button']"
                        )
                )
        );

        // Klikće dugme za prihvatanje.
        acceptCookie.click();

        // Čeka da obaveštenje postane nevidljivo ili bude uklonjeno.
        boolean bannerHidden = wait.until(
                ExpectedConditions.invisibilityOfElementLocated(cookieBanner)
        );

        // Proverava da je obaveštenje nestalo.
        Assert.assertTrue(
                "Obaveštenje o kolačićima je i dalje vidljivo.",
                bannerHidden
        );

        // Browser zatvara @After, čak i ako test padne.
    }


    /**
     * Test - Login without choosing a cookie option
     *
     * Steps:
     * 1. Open the home page.
     * 2. Wait for the cookie banner.
     * 3. Leave the banner without making a choice.
     * 4. Click "Prijava" and enter valid credentials.
     * 5. Submit the form and open the account menu.
     *
     * Expected result:
     * "Odjavi se" is visible after login.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testLoginWithoutCookieChoice() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Potvrđuje da je obaveštenje prikazano.
        // Ne klikće prihvatanje, odbijanje ili zatvaranje.
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".cky-consent-bar[data-cky-tag='notice']")
                )
        );

        // Otvara login stranicu u istom browseru, bez cookie izbora.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        loginPage.submitLogin();
        loginPage.openAccountMenu();

        // Proverava uspešnu prijavu.
        Assert.assertTrue(
                "Prijava bez izbora kolačića nije potvrđena.",
                loginPage.isLogoutVisible()
        );
    }

    /**
     * Test - Login after accepting all cookies
     *
     * Steps:
     * 1. Open the home page.
     * 2. Click "Prihvati sve".
     * 3. Wait for the cookie banner to disappear.
     * 4. Click "Prijava" and enter valid credentials.
     * 5. Submit the form and open the account menu.
     *
     * Expected result:
     * "Odjavi se" is visible after login.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testLoginAfterAcceptingCookies() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        By cookieBanner =
                By.cssSelector(".cky-consent-bar[data-cky-tag='notice']");

        // Čeka obaveštenje, zatim dugme „Prihvati sve“.
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cookieBanner)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                ".cky-consent-bar button[data-cky-tag='accept-button']"
                        )
                )
        ).click();

        // Čeka da se obaveštenje zatvori pre nastavka.
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(cookieBanner)
        );

        // Prijavljuje nalog u istom browseru nakon prihvatanja.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        loginPage.submitLogin();
        loginPage.openAccountMenu();

        Assert.assertTrue(
                "Prijava nakon prihvatanja kolačića nije potvrđena.",
                loginPage.isLogoutVisible()
        );
    }

    /**
     * Test - Login after rejecting optional cookies
     *
     * Steps:
     * 1. Open the home page.
     * 2. Click "Odbij sve".
     * 3. Wait for the cookie banner to disappear.
     * 4. Click "Prijava" and enter valid credentials.
     * 5. Submit the form and open the account menu.
     *
     * Expected result:
     * "Odjavi se" is visible after login.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testLoginAfterRejectingCookies() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        By cookieBanner =
                By.cssSelector(".cky-consent-bar[data-cky-tag='notice']");

        // Čeka obaveštenje, zatim dugme „Odbij sve“.
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cookieBanner)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                ".cky-consent-bar button[data-cky-tag='reject-button']"
                        )
                )
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(cookieBanner)
        );

        // Prijavljuje nalog u istom browseru nakon odbijanja.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        loginPage.submitLogin();
        loginPage.openAccountMenu();

        Assert.assertTrue(
                "Prijava nakon odbijanja kolačića nije potvrđena.",
                loginPage.isLogoutVisible()
        );
    }


    /**
     * Test - Unsuccessful login with valid email and invalid password
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Enter a valid email address.
     * 3. Enter an invalid password.
     * 4. Click the Sign-In button.
     * 5. Verify the error message.
     *
     * Expected result:
     * "Wrong user and/or password" is displayed.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testValidEmailAndInvalidPassword() {

        // Koristi browser koji je pripremila @Before metoda.
        // Konstruktor otvara login stranicu i priprema elemente.
        LoginPage loginPage = new LoginPage(driver);

        // Čeka email polje, briše sadržaj i unosi ispravan email.
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);

        // Čeka polje za lozinku, briše sadržaj i unosi pogrešnu lozinku.
        loginPage.enterPassword(Strings.INVALID_PASSWORD);

        // Čeka dugme i klikom šalje formu.
        loginPage.submitLogin();

        // Čeka vidljivu poruku greške i vraća njen tekst.
        String actualMessage = loginPage.getErrorMessage();

        // Poredi očekivani tekst sa stvarno prikazanim tekstom.
        Assert.assertEquals(
                "Nije prikazana očekivana poruka za pogrešnu lozinku.",
                "Wrong user and/or password",
                actualMessage
        );

        // @After zatvara browser i kada test padne.
    }


    /**
     * Test - Unsuccessful login with invalid email and valid password
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Enter an email for a nonexistent account.
     * 3. Enter the valid test account password.
     * 4. Click the Sign In button.
     * 5. Verify the error message.
     *
     * Expected result:
     * "Wrong user and/or password" is displayed.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testInvalidEmailAndValidPassword() {

        // Koristi browser iz @Before i otvara login stranicu.
        LoginPage loginPage = new LoginPage(driver);

        // Čeka email polje, briše sadržaj i unosi nepostojeći email.
        loginPage.enterEmailAddress(Strings.INVALID_EMAIL);

        // Čeka polje za lozinku i unosi lozinku test naloga.
        loginPage.enterPassword(Strings.VALID_PASSWORD);

        // Čeka dugme za prijavu i šalje formu.
        loginPage.submitLogin();

        // Čeka poruku greške i čita njen tekst.
        String actualMessage = loginPage.getErrorMessage();

        // Proverava da je prikazana očekivana poruka.
        Assert.assertEquals(
                "Nije prikazana očekivana poruka za nepostojeći nalog.",
                "Wrong user and/or password",
                actualMessage
        );

        // Browser zatvara @After metoda.
    }


    /**
     * Test - Unsuccessful login with empty fields
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Leave email and password empty.
     * 3. Click the Sign In button.
     * 4. Verify the validation message.
     *
     * Expected result:
     * "Sign in with a valid email" is displayed.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testLoginWithEmptyFields() {

        // Koristi browser iz @Before i otvara login stranicu.
        LoginPage loginPage = new LoginPage(driver);

        // Čeka email polje i briše sadržaj.
        // Prazan string "" ostavlja polje praznim.
        loginPage.enterEmailAddress("");

        // Čeka polje za lozinku i ostavlja ga praznim.
        loginPage.enterPassword("");

        // Čeka dugme za prijavu i šalje praznu formu.
        loginPage.submitLogin();

        // Čeka poruku validacije i vraća njen tekst.
        String actualMessage = loginPage.getErrorMessage();

        // Poredi očekivanu poruku sa prikazanom.
        Assert.assertEquals(
                "Nije prikazana očekivana poruka za prazna polja.",
                "Sign in with a valid email",
                actualMessage
        );

        // @After zatvara browser, čak i ako test padne.
    }


    /**
     * Test - Rejected login with valid email and empty password
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Enter a valid email address.
     * 3. Leave the password empty.
     * 4. Submit the login form.
     * 5. Verify the error message.
     *
     * Expected result:
     * Login is rejected with "Wrong user and/or password".
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testValidEmailAndEmptyPassword() {

        // Otvara login stranicu u browseru pripremljenom kroz @Before.
        LoginPage loginPage = new LoginPage(driver);

        // Unosi email registrovanog test naloga.
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);

        // Briše sadržaj polja za lozinku i ostavlja ga praznim.
        loginPage.enterPassword("");

        // Šalje formu.
        loginPage.submitLogin();

        // Čeka poruku kojom stranica odbija prijavu.
        String actualMessage = loginPage.getErrorMessage();

        // Test prolazi kada je prikazana očekivana greška.
        Assert.assertEquals(
                "Nije prikazana očekivana poruka za praznu lozinku.",
                "Wrong user and/or password",
                actualMessage
        );
    }


    /**
     * Test - Invalid email and empty password
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Enter a nonexistent email.
     * 3. Leave the password empty.
     * 4. Submit the form and verify the error.
     *
     * Expected result:
     * The error is visible and its text matches either
     * "Wrong user and/or password" or
     * "Pogrešan e-mail adresa i/ili lozinka".
     * * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testInvalidEmailAndEmptyPassword() {
        // Koristi browser koji otvara @Before.
        LoginPage loginPage = new LoginPage(driver);

        // Unosi nepostojeći email i ostavlja lozinku praznom.
        loginPage.enterEmailAddress(Strings.INVALID_EMAIL);
        loginPage.enterPassword("");

        // Šalje login formu.
        loginPage.submitLogin();

        // 1. Čeka i proverava vidljivost elementa sa greškom.
        Assert.assertTrue(
                "Poruka o grešci nije prikazana.",
                loginPage.isLoginErrorVisible()
        );

        // 2. Čita tekst prikazane poruke.
        String actualMessage = loginPage.getErrorMessage();

        // 3. Prihvata tačan engleski ILI srpski tekst.
        // Operator || znači „ili“.
        boolean expectedMessage =
                "Wrong user and/or password".equals(actualMessage)
                        || "Pogrešan e-mail adresa i/ili lozinka".equals(actualMessage);

        // 4. Proverava da tekst odgovara jednoj od dozvoljenih poruka.
        Assert.assertTrue(
                "Neočekivana poruka greške: " + actualMessage,
                expectedMessage
        );

        // Browser zatvara @After.
    }


    /**
     * Test - Empty email and valid password
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Leave the email empty.
     * 3. Enter the valid test account password.
     * 4. Submit the form and verify the error.
     *
     * Expected result:
     * "Sign in with a valid email" is visible.
     * * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testEmptyEmailAndValidPassword() {
        // Otvara login stranicu u browseru pripremljenom kroz @Before.
        LoginPage loginPage = new LoginPage(driver);

        // Ostavlja email praznim i unosi ispravnu lozinku.
        loginPage.enterEmailAddress("");
        loginPage.enterPassword(Strings.VALID_PASSWORD);

        // Šalje formu.
        loginPage.submitLogin();

        // 1. Proverava da je poruka greške vidljiva.
        Assert.assertTrue(
                "Poruka o grešci nije prikazana.",
                loginPage.isLoginErrorVisible()
        );

        // 2. Čita tekst poruke.
        String actualMessage = loginPage.getErrorMessage();

        // 3. Proverava poruku za prazno email polje.
        Assert.assertEquals(
                "Neočekivana poruka za prazan email.",
                "Sign in with a valid email",
                actualMessage
        );
    }


    /**
     * Test - Empty email and invalid password
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Leave the email empty.
     * 3. Enter an invalid password.
     * 4. Submit the form and verify the error.
     *
     * Expected result:
     * "Sign in with a valid email" is visible.
     * * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testEmptyEmailAndInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);

        // Ostavlja email praznim i unosi pogrešnu lozinku.
        loginPage.enterEmailAddress("");
        loginPage.enterPassword(Strings.INVALID_PASSWORD);
        loginPage.submitLogin();

        // Proverava da je element sa greškom vidljiv.
        Assert.assertTrue(
                "Poruka o grešci nije prikazana.",
                loginPage.isLoginErrorVisible()
        );

        // Čita tekst prikazane poruke.
        String actualMessage = loginPage.getErrorMessage();

        // Proverava tačnu poruku za prazno email polje.
        Assert.assertEquals(
                "Neočekivana poruka za prazan email i pogrešnu lozinku.",
                "Sign in with a valid email",
                actualMessage
        );
    }


    /**
     * Test - Successful login and logout
     *
     * Steps:
     * 1. Open the home page.
     * 1. Click "Prijava" to open the login page.
     * 2. Enter valid credentials.
     * 3. Click the Sign In button.
     * 4. Open the account menu and verify the logout option.
     * 5. Click "Odjavi se".
     *
     * 7. Verify that "Prijava" is visible after logout.
     * Expected result:
     * "Prijava" is visible after logout.
     *
     * Browser lifecycle:
     * JUnit Before opens Chrome and the home page.
     * JUnit After closes Chrome, even if the test fails.
     */
    @Test
    public void testSuccessfulLogInAndLogOut() {
        // Koristi browser koji je već otvorila @Before metoda.
        driver.manage().window().maximize();

        // Otvara login stranicu i priprema elemente.
        LoginPage loginPage = new LoginPage(driver);

        // Unosi podatke i šalje formu.
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        loginPage.submitLogin();

        // Otvara meni i potvrđuje uspešnu prijavu.
        loginPage.openAccountMenu();
        Assert.assertTrue(
                "Opcija 'Odjavi se' nije prikazana nakon prijave.",
                loginPage.isLogoutVisible()
        );

        // Klikće na opciju za odjavu.
        loginPage.logout();

        // Proverava da je posle odjave vidljivo „Prijava“.
        Assert.assertTrue(
                "Tekst 'Prijava' nije prikazan nakon odjave.",
                loginPage.isSignInVisible()
        );

        // Browser zatvara @After.
    } // Zatvara metodu.

} // Zatvara klasu LoginTests.
