import java.net.URI;
import org.junit.Assert;
import org.junit.Test;
import pages.CookieConsent;
import pages.PrenosniRacunariPage;
import pages.ProductPage;
import pages.Strings;

/** Provere kataloga i prikaza proizvoda, bez korpe, prijave ili porudžbine. */
public class CatalogTests extends BaseTest {
    /**
     * Test - Display positive prices in the laptop catalog
     * Steps:
     * 1. Open the laptop catalog and reject optional cookies.
     * 2. Read prices from the visible main product list, excluding recommendations.
     * 3. Check the number of prices and the sign of each price.
     * Expected result:
     * At least two visible product prices are returned and every price is greater than zero.
     */
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

    /**
     * Test - Display the expected product and an available cart button
     * Steps:
     * 1. Open the configured ASUS TUF F16 product and reject optional cookies.
     * 2. Compare the URL path and visible name with the configured product.
     * 3. Check that the Add to cart button is enabled without clicking it.
     * Expected result:
     * The correct product is displayed and adding it is available; the cart is not changed.
     */
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
