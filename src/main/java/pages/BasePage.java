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

    //Polje za prijavu
    @FindBy(id="hd-reg")
    WebElement loginButtonField;

    //Side bar menu Sve kategorije
    @FindBy(className="mainli")
    WebElement allCategoiesText;

    //Logout drop down menu
    @FindBy(className="drop-menu-holder")
    WebElement dropdownMenuBar;

    //Logout drop down menu
    @FindBy(className="drop-menu-holder")
    WebElement getDropdownMenuBar;

    //Button for submitting login
    @FindBy(id="loginSubmit")
    WebElement loginSubmitButton;

    //Button for submitting logOut
    @FindBy(id="/logout")
    WebElement logoutButton;


    ChromeDriver driver = null;

    public BasePage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public BasePage clickBurgerButton() {
        proizvodiSideBarLink.click();
        return this;
    }

    public BasePage clickLogoutButton() {
        dropdownMenuBar.click();
        return this;
    }

    public loginSubmitButton clickingSubmitButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebElement clickingSubmitButton;
        loginSubmitButton.click();
        final var loginSubmitButton=loginSubmitButton(driver);
        return loginSubmitButton(driver);
    }

    public loginSubmitButton loginSubmitButton(ChromeDriver driver) {
        loginSubmitButton submitButton=loginSubmitButton(driver);
        return submitButton(driver);
    }

    private BasePage.loginSubmitButton submitButton(ChromeDriver driver) {
        this.driver=driver;
        return null;
    }

    public static class loginSubmitButton {
    }

}