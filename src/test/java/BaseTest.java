import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.KorisnikPage;
import pages.LoginPage;
import pages.Strings;


public class BaseTest {
    ChromeDriver driver = null;


    public ChromeDriver openChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments(" --ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
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
            Thread.sleep(10000);

        } catch (Exception e) {}
    }
    //    public void testSuccessfulLogIn() {
//        ChromeDriver driver =  openChromeDriver();
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.enterUserName("standard_user");
//        loginPage.enterPassword("secret_sauce");
//        InventoryPage inventoryPage = loginPage.clickSubmitButtonSuccess();
//        inventoryPage.getAllItems();
//        inventoryPage.findItemByName("Sauce Labs Bike Light");
}
