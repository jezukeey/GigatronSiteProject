import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import pages.CookieConsent;
import pages.PrenosniRacunariPage;

public class SortingTests extends BaseTest {
    /**
     * Test - Sort laptop prices in ascending order
     * Steps:
     * 1. Open the laptop catalog and reject optional cookies.
     * 2. Select the lowest-price sorting option and wait for the list to update.
     * 3. Read at least two prices from the main product list.
     * 4. Compare every adjacent pair of prices.
     * Expected result:
     * Each next price is greater than or equal to the previous price on the displayed results page.
     */
    @Test
    public void testSortingByPriceAscending() { verifyOrder(true); }

    /**
     * Test - Sort laptop prices in descending order
     * Steps:
     * 1. Open the laptop catalog and reject optional cookies.
     * 2. Select the highest-price sorting option and wait for the list to update.
     * 3. Read at least two prices from the main product list.
     * 4. Compare every adjacent pair of prices.
     * Expected result:
     * Each next price is less than or equal to the previous price on the displayed results page.
     */
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