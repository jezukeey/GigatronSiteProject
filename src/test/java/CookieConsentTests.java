import org.junit.Assert;
import org.junit.Test;
import pages.CookieConsent;
import pages.Strings;

/** Proverava pamćenje izbora, a ne samo nestanak cookie obaveštenja. */
public class CookieConsentTests extends BaseTest {
    /**
     * Test - Remember cookie acceptance after refresh
     * Steps:
     * 1. Open the home page in a fresh browser session.
     * 2. Accept all cookies and wait for the banner to disappear.
     * 3. Refresh the page.
     * 4. Check the banner after document loading and during a three-second observation period.
     * Expected result:
     * The banner does not reappear during the observation period.
     */
    @Test
    public void testAcceptChoiceSurvivesRefresh() {
        verifyChoiceIsRemembered("accept-button");
    }

    /**
     * Test - Remember cookie rejection after refresh
     * Steps:
     * 1. Open the home page in a fresh browser session.
     * 2. Reject optional cookies and wait for the banner to disappear.
     * 3. Refresh the page.
     * 4. Check the banner after document loading and during a three-second observation period.
     * Expected result:
     * The banner does not reappear during the observation period.
     */
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
