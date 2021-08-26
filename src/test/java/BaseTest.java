import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;

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

    public LoginPage loginPage(ChromeDriver driver, LoginPage clickSubmitButtonSuccess) {
        new LoginPage();
        LoginPage.enterEmailAddress("projectgigatron@gmail.com");
        LoginPage.enterPassword("passwordon1");
        return clickSubmitButtonSuccess;
    }


    //Hardcodovani sleep. Tj. on samo ceka 2 sekunde
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
