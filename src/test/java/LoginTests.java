import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.Strings;

import java.time.Duration;

public class LoginTests extends BaseTest {

    private WebDriverWait wait;

    // Lokator obaveštenja o kolačićima na početnoj stranici.
    private final By cookieBanner =
            By.cssSelector(".cky-consent-bar[data-cky-tag='notice']");

    /**
     * Pre svakog testa:
     * 1. Otvara novi Chrome.
     * 2. Priprema čekanje.
     * 3. Otvara početnu stranicu.
     */
    @Before
    public void setUp() {
        driver = openChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://gigatron.rs/");
    }

    /**
     * Nakon svakog testa zatvara browser, čak i kada test padne.
     */
    @After
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driver = null;
            }
        }
    }

    /**
     * Priprema LoginPage i klikće prijavu sa početne stranice.
     * Svaki test poziva ovaj postupak jednom.
     */
    private LoginPage openLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openFromHomePage();
        return loginPage;
    }

    /**
     * Čeka cookie obaveštenje, klikće izabranu opciju
     * i čeka da obaveštenje nestane.
     */
    private void chooseCookieOption(String optionTag) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cookieBanner)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(
                                ".cky-consent-bar button[data-cky-tag='"
                                        + optionTag + "']"
                        )
                )
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(cookieBanner)
        );
    }

    /**
     * Unosi validne podatke i proverava sadržaj oba polja.
     * Šalje formu jednom i potvrđuje prijavu kroz meni naloga.
     */
    private void loginAndVerify(LoginPage loginPage) {
        Assert.assertTrue(
                "Strings.VALID_EMAIL nije podešen ili je prazan.",
                Strings.VALID_EMAIL != null
                        && !Strings.VALID_EMAIL.isBlank()
        );

        Assert.assertTrue(
                "Proveri GIGATRON_TEST_PASSWORD u konfiguraciji testa.",
                Strings.VALID_PASSWORD != null
                        && !Strings.VALID_PASSWORD.isEmpty()
        );

        // Svaki podatak unosi jednom.
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);
        loginPage.enterPassword(Strings.VALID_PASSWORD);

        // Proverava da email nije nestao tokom unosa lozinke.
        Assert.assertEquals(
                "Email u polju se razlikuje od Strings.VALID_EMAIL.",
                Strings.VALID_EMAIL,
                loginPage.getEnteredEmail()
        );

        // Proverava lozinku bez njenog ispisivanja.
        Assert.assertTrue(
                "Sadržaj polja za lozinku ne odgovara očekivanoj vrednosti.",
                loginPage.isPasswordValueCorrect(Strings.VALID_PASSWORD)
        );

        loginPage.submitLogin();
        loginPage.openAccountMenu();

        Assert.assertTrue(
                "Opcija 'Odjavi se' nije prikazana nakon prijave.",
                loginPage.isLogoutVisible()
        );
    }

    /**
     * Šalje zadate podatke i proverava vidljivost i tekst greške.
     */
    private void verifyRejectedLogin(
            String email,
            String password,
            String expectedMessage
    ) {
        LoginPage loginPage = openLoginPage();

        loginPage.enterEmailAddress(email);
        loginPage.enterPassword(password);
        loginPage.submitLogin();

        Assert.assertTrue(
                "Poruka o grešci nije prikazana.",
                loginPage.isLoginErrorVisible()
        );

        Assert.assertEquals(
                "Prikazana je poruka sa neočekivanim tekstom.",
                expectedMessage,
                loginPage.getErrorMessage()
        );
    }

    /**
     * Test - Successful login with valid credentials
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Enter and verify valid credentials.
     * 3. Submit the form and open the account menu.
     *
     * Expected result:
     * The "Odjavi se" option is visible.
     */
    @Test
    public void testSuccessfulLogIn() {
        loginAndVerify(openLoginPage());
    }

    /**
     * Test - Accept all cookies
     *
     * Steps:
     * 1. Wait for the cookie banner on the home page.
     * 2. Click "Prihvati sve".
     * 3. Wait for the banner to disappear.
     *
     * Expected result:
     * The cookie banner is no longer visible.
     */
    @Test
    public void CookieTestAccept() {
        chooseCookieOption("accept-button");

        Assert.assertTrue(
                "Obaveštenje o kolačićima je i dalje vidljivo.",
                wait.until(
                        ExpectedConditions.invisibilityOfElementLocated(
                                cookieBanner
                        )
                )
        );
    }

    /**
     * Test - Login without choosing a cookie option
     *
     * Steps:
     * 1. Wait for the cookie banner.
     * 2. Leave it without accepting, rejecting or closing it.
     * 3. Open login and sign in with valid credentials.
     *
     * Expected result:
     * The "Odjavi se" option is visible.
     */
    @Test
    public void testLoginWithoutCookieChoice() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cookieBanner)
        );

        loginAndVerify(openLoginPage());
    }

    /**
     * Test - Successful login after accepting all cookies
     *
     * Steps:
     * 1. Accept cookies on the home page.
     * 2. Wait for the banner to disappear.
     * 3. Open login and enter valid credentials.
     * 4. Verify field values and submit the form.
     * 5. Open the account menu.
     *
     * Expected result:
     * The "Odjavi se" option is visible.
     */
    @Test
    public void testLoginAfterAcceptingCookies() {
        // Prihvata kolačiće pre otvaranja prijave.
        chooseCookieOption("accept-button");

        // Prijavljuje nalog u istom browseru.
        loginAndVerify(openLoginPage());
    }

    /**
     * Test - Login after rejecting optional cookies
     *
     * Steps:
     * 1. Click "Odbij sve" on the home page.
     * 2. Wait for the banner to disappear.
     * 3. Open login and sign in with valid credentials.
     *
     * Expected result:
     * The "Odjavi se" option is visible.
     */
    @Test
    public void testLoginAfterRejectingCookies() {
        chooseCookieOption("reject-button");
        loginAndVerify(openLoginPage());
    }

    /**
     * Test - Valid email and invalid password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Enter valid email and invalid password.
     * 3. Submit and check the visible error and its text.
     *
     * Expected result:
     * "Wrong user and/or password" is displayed.
     */
    @Test
    public void testValidEmailAndInvalidPassword() {
        verifyRejectedLogin(
                Strings.VALID_EMAIL,
                Strings.INVALID_PASSWORD,
                "Wrong user and/or password"
        );
    }

    /**
     * Test - Nonexistent email and valid password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Enter a nonexistent email with valid email syntax.
     * 3. Enter the valid test account password.
     * 4. Submit and check the error.
     *
     * Expected result:
     * "Wrong user and/or password" is displayed.
     */
    @Test
    public void testInvalidEmailAndValidPassword() {
        verifyRejectedLogin(
                Strings.INVALID_EMAIL,
                Strings.VALID_PASSWORD,
                "Wrong user and/or password"
        );
    }

    /**
     * Test - Empty email and password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Leave both fields empty.
     * 3. Submit and check the error.
     *
     * Expected result:
     * "Sign in with a valid email" is displayed.
     */
    @Test
    public void testLoginWithEmptyFields() {
        verifyRejectedLogin("", "", "Sign in with a valid email");
    }

    /**
     * Test - Valid email and empty password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Enter valid email and leave the password empty.
     * 3. Submit and check the error.
     *
     * Expected result:
     * "Wrong user and/or password" is displayed.
     */
    @Test
    public void testValidEmailAndEmptyPassword() {
        verifyRejectedLogin(
                Strings.VALID_EMAIL,
                "",
                "Wrong user and/or password"
        );
    }

    /**
     * Test - Nonexistent email and empty password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Enter a nonexistent email and leave the password empty.
     * 3. Submit and verify the visible error and its text.
     *
     * Expected result:
     * The English or Serbian credentials error is displayed.
     */
    @Test
    public void testInvalidEmailAndEmptyPassword() {
        LoginPage loginPage = openLoginPage();

        loginPage.enterEmailAddress(Strings.INVALID_EMAIL);
        loginPage.enterPassword("");
        loginPage.submitLogin();

        Assert.assertTrue(
                "Poruka o grešci nije prikazana.",
                loginPage.isLoginErrorVisible()
        );

        String actualMessage = loginPage.getErrorMessage();

        // || znači da je dovoljan jedan od dva tačna teksta.
        boolean expectedMessage =
                "Wrong user and/or password".equals(actualMessage)
                        || "Pogrešan e-mail adresa i/ili lozinka"
                        .equals(actualMessage);

        Assert.assertTrue(
                "Neočekivana poruka greške: " + actualMessage,
                expectedMessage
        );
    }

    /**
     * Test - Empty email and valid password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Leave email empty and enter valid password.
     * 3. Submit and check the error.
     *
     * Expected result:
     * "Sign in with a valid email" is displayed.
     */
    @Test
    public void testEmptyEmailAndValidPassword() {
        verifyRejectedLogin(
                "",
                Strings.VALID_PASSWORD,
                "Sign in with a valid email"
        );
    }

    /**
     * Test - Empty email and invalid password
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Leave email empty and enter invalid password.
     * 3. Submit and check the error.
     *
     * Expected result:
     * "Sign in with a valid email" is displayed.
     */
    @Test
    public void testEmptyEmailAndInvalidPassword() {
        verifyRejectedLogin(
                "",
                Strings.INVALID_PASSWORD,
                "Sign in with a valid email"
        );
    }

    /**
     * Test - Successful login and logout
     *
     * Steps:
     * 1. Open login from the home page.
     * 2. Sign in and verify the logout option.
     * 3. Click "Odjavi se".
     * 4. Verify the sign-in label.
     *
     * Expected result:
     * "Prijava" is visible after logout.
     */
    @Test
    public void testSuccessfulLogInAndLogOut() {
        LoginPage loginPage = openLoginPage();

        // Potvrđuje prijavu pre provere odjave.
        loginAndVerify(loginPage);

        // Meni je već otvoren u loginAndVerify().
        loginPage.logout();

        Assert.assertTrue(
                "Tekst 'Prijava' nije prikazan nakon odjave.",
                loginPage.isSignInVisible()
        );
    }
}