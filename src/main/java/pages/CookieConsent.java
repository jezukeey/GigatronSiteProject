package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CookieConsent extends BasePage {
    private final By banner = By.cssSelector(".cky-consent-bar[data-cky-tag='notice']");
    public CookieConsent(ChromeDriver driver) { super(driver); }

    public void choose(String option) {
        // Poziv mora izabrati stvarnu opciju bannera, da pogrešan naziv ne izazove timeout.
        if (!"accept-button".equals(option) && !"reject-button".equals(option)) {
            throw new IllegalArgumentException("Nepoznata cookie opcija: " + option);
        }
        // Čekamo banner, zatim klikćemo izabranu opciju normalnim Selenium klikom.
        visible(banner);
        click(By.cssSelector(".cky-consent-bar button[data-cky-tag='" + option + "']"));
        // Sledeći korak počinje tek kada obaveštenje više ne prekriva stranicu.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(banner));
    }

    /** Posle ponovnog učitavanja izbor treba da ostane zapamćen. */
    public boolean remainsHiddenAfterReload() {
        // Čekamo završetak dokumenta pre provere da se obaveštenje nije vratilo.
        wait.until(browser -> "complete".equals(
                ((org.openqa.selenium.JavascriptExecutor) browser).executeScript("return document.readyState")));
        long deadline = System.nanoTime() + java.time.Duration.ofSeconds(3).toNanos();
        // Kratak period posmatranja hvata i banner koji se pojavi posle učitavanja JS-a.
        String outcome = wait.until(browser -> {
            for (var element : browser.findElements(banner)) {
                try {
                    if (element.isDisplayed()) return "visible";
                } catch (org.openqa.selenium.StaleElementReferenceException ignored) { }
            }
            return System.nanoTime() >= deadline ? "hidden" : null;
        });
        return "hidden".equals(outcome);
    }
}
