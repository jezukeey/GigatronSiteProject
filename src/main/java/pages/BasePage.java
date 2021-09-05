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



//    //Click accept cookie button
//    @FindBy(By.xpath("//*[text()='Prihvatam']"))
//    WebElement acceptCookieButton;


    public KorisnikPage loginToKorisnikPage (ChromeDriver driver) {
        LoginPage loginPage=new LoginPage(driver);
        loginPage.enterEmailAddress(Strings.VALID_USER);
        loginPage.enterPassword(Strings.VALID_PASSWORD);
        KorisnikPage korisnikPage=loginPage.clickSubmitButtonSuccess();
        return korisnikPage;
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