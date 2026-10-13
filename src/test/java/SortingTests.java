import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import pages.CookieConsent;
import pages.PrenosniRacunariPage;

public class SortingTests extends BaseTest {
    @Test
    public void testSortingByPriceAscending() { verifyOrder(true); }

    @Test
    public void testSortingByPriceDescending() { verifyOrder(false); }

    private void verifyOrder(boolean ascending) {
        PrenosniRacunariPage page = new PrenosniRacunariPage(driver).open();
        new CookieConsent(driver).choose("reject-button");
        List<BigDecimal> prices = page.sortByPrice(ascending).getPrices();
        Assert.assertTrue("Potrebna su najmanje dva proizvoda.", prices.size() >= 2);
        for (int i = 1; i < prices.size(); i++) {
            int comparison = prices.get(i - 1).compareTo(prices.get(i));
            Assert.assertTrue("Pogrešan redosled cena na poziciji " + i + ": " + prices,
                    ascending ? comparison <= 0 : comparison >= 0);
        }
    }
}