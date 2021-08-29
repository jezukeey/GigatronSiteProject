import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.KorisnikPage;
import pages.LoginPage;
import pages.Strings;


public class BaseTest {
    ChromeDriver driver = null;


    public ChromeDriver openChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String[]{"--start-maximized"});
        options.addArguments(new String[]{"--ignore-certificate-errors"});
        options.addArguments(new String[]{"--disable-popup-blocking"});
        options.addArguments(new String[]{"--incognito"});
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        ChromeDriver driver = new ChromeDriver(options);
        return driver;
    }


    public KorisnikPage loginToKorisnikPage (ChromeDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(Strings.VALID_USER);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        KorisnikPage korisnikPage = loginPage.clickSubmitButtonSuccess();
        return korisnikPage;
    }


    //Hardcodovani sleep. on samo ceka 10 sekundi
    public void sleep(){
        try{
            Thread.sleep(3000);

        } catch (Exception e) {}
    }

}
