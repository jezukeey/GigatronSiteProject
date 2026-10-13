package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URI;

/** Provera stranice kartičnog plaćanja bez unošenja kartice ili klika na PLATI. */
public class WspayPage extends BasePage {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final By cardNumber = By.cssSelector("input[placeholder='Upišite broj kartice']");
    private final By expiry = By.cssSelector("input[placeholder='MM/YY']");
    private final By securityCode = By.cssSelector("input[placeholder='xxx(x)']");
    private final By pay = buttonNamed("PLATI");
    private final By cancel = buttonNamed("ODUSTANI");

    public WspayPage(ChromeDriver driver) {
        super(driver);
        wait.withTimeout(java.time.Duration.ofSeconds(45)).withMessage(
                "WSPay nije otvoren nakon online kartičnog izbora. Ako je prikazan dodatni korak potvrde, proveri ga ručno.")
                .until(browser -> "form.wspay.biz".equals(URI.create(browser.getCurrentUrl()).getHost()));

    }

    private static By buttonNamed(String name) {
        return By.xpath("//*[self::button or self::input or @role='button']["
                + "translate(normalize-space(),'" + LOWER + "','" + UPPER + "')='" + name + "'"
                + " or translate(@value,'" + LOWER + "','" + UPPER + "')='" + name + "']");
    }

    public boolean areCardFieldsEmpty() {
        return isEmpty(cardNumber) && isEmpty(expiry) && isEmpty(securityCode);
    }

    private boolean isEmpty(By locator) {
        String value = visible(locator).getDomProperty("value");
        return value != null && value.isEmpty();
    }

    public boolean isPayButtonVisible() {
        return visible(pay).isDisplayed();
    }

    /** Odustaje od plaćanja. Povratak sam po sebi ne potvrđuje otkazivanje narudžbe. */
    public void cancelAndWaitForReturn() {
        click(cancel);
        wait.withMessage("Nakon Odustani nije potvrđen povratak na Gigatron.")
                .until(browser -> {
                    URI current = URI.create(browser.getCurrentUrl());
                    return "gigatron.rs".equals(current.getHost()) && "/korpa".equals(current.getPath());
                });
    }
}