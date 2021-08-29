package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    //Side bar burger button
    @FindBy(className="burger-button")
    WebElement proizvodiSideBarLink;

    //Side bar close burger button
    @FindBy (xpath = "[/@class = \"close-menu\"]")
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

    //Click cookie button
    @FindBy(className="btn primary")
    WebElement cookieButton;




    ChromeDriver driver = null;


    public BasePage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public BasePage clickCookieButton() {
        cookieButton.click();
        return this;
    }

    public BasePage clickBurgerButton() {
        proizvodiSideBarLink.click();
        return this;
    }

}