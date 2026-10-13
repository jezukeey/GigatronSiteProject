import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;

import pages.Strings;

import java.time.Duration;

public class ShoppingTests extends BaseTest {

    @Test
    public void AddAndRemoveItem() {
        String productUrl = Strings.TEST_PRODUCT_URL;
        String productName = Strings.TEST_PRODUCT_NAME;
        String productPath = java.net.URI.create(productUrl).getPath();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(productUrl);
        new pages.CookieConsent(driver).choose("reject-button");
        Assert.assertEquals("Otvoren je pogrešan proizvod.",
                productName, wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText());

        clickVisible(wait, By.xpath("//button[normalize-space()='Dodaj u korpu']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@role='dialog']//h2[normalize-space()='Proizvod je dodat u korpu']")));
        clickVisible(wait, By.cssSelector("[role='dialog'] a[href='/pregled-korpe']"));
        wait.until(browser -> "/pregled-korpe".equals(java.net.URI.create(browser.getCurrentUrl()).getPath()));

        By productInCart = By.cssSelector("main a[href='" + productPath + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productInCart));
        Assert.assertTrue("Izabrani laptop nije prikazan u korpi.",
                driver.findElements(productInCart).stream().anyMatch(e -> e.isDisplayed()));

        // Brisanje samo reda sa izabranim proizvodom.
        By removeProduct = By.xpath("//a[@href='" + productPath
                + "']/ancestor::*[.//button[@aria-label='Obriši proizvod']][1]//button[@aria-label='Obriši proizvod']");
        clickVisible(wait, removeProduct);
        clickVisible(wait, By.xpath("//button[normalize-space()='Obriši']"));
        By emptyCart = By.xpath("//h2[normalize-space()='Tvoja korpa je prazna']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCart));
        Assert.assertTrue("Proizvod je ostao u korpi.", driver.findElements(productInCart).isEmpty());

        // Potvrđuje i da brisanje ostaje sačuvano posle osvežavanja.
        driver.navigate().refresh();
        Assert.assertTrue("Korpa nije ostala prazna posle osvežavanja.",
                wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCart)).isDisplayed());
        clickVisible(wait, By.xpath("//a[normalize-space()='Početna strana']"));
        wait.until(ExpectedConditions.urlToBe(Strings.MAIN_URL));
        Assert.assertEquals("Povratak na početnu stranicu nije uspeo.",
                Strings.MAIN_URL, driver.getCurrentUrl());
    }

    private void clickVisible(WebDriverWait wait, By locator) {
        java.util.concurrent.atomic.AtomicReference<RuntimeException> lastFailure =
                new java.util.concurrent.atomic.AtomicReference<>();
        wait.withMessage(() -> "Klik nije uspeo: " + locator
                + "; URL: " + driver.getCurrentUrl()
                + "; poslednji uzrok: " + (lastFailure.get() == null
                ? "Nije pronađen vidljiv i omogućen element."
                : lastFailure.get().getMessage()));
        try {
            wait.until(browser -> {
                for (var element : browser.findElements(locator)) {
                    try {
                        if (element.isDisplayed() && element.isEnabled()) {
                            // Normalan Selenium klik nakon pomeranja izvan sticky zaglavlja.
                            ((org.openqa.selenium.JavascriptExecutor) browser).executeScript(
                                    "arguments[0].scrollIntoView({block:'center', inline:'nearest', behavior:'instant'});",
                                    element);
                            element.click();
                            return true;
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException
                             | org.openqa.selenium.ElementClickInterceptedException e) {
                        lastFailure.set(e);
                    }
                }
                return false;
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            try {
                java.nio.file.Path screenshot = java.nio.file.Path.of(
                        "target", "screenshots", "shopping-click-timeout.png");
                java.nio.file.Files.createDirectories(screenshot.getParent());
                java.nio.file.Files.write(screenshot,
                        driver.getScreenshotAs(org.openqa.selenium.OutputType.BYTES));
                e.addSuppressed(new IllegalStateException(
                        "Screenshot: " + screenshot.toAbsolutePath()));
            } catch (java.io.IOException | org.openqa.selenium.WebDriverException screenshotError) {
                e.addSuppressed(screenshotError);
            }
            if (lastFailure.get() != null) e.addSuppressed(lastFailure.get());
            throw e;
        }
    }

    /** Proverava checkout do Cloudflare provere; nastavak do plaćanja je ručni. */
    @Test
    public void GuestCheckoutToVerification() {
        String productUrl = Strings.TEST_PRODUCT_URL;
        String productPath = java.net.URI.create(productUrl).getPath();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean productAdded = false;
        Throwable scenarioFailure = null;
        try {
            driver.get(productUrl);
            new pages.CookieConsent(driver).choose("reject-button");
            Assert.assertEquals("Otvoren je pogrešan proizvod.",
                    Strings.TEST_PRODUCT_NAME,
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText());
            clickVisible(wait, By.xpath("//button[normalize-space()='Dodaj u korpu']"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@role='dialog']//h2[normalize-space()='Proizvod je dodat u korpu']")));
            productAdded = true;
            clickVisible(wait, By.cssSelector("[role='dialog'] a[href='/pregled-korpe']"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("main a[href='" + productPath + "']")));
            clickVisible(wait, By.xpath("//a[normalize-space()='Nastavi na način isporuke']"));
            wait.until(browser -> "/korpa".equals(java.net.URI.create(browser.getCurrentUrl()).getPath()));

            By pickup = By.cssSelector("[role='radio'][value='gstore-pickup']");
            clickVisible(wait, pickup);
            By store = By.cssSelector("[role='dialog'] [role='radio'][value='G-62']");
            clickVisible(wait, store);
            Assert.assertEquals("Prodavnica nije izabrana.", "true",
                    driver.findElement(store).getDomAttribute("aria-checked"));
            clickVisible(wait, By.xpath("//*[@role='dialog']//button[normalize-space()='Potvrdite izbor']"));
            wait.until(browser -> browser.findElements(By.cssSelector("[role='dialog']")).stream()
                    .noneMatch(element -> element.isDisplayed()));
            Assert.assertEquals("Preuzimanje u prodavnici nije izabrano.", "true",
                    driver.findElement(pickup).getDomAttribute("aria-checked"));
            Assert.assertTrue("Izabrana prodavnica G-62 nije prikazana.",
                    driver.findElements(By.xpath("//*[normalize-space()='G-62']"))
                            .stream().anyMatch(element -> element.isDisplayed()));
            // Prisustvo provere je očekivani završetak ovog ograničenog scenarija.
            // Ne proglašava uspešnim checkout, CAPTCHA ili plaćanje.
            try {
                wait.withMessage("Cloudflare okvir nije pronađen, ni unutar shadow DOM-a.")
                        .until(browser -> hasVerificationFrame(browser, 0));
            } catch (org.openqa.selenium.TimeoutException failure) {
                try {
                    var screenshot = java.nio.file.Path.of("target", "screenshots", "guest-verification-not-found.png");
                    java.nio.file.Files.createDirectories(screenshot.getParent());
                    java.nio.file.Files.write(screenshot,
                            driver.getScreenshotAs(org.openqa.selenium.OutputType.BYTES));
                    failure.addSuppressed(new IllegalStateException("Screenshot: " + screenshot.toAbsolutePath()));
                } catch (java.io.IOException | org.openqa.selenium.WebDriverException captureFailure) {
                    failure.addSuppressed(captureFailure);
                }
                throw failure;
            }
            System.out.println("PASS: proizvod, guest checkout i G-62 su potvrđeni; "
                    + "Cloudflare provera je prikazana. CAPTCHA NIJE rešena. "
                    + "Kontakt podaci, način plaćanja i WSPay NISU testirani. "
                    + "Ručni nastavak: GUEST_CHECKOUT.md. Test sada čisti korpu i zatvara browser.");
        } catch (RuntimeException | AssertionError failure) {
            scenarioFailure = failure;
            throw failure;
        } finally {
            if (productAdded) {
                try {
                    removeCheckoutProduct(wait, productPath);
                } catch (RuntimeException | AssertionError cleanupFailure) {
                    if (scenarioFailure != null) scenarioFailure.addSuppressed(cleanupFailure);
                    else throw cleanupFailure;
                }
            }
        }
    }
    /** Čita samo metapodatke okvira; ne ulazi u CAPTCHA i ne klikće je. */
    private boolean hasVerificationFrame(org.openqa.selenium.SearchContext root, int depth) {
        if (depth > 5) return false;
        for (var element : root.findElements(By.cssSelector("*"))) {
            try {
                if ("iframe".equalsIgnoreCase(element.getTagName()) && element.isDisplayed()) {
                    String src = element.getDomAttribute("src");
                    String title = element.getDomAttribute("title");
                    String id = element.getDomAttribute("id");
                    String metadata = ((src == null ? "" : src) + " "
                            + (title == null ? "" : title) + " " + (id == null ? "" : id))
                            .toLowerCase(java.util.Locale.ROOT);
                    if (metadata.contains("challenges.cloudflare.com")
                            || metadata.contains("cf-chl-widget")
                            || metadata.contains("cloudflare")
                            || metadata.contains("security challenge")) return true;
                }
                try {
                    if (hasVerificationFrame(element.getShadowRoot(), depth + 1)) return true;
                } catch (org.openqa.selenium.NoSuchShadowRootException ignored) { }
            } catch (org.openqa.selenium.StaleElementReferenceException
                     | org.openqa.selenium.DetachedShadowRootException ignored) { }
        }
        return false;
    }
    private void removeCheckoutProduct(WebDriverWait wait, String productPath) {
        driver.get(Strings.MAIN_URL + "pregled-korpe");
        By remove = By.xpath("//a[@href='" + productPath
                + "']/ancestor::*[.//button[@aria-label='Obriši proizvod']][1]//button[@aria-label='Obriši proizvod']");
        clickVisible(wait, remove);
        clickVisible(wait, By.xpath("//button[normalize-space()='Obriši']"));
        Assert.assertTrue("Test proizvod nije uklonjen posle checkout provere.",
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[normalize-space()='Tvoja korpa je prazna']"))).isDisplayed());
    }

}
