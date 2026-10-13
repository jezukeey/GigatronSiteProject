import java.net.URI;
import org.junit.Assert;
import org.junit.Test;
import pages.CookieConsent;
import pages.PrenosniRacunariPage;
import pages.ProductPage;
import pages.Strings;

/** Provere kataloga i prikaza proizvoda, bez korpe, prijave ili porudžbine. */
public class CatalogTests extends BaseTest {
    @Test
    public void testLaptopCatalogContainsPositivePrices() {
        // Otvaramo glavnu listu laptopova i čekamo cene proizvoda.
        PrenosniRacunariPage page = new PrenosniRacunariPage(driver).open();
        new CookieConsent(driver).choose("reject-button");
        // Čitamo samo glavnu listu; preporuke nisu deo provere kataloga.
        var prices = page.getPrices();
        Assert.assertTrue("Katalog nema dovoljno proizvoda.", prices.size() >= 2);
        // Prodajne cene moraju biti pozitivne; nula ili negativna cena ukazuje na problem.
        Assert.assertTrue("Katalog sadrži nepozitivnu cenu.",
                prices.stream().allMatch(price -> price.signum() > 0));
    }

    @Test
    public void testProductNameAndAddToCartAvailability() {
        // Proveravamo poznati proizvod bez dodavanja u korpu.
        ProductPage page = new ProductPage(driver).open();
        new CookieConsent(driver).choose("reject-button");
        // Putanja i naziv zajedno sprečavaju prolaz ako je otvoren drugi proizvod.
        Assert.assertEquals(URI.create(Strings.TEST_PRODUCT_URL).getPath(),
                URI.create(driver.getCurrentUrl()).getPath());
        Assert.assertEquals("Prikazan je pogrešan proizvod.", Strings.TEST_PRODUCT_NAME, page.getName());
        // Dostupno dugme znači da je proizvod moguće dodati; test ga ne klikće.
        Assert.assertTrue("Dodavanje u korpu nije dostupno.", page.isAddToCartAvailable());
    }
}
