package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    //Side bar burger button
    @FindBy(className="burger-button")
    WebElement proizvodiSideBarLink;

    //Side bar close burger button
    @FindBy (xpath="//*[@id=\"navigation\"]/nav/div/div/div[1]/div/div/div/div/button/img")
    WebElement closeBurgerButton;

    //Polje za prijavu
    @FindBy(id="hd-reg")
    WebElement loginButtonField;

    //Side bar menu Sve kategorije
    @FindBy(className="mainli")
    WebElement allCategoiesText;

    //Logout drop down menu
    @FindBy(className="drop-menu-holder")
    WebElement dropdownMenuBar;


    ChromeDriver driver = null;

    public BasePage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }



    /**
     * Prijava sa ispravnim podacima.
     *
     * Steps:
     * 1. Open the login page.
     * 2. Enter a valid email address.
     * 3. Enter a valid password.
     * 4. Click the Sign In button.
     * 5. Open the account menu.
     * 6. Wait for the "Odjavi se" option.
     *
     * Expected result:
     * Login is confirmed before returning a KorisnikPage object.
     */
    public KorisnikPage loginToKorisnikPage(ChromeDriver driver) {

        // Kreira LoginPage objekat koristeći već otvoreni browser.
        // Konstruktor LoginPage otvara adresu za prijavu.
        LoginPage loginPage = new LoginPage(driver);

        // Čeka email polje, briše sadržaj i unosi email.
        loginPage.enterEmailAddress(Strings.VALID_EMAIL);

        // Čeka polje za lozinku, briše sadržaj i unosi lozinku.
        loginPage.enterPassword(Strings.VALID_PASSWORD);

        // Čeka dugme za prijavu i klikće ga.
        loginPage.submitLogin();

        // Čeka ime „Petar“ i klikom otvara meni naloga.
        loginPage.openAccountMenu();

        // Čeka da „Odjavi se“ bude vidljivo.
        // Ako se ne pojavi u roku od 15 sekundi, baca grešku čekanja.
        loginPage.isLogoutVisible();

        // Kreira i vraća KorisnikPage objekat za isti browser.
        // Njegov konstruktor ne otvara novu adresu.
        return new KorisnikPage(driver);
    }



//    ChromeDriver driver = null;
//    private WebElement acceptCookieButton;
//
//
//    public BasePage(ChromeDriver driver) {
//        PageFactory.initElements(driver, this);
//        this.driver = driver;
//    }


    public BasePage clickBurgerButton() {
        proizvodiSideBarLink.click();
        return this;
    }
    public void print (String s) {
        System.out.println(s);
    }

}