import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/** Zajednički životni ciklus browsera i dijagnostika neuspeha svih UI testova. */
public abstract class BaseTest {
    protected ChromeDriver driver;

    @Rule
    public final TestWatcher browserLifecycle = new TestWatcher() {
        @Override
        protected void failed(Throwable failure, Description description) {
            if (driver == null) return;
            try {
                // Screenshot nastaje pre zatvaranja browsera, na mestu gde je test pao.
                String name = description.getClassName() + "-" + description.getMethodName();
                Path screenshot = Path.of("target", "screenshots",
                        name.replaceAll("[^a-zA-Z0-9_-]", "_") + "-" + System.currentTimeMillis() + ".png");
                Files.createDirectories(screenshot.getParent());
                Files.write(screenshot, driver.getScreenshotAs(OutputType.BYTES));
                // Ispisujemo putanju, bez kontakt podataka i sadržaja login forme.
                System.err.println("Screenshot neuspeha: " + screenshot.toAbsolutePath());
            } catch (Exception screenshotFailure) {
                // Dijagnostika ne sme zameniti prvobitni uzrok pada testa.
                failure.addSuppressed(screenshotFailure);
            }
        }

        @Override
        protected void finished(Description description) {
            // Poziva se i nakon neuspeha @Before metode ili assertion-a.
            stopBrowser();
        }
    };

    @Before
    public void startBrowser() {
        // Svaki test dobija odvojenu sesiju: nema kolačića ili prijave iz prethodnog testa.
        driver = openChromeDriver();
    }

    public ChromeDriver openChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-features=Translate");
        // Headless je opcioni režim; običan IntelliJ run prikazuje Chrome.
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1440,1000");
        }
        ChromeDriver browser = new ChromeDriver(options);
        try {
            if (Boolean.getBoolean("headless")) {
                browser.manage().window().setSize(new org.openqa.selenium.Dimension(1440, 1000));
            }
            // Ograničavamo učitavanje i izvršavanje skripti da test ne čeka beskonačno.
            browser.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
            browser.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
            return browser;
        } catch (RuntimeException setupFailure) {
            // Čak i neuspešna konfiguracija novog browsera mora osloboditi njegov proces.
            try { browser.quit(); } catch (RuntimeException closeFailure) {
                setupFailure.addSuppressed(closeFailure);
            }
            throw setupFailure;
        }
    }

    public void stopBrowser() {
        if (driver == null) return;
        try {
            // Zatvaramo sve prozore te sesije i ChromeDriver proces.
            driver.quit();
        } catch (RuntimeException closeFailure) {
            // Problem zatvaranja beležimo, bez prikrivanja rezultata osnovnog testa.
            System.err.println("Browser nije uredno zatvoren: " + closeFailure.getClass().getSimpleName());
        } finally {
            driver = null;
        }
    }
}