package pages;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.chrome.ChromeDriver;

public class PrenosniRacunariPage extends BasePage {
    private final By grid = By.cssSelector("section[class*='grid-cols']");
    private final By prices = By.cssSelector("section[class*='grid-cols'] [itemprop='price']");
    public PrenosniRacunariPage(ChromeDriver driver) { super(driver); }

    public PrenosniRacunariPage open() {
        driver.get(Strings.PRENOSNI_RACUNARI_URL);
        visible(prices);
        return this;
    }

    public PrenosniRacunariPage sortByPrice(boolean ascending) {
        By desktopSort = By.cssSelector("div[role='combobox']");
        boolean desktop = driver.findElements(desktopSort).stream().anyMatch(e -> e.isDisplayed());
        click(desktop ? desktopSort : By.cssSelector("button[aria-label='Otvori sortiranje']"));
        String label = ascending ? "Najnižoj ceni" : "Najvišoj ceni";
        click(desktop ? By.xpath("//*[@role='option' and normalize-space()='" + label + "']")
                : By.cssSelector("button[aria-label='Sortiranje po " + label + "']"));
        wait.until(browser -> browser.getCurrentUrl().contains(
                ascending ? "sort=price%3Aasc" : "sort=price%3Adesc"));
        wait.until(browser -> {
            try {
                return browser.findElements(grid).stream().anyMatch(e ->
                        e.isDisplayed() && !e.getDomAttribute("class").contains("opacity-60"));
            } catch (StaleElementReferenceException ignored) { return false; }
        });
        visible(prices);
        return this;
    }

    /** Samo glavna lista proizvoda; preporuke nisu deo sortiranja. */
    public List<BigDecimal> getPrices() {
        return wait.until(browser -> {
            try {
                List<BigDecimal> result = new ArrayList<>();
                for (var element : browser.findElements(prices)) {
                    String text = element.getText();
                    if (element.isDisplayed() && !text.isBlank()) {
                        // Zajednički parser proverava format pre poređenja cena.
                        result.add(PriceParser.parse(text));
                    }
                }
                return result.size() >= 2 ? result : null;
            } catch (StaleElementReferenceException ignored) { return null; }
        });
    }
}
