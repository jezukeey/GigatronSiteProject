import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.KorisnikPage;
import pages.LoginPage;
import pages.Strings;

public abstract class BaseTest {
    protected ChromeDriver driver;

    @Before
    public void startBrowser() {
        driver = openChromeDriver();
    }

    public ChromeDriver openChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito", "--disable-features=Translate");
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1440,1000");
        }
        ChromeDriver browser = new ChromeDriver(options);
        if (Boolean.getBoolean("headless")) {
            browser.manage().window().setSize(new org.openqa.selenium.Dimension(1440, 1000));
        }
        browser.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(45));
        browser.manage().timeouts().scriptTimeout(java.time.Duration.ofSeconds(15));
        return browser;
    }

    @After
    public void stopBrowser() {
        if (driver != null) {
            try { driver.quit(); }
            finally { driver = null; }
        }
    }

    public KorisnikPage loginToKorisnikPage(ChromeDriver browser) {
        browser.get(Strings.MAIN_URL);
        return new LoginPage(browser).openFromHomePage()
                .enterEmailAddress(Strings.VALID_EMAIL)
                .enterPassword(Strings.VALID_PASSWORD)
                .clickSubmitButtonSuccess();
    }
}
