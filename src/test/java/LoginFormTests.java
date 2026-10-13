import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.CookieConsent;
import pages.LoginPage;
import pages.Strings;

/** Proverava unos i zaštitu login forme bez slanja prijave i bez stvarne lozinke. */
public class LoginFormTests extends BaseTest {
    private LoginPage loginPage;

    @Before
    public void openForm() {
        // Svaki test počinje na početnoj stranici u novom browseru.
        driver.get(Strings.MAIN_URL);
        // Odbijamo opcione kolačiće da banner ne prekrije formu.
        new CookieConsent(driver).choose("reject-button");
        // Otvaramo formu kroz isto dugme koje koristi posetilac sajta.
        loginPage = new LoginPage(driver).openFromHomePage();
    }

    /**
     * Test - Replace an already entered email
     * Steps:
     * 1. Open login from the home page after rejecting optional cookies.
     * 2. Enter the first test email.
     * 3. Enter a different test email into the same field.
     * 4. Read the current email without submitting the form.
     * Expected result:
     * Only the second email is present; the previous value is not appended.
     */
    @Test
    public void testEmailCanBeReplaced() {
        // Dva uzastopna unosa ne smeju se spojiti u jednu email adresu.
        loginPage.enterEmailAddress("first@example.invalid");
        loginPage.enterEmailAddress("second@example.invalid");
        // Očekujemo samo drugi unos; formu ne šaljemo.
        Assert.assertEquals("Prethodni email nije zamenjen.",
                "second@example.invalid", loginPage.getEnteredEmail());
    }

    /**
     * Test - Clear an already entered email
     * Steps:
     * 1. Open login from the home page after rejecting optional cookies.
     * 2. Enter a test email.
     * 3. Replace its value with an empty string.
     * 4. Read the current email without submitting the form.
     * Expected result:
     * The email field is empty.
     */
    @Test
    public void testEmailCanBeCleared() {
        // Proveravamo da korisnik može obrisati već upisanu adresu.
        loginPage.enterEmailAddress("demo@example.invalid");
        loginPage.enterEmailAddress("");
        // Prazno polje potvrđuje da metoda ne ostavlja stari unos.
        Assert.assertEquals("Email nije obrisan.", "", loginPage.getEnteredEmail());
    }

    /**
     * Test - Mask and replace a password without submitting login
     * Steps:
     * 1. Open login from the home page after rejecting optional cookies.
     * 2. Enter an invented password, then replace it with another invented password.
     * 3. Check the password input type and verify its value without printing it.
     * Expected result:
     * The input remains masked and contains only the second password; no login request is submitted.
     */
    @Test
    public void testPasswordIsMaskedAndCanBeReplaced() {
        // Koristimo izmišljene vrednosti, ne lozinku naloga; ništa se ne šalje serveru.
        loginPage.enterPassword("Demo-first-123");
        loginPage.enterPassword("Demo-second-456");
        // Proveravamo maskiranje i zamenu bez ispisivanja sadržaja password polja.
        Assert.assertTrue("Lozinka nije maskirana.", loginPage.isPasswordMasked());
        Assert.assertTrue("Prethodna lozinka nije zamenjena.",
                loginPage.isPasswordValueCorrect("Demo-second-456"));
    }
}
