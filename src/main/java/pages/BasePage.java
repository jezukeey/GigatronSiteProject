package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class BasePage {
    protected final ChromeDriver driver;
    protected final WebDriverWait wait;

    public BasePage(ChromeDriver driver) {
        this.driver = Objects.requireNonNull(driver, "Browser nije otvoren.");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected WebElement visible(By locator) {
        return wait.until(browser -> {
            for (WebElement element : browser.findElements(locator)) {
                try {
                    if (element.isDisplayed()) return element;
                } catch (StaleElementReferenceException ignored) { }
            }
            return null;
        });
    }

    protected void click(By locator) {
        wait.until(browser -> {
            for (WebElement element : browser.findElements(locator)) {
                try {
                    if (element.isDisplayed() && element.isEnabled()) {
                        element.click();
                        return true;
                    }
                } catch (StaleElementReferenceException ignored) { }
            }
            return false;
        });
    }

    public KorisnikPage loginToKorisnikPage(ChromeDriver browser) {
        browser.get(Strings.MAIN_URL);
        return new LoginPage(browser).openFromHomePage()
                .enterEmailAddress(Strings.VALID_EMAIL)
                .enterPassword(Strings.VALID_PASSWORD)
                .clickSubmitButtonSuccess();
    }

    public BasePage clickBurgerButton() {
        click(By.cssSelector("button[aria-label='Otvori meni']"));
        return this;
    }
}
