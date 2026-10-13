package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

/** Prikaz proizvoda za demonstraciju; ove metode ništa ne dodaju u korpu. */
public class ProductPage extends BasePage {
    private final By heading = By.tagName("h1");
    private final By addToCart = By.xpath("//button[normalize-space()='Dodaj u korpu']");

    public ProductPage(ChromeDriver driver) { super(driver); }

    public ProductPage open() {
        // Direktna adresa daje ponovljiv početak testa, nezavisno od prethodne stranice.
        driver.get(Strings.TEST_PRODUCT_URL);
        // Čekamo naziv: učitan HTML nije dovoljan dokaz da je proizvod prikazan.
        visible(heading);
        return this;
    }

    public String getName() {
        // Čitamo vidljivi naziv koji korisnik vidi na stranici.
        return visible(heading).getText().trim();
    }

    public boolean isAddToCartAvailable() {
        // Čitamo stanje dugmeta; namerno ga ne klikćemo u testu prikaza proizvoda.
        return visible(addToCart).isEnabled();
    }
}
