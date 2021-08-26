import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.Strings;


public class BaseTest {
    ChromeDriver driver=null;
    private LoginPage clickSubmitButtonSuccess;
    private LoginPage korisnikPage;


    public ChromeDriver openChromeDriver () {
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        ChromeDriver driver=new ChromeDriver(options);
        return driver;
    }


    public LoginPage loginToLoginPage (ChromeDriver driver) {
        LoginPage loginPage=new LoginPage(driver);
        LoginPage.enterEmailAddress(Strings.VALID_USER);
        LoginPage.enterPassword(Strings.VALID_PASSWORD);
        return korisnikPage;
    }

    public void print (String s) {
        System.out.println(s);
    }

    //Hardcodovani sleep. Tj. on samo ceka 2 sekunde
    public void sleep () {
        try {
            Thread.sleep(2000);

        } catch (Exception e) {
        }
    }

//    public void testSuccessfulLogIn() {
//        ChromeDriver driver = openChromeDriver();
//        LoginPage loginPage = new LoginPage(driver);
//        LoginPage.enterEmailAddress("projectgigatron@gmail.com");
//        LoginPage.enterPassword("passwordon1");
//    }
//}
}

