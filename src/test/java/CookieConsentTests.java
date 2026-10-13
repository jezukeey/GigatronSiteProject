import org.junit.Assert;
import org.junit.Test;
import pages.CookieConsent;
import pages.Strings;

/** Proverava pamćenje izbora, a ne samo nestanak cookie obaveštenja. */
public class CookieConsentTests extends BaseTest {
    @Test
    public void testAcceptChoiceSurvivesRefresh() {
        verifyChoiceIsRemembered("accept-button");
    }

    @Test
    public void testRejectChoiceSurvivesRefresh() {
        verifyChoiceIsRemembered("reject-button");
    }

    private void verifyChoiceIsRemembered(String option) {
        // Nov browser nema izbor iz prethodnog testa.
        driver.get(Strings.MAIN_URL);
        CookieConsent consent = new CookieConsent(driver);
        // Biramo jednu od stvarnih opcija i čekamo da banner nestane.
        consent.choose(option);
        // Ponovno učitavanje proverava da je izbor sačuvan, a ne samo vizuelno sakriven.
        driver.navigate().refresh();
        Assert.assertTrue("Cookie izbor nije zapamćen: " + option,
                consent.remainsHiddenAfterReload());
    }
}
