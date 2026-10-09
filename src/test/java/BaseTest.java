import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.KorisnikPage;
import pages.LoginPage;
import pages.Strings;


public class BaseTest {
    ChromeDriver driver = null;


    /**
     * Pokreće Chrome sa podešavanjima za automatizovane testove.
     *
     * Steps:
     * 1. Create browser options.
     * 2. Disable Chrome translation.
     * 3. Start the browser maximized.
     * 4. Configure certificate and popup handling.
     * 5. Use incognito mode.
     * 6. Create and return ChromeDriver.
     */
    public ChromeDriver openChromeDriver() {

        // Kreira objekat u koji dodajemo podešavanja za Chrome.
        ChromeOptions options = new ChromeOptions();

        // Isključuje Chrome funkciju prevođenja.
        // Ne određuje jezik koji sam sajt koristi.
        options.addArguments("--disable-features=Translate");

        // Pokreće Chrome sa maksimizovanim prozorom.
        options.addArguments("--start-maximized");

        // Omogućava nastavak rada uprkos greškama sertifikata.
        // Time test može propustiti problem sa sertifikatom sajta.
        options.addArguments("--ignore-certificate-errors");

        // Isključuje blokiranje iskačućih prozora.
        options.addArguments("--disable-popup-blocking");

        // Pokreće Chrome u anonimnom režimu.
        // Ne koristi sačuvanu prijavu iz tvog uobičajenog profila.
        options.addArguments("--incognito");

        // Izostavlja Chrome prekidač „enable-automation“.
        // Ovo ne garantuje da sajt neće prepoznati automatizaciju.
        options.setExperimentalOption(
                "excludeSwitches",
                new String[]{"enable-automation"}
        );

        // Pokreće browser koristeći sva navedena podešavanja.
        ChromeDriver driver = new ChromeDriver(options);

        // Vraća driver metodi koja je pozvala openChromeDriver().
        return driver;
    }


    /**
     * Prijavljuje test nalog koristeći postojeći browser.
     *
     * Steps:
     * 1. Open the login page.
     * 2. Enter valid credentials.
     * 3. Submit the login form.
     * 4. Open the account menu.
     * 5. Verify that the logout option is visible.
     *
     * Expected result:
     * Login is confirmed before returning KorisnikPage.
     */
    public KorisnikPage loginToKorisnikPage(ChromeDriver driver) {
        // Otvara login stranicu i priprema njene elemente.
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterEmailAddress(Strings.VALID_EMAIL);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        loginPage.submitLogin();
        loginPage.openAccountMenu();

        // Čeka opciju za odjavu i potvrđuje prijavu.
        org.junit.Assert.assertTrue(
                "Prijava nije potvrđena.",
                loginPage.isLogoutVisible()
        );

        // Vraća objekat koji koristi isti browser.
        return new KorisnikPage(driver);
    }


    //Hardcodovani sleep. on samo ceka 10 sekundi
    public void sleep(){
        try{
            Thread.sleep(3000);

        } catch (Exception e) {}
    }

}
