package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CookieConsent extends BasePage {
    private final By banner = By.cssSelector(".cky-consent-bar[data-cky-tag='notice']");
    public CookieConsent(ChromeDriver driver) { super(driver); }

    public void choose(String option) {
        if (!"accept-button".equals(option) && !"reject-button".equals(option)) {
            throw new IllegalArgumentException("Nepoznata cookie opcija: " + option);
        }
        visible(banner);
        click(By.cssSelector(".cky-consent-bar button[data-cky-tag='" + option + "']"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(banner));
    }
}